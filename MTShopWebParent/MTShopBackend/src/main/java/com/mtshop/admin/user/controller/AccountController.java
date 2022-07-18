package com.mtshop.admin.user.controller;

import com.mtshop.admin.FileUploadUtil;
import com.mtshop.admin.security.MTShopUserDetails;
import com.mtshop.admin.service.UserService;
import com.mtshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal MTShopUserDetails loggedUser, Model model) {
        String email = loggedUser.getUsername();
        User user = userService.getByEmail(email);
        model.addAttribute("user", user);

        return "users/account_form";
    }

    @PostMapping("/account/update")
    public String saveDetails(User user, RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal MTShopUserDetails loggedUser,
                              @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);

            User savedUser = userService.updateAccount(user);

            String uploadDir = "images/user-photos/" + savedUser.getId();

            FileUploadUtil.cleanDir(uploadDir); // remove ảnh cũ khi có ảnh mới
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhotos().isEmpty())
                user.setPhotos(null);
            userService.updateAccount(user);
        }

        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());

        redirectAttributes.addFlashAttribute("message", "Tài khoản đã được cập nhật thành công !");

        return "redirect:/account";
    }
}
