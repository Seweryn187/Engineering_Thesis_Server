package com.server.repositories;

import com.server.entities.DailyValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyValueRepository extends CrudRepository<DailyValue, Integer> {
}