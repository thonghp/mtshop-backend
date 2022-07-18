package com.mtshop.admin.setting.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtshop.common.entity.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private CountryRepository repo;

    @Test
    @WithMockUser(username = "thong@gmail.com", password = "thong123", roles = "Admin")
    public void testListCountries() throws Exception {
        String url = "/countries/list";
        MvcResult result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        System.out.println(jsonResponse);

        Country[] countries = objectMapper.readValue(jsonResponse, Country[].class);
        for (Country country : countries) {
            System.out.println(country);
        }

        assertThat(countries).hasSizeGreaterThan(0);
    }

    @Test
    @WithMockUser(username = "thong@gmail.com", password = "thong123", roles = "Admin")
    public void testCreateCountry() throws Exception {
        String url = "/countries/save";
        String countryName = "USA";
        String countryCode = "US";
        Country country = new Country(countryName, countryCode);

        MvcResult result = mockMvc.perform(post(url).contentType("application/json")
                .content(objectMapper.writeValueAsString(country))
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Integer countryId = Integer.parseInt(response);
        Optional<Country> findByID = repo.findById(countryId);
        Country savedCountry = findByID.get();

        assertThat(savedCountry.getName()).isEqualTo(countryName);
    }

    @Test
    @WithMockUser(username = "thong@gmail.com", password = "thong123", roles = "Admin")
    public void testUpdateCountry() throws Exception {
        String url = "/countries/save";

        Integer countryId = 3;
        String countryName = "My";
        String countryCode = "US";
        Country country = new Country(countryId, countryName, countryCode);

        mockMvc.perform(post(url).contentType("application/json")
                .content(objectMapper.writeValueAsString(country))
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(String.valueOf(countryId)));

        Optional<Country> findByID = repo.findById(countryId);
        Country savedCountry = findByID.get();

        assertThat(savedCountry.getName()).isEqualTo(countryName);
    }

    @Test
    @WithMockUser(username = "thong@gmail.com", password = "thong123", roles = "Admin")
    public void testDeleteCountry() throws Exception {
        Integer countryId = 3;
        String url = "/countries/delete/" + countryId;

        mockMvc.perform(get(url)).andExpect(status().isOk());

        Optional<Country> findByID = repo.findById(countryId);

        assertThat(findByID).isNotPresent();
    }
}
