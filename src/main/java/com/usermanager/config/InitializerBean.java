package com.usermanager.config;

import com.usermanager.entity.Role;
import com.usermanager.entity.RoleType;
import com.usermanager.entity.User;
import com.usermanager.repository.RoleRepository;
import com.usermanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitializerBean {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public InitializerBean(RoleRepository roleRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        initializeRoles();
        initializeUsers();
    }

    private void initializeRoles() {

        List<Role> roles = new ArrayList<>();

        roles.add(new Role(RoleType.ROLE_DISABLED));
        roles.add(new Role(RoleType.ROLE_USER));
        roles.add(new Role(RoleType.ROLE_ADMIN));

        roleRepository.save(roles);

    }

    private void initializeUsers() {

        Role admin = roleRepository.findByRoleType(RoleType.ROLE_ADMIN);

        User xattus = new User(
                "XAttus",
                "$2a$10$9fQS0odOowHrEnZcpO93s.00RPWfdVrpoVpaSl3LpDE.z7RuxjVF6" ,
                "xattus@gmail.com", admin);

        userRepository.save(xattus);
    }
}
