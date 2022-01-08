package com.server.repositories;

import com.server.entities.HistoricalValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HistoricalValueRepository extends CrudRepository<HistoricalValue, Integer> {
    List<HistoricalValue> findHistoricalValueByCurrencyAbbr(String abbr);
    List<HistoricalValue> findHistoricalValueByCurrencyAbbrAndSourceName(String abbr, String name);
    List<HistoricalValue> findHistoricalValueByCurrencyAbbrAndSourceNameAndDateBefore(String abbr, String name,
                                                                                    LocalDate timePeriod);
}