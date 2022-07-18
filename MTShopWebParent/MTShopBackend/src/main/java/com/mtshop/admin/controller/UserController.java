package com.mtshop.admin.controller;

import com.mtshop.admin.FileUploadUtil;
import com.mtshop.admin.user.UserNotFoundException;
import com.mtshop.admin.service.UserService;
import com.mtshop.admin.user.export.UserCSVExporter;
import com.mtshop.admin.user.export.UserExcelExporter;
import com.mtshop.admin.user.export.UserPDFExporter;
import com.mtshop.common.entity.Role;
import com.mtshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> listFirstPage(Model model) {
        return listByPage(1, model, "id", "asc", null);
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<List<User>> listByPage(@PathVariable(name = "pageNumber") int pageNum, Model model,
                                                 @RequestParam("sortField") String sortField, @RequestParam("sortType") String sortType,
                                                 @RequestParam(value = "keyword", required = false) String keyword) {
        Page<User> page = userService.listByPage(pageNum, sortField, sortType, keyword);
        List<User> listUsers = page.getContent();

        long startElementOfPage = (pageNum - 1) * UserService.USER_PER_PAGE + 1;
        long endElementOfPage = startElementOfPage + UserService.USER_PER_PAGE - 1;

        if (endElementOfPage > page.getTotalElements()) {
            endElementOfPage = page.getTotalElements();
        }

        String reverseSortType = "desc".equals(sortType) ? "asc" : "desc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startElementOfPage);
        model.addAttribute("endCount", endElementOfPage);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("reverseSortType", reverseSortType);
        model.addAttribute("keyword", keyword);

        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        User user = new User();
        List<Role> listRoles = userService.listRoles();

        user.setEnabled(true);

//      User(null,...,true,...)
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Tạo người dùng mới");

        return "users/user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);

            User savedUser = userService.save(user);

            String uploadDir = "images/user-photos/" + savedUser.getId();

            FileUploadUtil.cleanDir(uploadDir); // remove ảnh cũ khi có ảnh mới
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhotos().isEmpty())
                user.setPhotos(null);
            userService.save(user);
        }

        redirectAttributes.addFlashAttribute("message", "User đã được lưu thành công !");

        return getRedirectURLToAffectedUser(user);
    }

    private String getRedirectURLToAffectedUser(User user) {
        String firstPartOfEmail = user.getEmail().split("@")[0];
        return "redirect:/users/page/1?sortField=id&sortType=asc&keyword=" + firstPartOfEmail;
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.get(id);
            List<Role> listRoles = userService.listRoles();

            model.addAttribute("user", user);
            model.addAttribute("listRoles", listRoles);
            model.addAttribute("pageTitle", "Sửa người dùng (ID: " + id + ")");

            return "users/user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/users";
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Integer id) throws UserNotFoundException {

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable(name = "id") Integer id,
                                          @PathVariable(name = "status") boolean enabled,
                                          RedirectAttributes redirectAttributes) {
        userService.updateUserEnabledStatus(id, enabled);

        String status = enabled ? "kích hoạt" : "vô hiệu hoá";
        String message = "User " + id + " đã được " + status;

        redirectAttributes.addFlashAttribute("message", message);

        return "redirect:/users";
    }

    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.listAll();
        UserCSVExporter exporter = new UserCSVExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.listAll();
        UserExcelExporter exporter = new UserExcelExporter();
        exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<User> listUsers = userService.listAll();
        UserPDFExporter exporter = new UserPDFExporter();
        exporter.export(listUsers, response);
    }
}
