package repositories;

import entities.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlertRepository extends CrudRepository<Alert, Integer> {
}