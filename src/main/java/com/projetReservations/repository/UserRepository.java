package com.projetReservations.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.projetReservations.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByLogin(String login);
    List<User> findByLastname(String lastname);
    User findById(long id);
}
