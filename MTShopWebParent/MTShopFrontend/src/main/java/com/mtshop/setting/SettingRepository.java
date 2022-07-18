package com.mtshop.setting;

import com.mtshop.common.entity.setting.Setting;
import com.mtshop.common.entity.setting.SettingCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingRepository extends CrudRepository<Setting, String> {
    List<Setting> findByCategory(SettingCategory category);

    @Query("select s from Setting s where s.category = ?1 or s.category = ?2")
    List<Setting> findByTwoCategories(SettingCategory catOne, SettingCategory catTwo);
}
