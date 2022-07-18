package com.mtshop.category;

import com.mtshop.common.entity.Category;
import com.mtshop.common.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepo;

    // return children nhỏ nhất -- --
    public List<Category> listNoChildrenCategories() {
        List<Category> listNoChildrenCategories = new ArrayList<>();

        List<Category> listEnabledCategories = categoryRepo.findAllByEnabledTrueOrderByNameAsc();

        listEnabledCategories.forEach(category -> {
            Set<Category> children = category.getChildren();
            if (children == null || children.size() == 0) {
                listNoChildrenCategories.add(category);
            }
        });

        return listNoChildrenCategories;
    }

    public Category getCategory(String alias) throws CategoryNotFoundException {
        Category category = categoryRepo.findAllByAliasAndEnabledTrue(alias);
        if (category == null)
            throw new CategoryNotFoundException("Could not find any product with alias " + alias);

        return category;
    }

    // breadcum
    public List<Category> getCategoryParents(Category child) {
        List<Category> listParents = new ArrayList<>();

        Category parent = child.getParent();

        while (parent != null) {
            listParents.add(0, parent);
            parent = parent.getParent();
        }

        listParents.add(child);

        return listParents;
    }
}
