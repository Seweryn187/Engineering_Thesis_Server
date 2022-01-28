package com.server.repositories;

import com.server.entities.Alert;
import com.server.entities.CurrentValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrentValueRepository extends CrudRepository<CurrentValue, Integer> {

    List<CurrentValue> findCurrentValueBySourceName(String name);
    List<CurrentValue> findCurrentValueByCurrencyAbbr(String abbr);
    List<CurrentValue> findCurrentValueByBestPrice(boolean bestPrice);
}