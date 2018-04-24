package com.usermanager.service;

import com.usermanager.entity.RoleType;
import com.usermanager.entity.User;
import com.usermanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            String message = "Username not found: " + username;
            logger.info(message);
            throw new UsernameNotFoundException(message);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(user.getRoleString()));

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                (user.getRole().getRoleType() != RoleType.ROLE_DISABLED), // enabled
                true, //Account Not Expired
                true, //Credentials Not Expired
                true,//Account Not Locked
                authorities
        ) {
        };

    }
}
