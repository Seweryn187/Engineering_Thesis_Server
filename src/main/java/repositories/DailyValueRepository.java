package repositories;

import entities.DailyValue;
import org.springframework.data.repository.CrudRepository;

public interface DailyValueRepository extends CrudRepository<DailyValue, Integer> {
}