package com.mtshop.admin.brand;

import com.mtshop.common.entity.Brand;
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
public class BrandServiceTest {

    @MockBean
    private BrandRepository brandRepo;

    @InjectMocks
    private BrandService brandService;

    @Test
    public void testCheckUniqueInNewModeReturnDuplicated() {
        Integer id = null;
        String name = "Dell";

        Brand brand = new Brand();
        brand.setName(name);

        Mockito.when(brandRepo.findByName(name)).thenReturn(brand);

        String result = brandService.checkUnique(id, name);

        assertThat(result).isEqualTo("Duplicate");
    }

    @Test
    public void testCheckUniqueInNewModeReturnOK() {
        Integer id = null;
        String name = "Logitech";

        Mockito.when(brandRepo.findByName(name)).thenReturn(null);

        String result = brandService.checkUnique(id, name);

        assertThat(result).isEqualTo("OK");
    }
}
