package com.usermanager.repository;

import com.usermanager.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    User findUserById(Long id);
}
