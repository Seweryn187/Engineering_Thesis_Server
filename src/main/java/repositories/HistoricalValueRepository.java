package repositories;

import entities.HistoricalValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricalValueRepository extends CrudRepository<HistoricalValue, Integer> {
    List<HistoricalValue> findHistoricalValueByCurrencyAbbr(String abbr);
}