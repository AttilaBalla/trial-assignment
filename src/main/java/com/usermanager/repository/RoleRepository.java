package com.usermanager.repository;

import com.usermanager.entity.Role;
import com.usermanager.entity.RoleType;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByRoleType(RoleType role);
}