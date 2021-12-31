package com.server.repositories;

import com.server.entities.Alert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AlertRepository extends CrudRepository<Alert, Integer> {
    List<Alert> findAlertByCurrencyAbbr(String abbr);
    List<Alert> findAlertByUserEmail(String email);
}