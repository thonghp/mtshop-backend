package com.mtshop.admin.setting.state;

import com.mtshop.common.entity.Country;
import com.mtshop.common.entity.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {

    @Autowired
    private StateRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateStateInVietnam() {
        Integer countryId = 1;
        Country country = entityManager.find(Country.class, 1);
        State tphcm = repo.save(new State("TPHCM", country));

        assertThat(tphcm.getId()).isGreaterThan(0);
    }
}
