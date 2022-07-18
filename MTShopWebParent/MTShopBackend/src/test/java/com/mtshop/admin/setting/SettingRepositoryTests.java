package com.mtshop.admin.setting;

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
    public void testCreateGeneralSetting() {
        Setting siteName = new Setting();
        siteName.setKey("COPYRIGHT");
        siteName.setValue("Copyright (C) 2022 MTShop Ltd.");
        siteName.setCategory(SettingCategory.GENERAL);

        Setting savedSetting = settingRepo.save(siteName);

        assertThat(savedSetting).isNotNull();
    }

    @Test
    public void testCreateCurrencySetting() {
        Setting currencyId = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
        Setting symbol = new Setting("CURRENCY_SYMBOL", "$", SettingCategory.CURRENCY);
        Setting symbolPosition = new Setting("CURRENCY_SYMBOL_POSITION", "before", SettingCategory.CURRENCY);
        Setting decimalPointType = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
        Setting decimalDigits = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
        Setting thousandsPointType = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.CURRENCY);

        settingRepo.saveAll(List.of(currencyId, symbol, symbolPosition, decimalPointType, decimalDigits, thousandsPointType));
    }

    @Test
    public void testListSettingByCategory() {
        List<Setting> settings = settingRepo.findByCategory(SettingCategory.GENERAL);

        settings.forEach(System.out::println);
    }
}
