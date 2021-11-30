package repositories;

import entities.CurrentValue;
import org.springframework.data.repository.CrudRepository;

public interface CurrentValueRepository extends CrudRepository<CurrentValue, Integer> {
}