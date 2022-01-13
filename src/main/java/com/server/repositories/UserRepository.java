package com.server.repositories;

import com.server.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByEmail(String email);
    Optional<User> findByLogin(String login);
    Boolean existsByLogin(String login);
    Integer deleteByEmail(String email);
}