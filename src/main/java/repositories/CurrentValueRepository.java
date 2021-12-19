package repositories;

import entities.CurrentValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentValueRepository extends CrudRepository<CurrentValue, Integer> {
}