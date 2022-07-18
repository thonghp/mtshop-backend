package com.mtshop.admin.setting;

import com.mtshop.common.entity.setting.Setting;
import com.mtshop.common.entity.setting.SettingCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SettingRepository extends CrudRepository<Setting, String> {
    List<Setting> findByCategory(SettingCategory category);
}
