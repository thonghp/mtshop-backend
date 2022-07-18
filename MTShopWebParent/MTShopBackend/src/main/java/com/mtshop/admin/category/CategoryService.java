package com.mtshop.admin.category;

import com.mtshop.common.entity.Category;
import com.mtshop.common.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CategoryService {

    // 1 page only shows 2 category root
    public static final int ROOT_CATEGORIES_PER_PAGE = 2;

    @Autowired
    private CategoryRepository categoryRepo;

    public List<Category> listByPage(String sortType, int pageNum, CategoryPageInfo pageInfo, String keyword) {
        Sort sort = Sort.by("name");

        if (sortType.equals("asc")) {
            sort = sort.ascending();
        } else {
            sort = sort.descending();
        }

        Pageable pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORIES_PER_PAGE, sort);

        Page<Category> pageCategories = null;
        if (keyword != null && !keyword.isEmpty()) {
            pageCategories = categoryRepo.findByName(pageable, keyword);
        } else {
            pageCategories = categoryRepo.findByParentIsNull(pageable);
        }

        List<Category> rootCategories = pageCategories.getContent();

        pageInfo.setTotalPages(pageCategories.getTotalPages());
        pageInfo.setTotalElements(pageCategories.getTotalElements());

        if (keyword != null && !keyword.isEmpty()) {
            List<Category> searchResult = pageCategories.getContent();
            for (Category category : searchResult) {
                category.setHasChildren(category.getChildren().size() > 0);
            }

            return searchResult;
        } else {
            return listHierarchicalCategories(rootCategories, sortType);
        }
    }

    private List<Category> listHierarchicalCategories(List<Category> rootCategories, String sortDir) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));

            Set<Category> children = sortSubCategories(rootCategory.getChildren(), sortDir);

            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory, name));

                listSubHierachicalCategories(hierarchicalCategories, subCategory, 1, sortDir);
            }
        }

        return hierarchicalCategories;
    }

    private void listSubHierachicalCategories(List<Category> hierarchicalCategories, Category parent, int subLevel,
                                              String sortDir) {
        Set<Category> children = sortSubCategories(parent.getChildren(), sortDir);
        int newSubLevel = subLevel + 1;

        for (Category subCategory : children) {
            String name = "";

            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }

            name += subCategory.getName();

            hierarchicalCategories.add(Category.copyFull(subCategory, name));

            listSubHierachicalCategories(hierarchicalCategories, subCategory, newSubLevel, sortDir);
        }
    }

    public List<Category> listCategoriesUsedInForm() {
        List<Category> categoriesUsedInForm = new ArrayList<>();

        Iterable<Category> categoriesInDB = categoryRepo.findByParentIsNull(Sort.by("name").ascending());

        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {
                categoriesUsedInForm.add(Category.copyIdAndName(category));

                Set<Category> children = sortSubCategories(category.getChildren());

                for (Category subCategory : children) {
                    String name = "--" + subCategory.getName();

                    categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

                    listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
                }
            }
        }
        return categoriesUsedInForm;
    }

    private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = sortSubCategories(parent.getChildren());

        for (Category subCategory : children) {
            String name = "";

            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }

            name += subCategory.getName();

            categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

            listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
        }
    }

    public Category save(Category category) {
        Category parent = category.getParent();
        if (parent != null) {
            String allParentIds = parent.getAllParentIDs() == null ? "-" : parent.getAllParentIDs();
            allParentIds += String.valueOf(parent.getId()) + "-";
            category.setAllParentIDs(allParentIds);
        }

        if (category.getAlias() == null || category.getAlias().isEmpty()) {
            String defaultAlias = category.getName().replaceAll(" ", "-");
            category.setAlias(defaultAlias);
        } else {
            category.setAlias(category.getAlias().replaceAll(" ", "-"));
        }

        return categoryRepo.save(category);
    }

    public Category get(Integer id) throws CategoryNotFoundException {
        try {
            return categoryRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new CategoryNotFoundException("Could not find any category with ID " + id);
        }
    }

    public String checkUnique(Integer id, String name, String alias) {
        boolean isCreatingNew = (id == null || id == 0);

        Category categoryByName = categoryRepo.findByName(name);

        if (isCreatingNew) {
            if (categoryByName != null) {
                return "DuplicateName";
            } else {
                Category categoryByAlias = categoryRepo.findByAlias(alias);
                if (categoryByAlias != null) return "DuplicateAlias";
            }
        } else {
            if (categoryByName != null && categoryByName.getId() != id) return "DuplicateName";

            Category categoryByAlias = categoryRepo.findByAlias(alias);
            if (categoryByAlias != null && categoryByAlias.getId() != id) return "DuplicateAlias";
        }

        return "OK";
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children) {
        return sortSubCategories(children, "asc");
    }

    private SortedSet<Category> sortSubCategories(Set<Category> children, String sortDir) {
        SortedSet<Category> sortedSet = new TreeSet<>(new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                if (sortDir.equals("asc")) {
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return o2.getName().compareTo(o1.getName());
                }
            }
        });

        sortedSet.addAll(children);

        return sortedSet;
    }

    public void delete(Integer id) throws CategoryNotFoundException {
        Long countById = categoryRepo.countById(id);

        if (countById == null || countById == 0) {
            throw new CategoryNotFoundException("Could not find any category with ID: " + id);
        }

        categoryRepo.deleteById(id);
    }

    public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
        categoryRepo.updateEnabledStatus(id, enabled);
    }

//    public Category getByEmail(String email) {
//        return categoryRepo.findByEmail(email);
//    }
}
