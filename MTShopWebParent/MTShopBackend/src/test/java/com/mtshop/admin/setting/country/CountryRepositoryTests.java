package com.mtshop.admin.setting.country;

import com.mtshop.common.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CountryRepositoryTests {

    @Autowired
    private CountryRepository repo;

    @Test
    public void testCreateCountry() {
        Country country = repo.save(new Country("Vietnam", "VN"));

        assertThat(country).isNotNull();
    }
}
