package repositories;

import entities.HistoricalValue;
import org.springframework.data.repository.CrudRepository;

public interface HistoricalValueRepository extends CrudRepository<HistoricalValue, Integer> {
}