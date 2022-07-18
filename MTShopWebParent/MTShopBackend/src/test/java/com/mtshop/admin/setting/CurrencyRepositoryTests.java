package com.mtshop.admin.setting;

import com.mtshop.common.entity.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CurrencyRepositoryTests {

    @Autowired
    private CurrencyRepository currencyRepo;

    @Test
    public void testCreateCurrencies() {
        List<Currency> listCurrencies = Arrays.asList(
                new Currency("British Pound", "£", "GPB"),
                new Currency("Euro", "€", "EUR"),
                new Currency("United States Dollar", "$", "USD"),
                new Currency("Japanese Yen", "¥", "JPY"),
                new Currency("Vietnamese đồng", "đ", "VND")
        );

        currencyRepo.saveAll(listCurrencies);

        Iterable<Currency> iterable = currencyRepo.findAll();

        assertThat(iterable).size().isEqualTo(5);
    }

    @Test
    public void testListAllOrderByNameAsc() {
        List<Currency> currencies = currencyRepo.findAllByOrderByNameAsc();

        currencies.forEach(System.out::println);

        assertThat(currencies.size()).isGreaterThan(0);
    }
}
