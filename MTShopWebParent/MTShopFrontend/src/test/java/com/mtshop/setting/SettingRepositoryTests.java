package com.mtshop.setting;

import com.mtshop.common.entity.setting.Setting;
import com.mtshop.common.entity.setting.SettingCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {

    @Autowired
    private SettingRepository settingRepo;

    @Test
    public void testFindByTwoCategories() {
        List<Setting> settings = settingRepo.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);

        settings.forEach(System.out::println);
    }
}
