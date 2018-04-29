package com.usermanager.service;

import com.usermanager.entity.Role;
import com.usermanager.entity.RoleType;
import com.usermanager.entity.User;
import com.usermanager.entity.json.ResponseJson;
import com.usermanager.entity.json.UserJson;
import com.usermanager.repository.RoleRepository;
import com.usermanager.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserByUserName(username);

        if (user == null) {
            String message = "Username not found: " + username;
            logger.info(message);
            throw new UsernameNotFoundException(message);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(user.getRoleString()));

        return new org.springframework.security.core.userdetails.User(
                username,
                user.getUserPassword(),
                (user.getRole().getRoleType() != RoleType.ROLE_DISABLED), // enabled
                true, //Account Not Expired
                true, //Credentials Not Expired
                true,//Account Not Locked
                authorities
        ) {
        };

    }

    public void registerUser(UserJson userJson) throws ConstraintViolationException, IllegalArgumentException {

        String userName = userJson.getUserName();
        String userEmail = userJson.getUserEmail();

        if(userRepository.findUserByUserName(userName) != null || userRepository.findUserByUserEmail(userEmail) != null) {
            throw new IllegalArgumentException(
                    "User with these credentials already exists!" + userEmail + ", " + userName
            );
        }

        Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER);
        String password = BCrypt.hashpw(userJson.getUserPassword(), BCrypt.gensalt());

        User user = new User(userName, password, userJson.getUserEmail(), userRole);
        saveUser(user);

    }

    private void saveUser(@Valid User user) throws ConstraintViolationException {

        userRepository.save(user);
        logger.info("A new user has been added: {}", user.getUserName());
    }

    public List<HashMap<String, String>> getUsers() {

        List<User> users = userRepository.findAll();

        return users
                .stream()
                .map(user -> ( user.getSimpleUserDetails()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseJson deleteUser(long userId) {
        ResponseJson response = new ResponseJson(true);

        User user = userRepository.findUserById(userId);

        if(user == null) {
            response.setSuccess(false);
            response.setInformation("No user exists with such ID!");
            return response;
        }

        userRepository.deleteUserById(userId);
        logger.info("A user has been deleted: ID: {}, username: {}", userId, user.getUserName());

        return response;
    }
}
