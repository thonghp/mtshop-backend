package com.mtshop.admin.category;

import com.mtshop.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepo;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testCheckUniqueInNewModeReturnDuplicatedName() {
        Integer id = null;
        String name = "Linh kiện";
        String alias = "abc";

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setAlias(alias);

        Mockito.when(categoryRepo.findByName(name)).thenReturn(category);
        Mockito.when(categoryRepo.findByAlias(alias)).thenReturn(null);

        String result = categoryService.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void testCheckUniqueInNewModeReturnDuplicatedAlias() {
        Integer id = null;
        String name = "abc";
        String alias = "vga";

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setAlias(alias);

        Mockito.when(categoryRepo.findByName(name)).thenReturn(null);
        Mockito.when(categoryRepo.findByAlias(alias)).thenReturn(category);

        String result = categoryService.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateAlias");
    }

    @Test
    public void testCheckUniqueInNewModeReturnOK() {
        Integer id = null;
        String name = "DDR4";
        String alias = "ddr4";

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setAlias(alias);

        Mockito.when(categoryRepo.findByName(name)).thenReturn(null);
        Mockito.when(categoryRepo.findByAlias(alias)).thenReturn(null);

        String result = categoryService.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("OK");
    }
}
