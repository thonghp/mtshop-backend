package com.mtshop.setting;

import com.mtshop.common.entity.Country;
import com.mtshop.common.entity.State;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StateRepository extends CrudRepository<State, Integer> {
    List<State> findByCountryOrderByNameAsc(Country country);
}
