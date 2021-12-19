package repositories;

import entities.HistoricalValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalValueRepository extends CrudRepository<HistoricalValue, Integer> {
}