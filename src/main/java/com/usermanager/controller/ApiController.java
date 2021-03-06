package com.usermanager.controller;

import com.usermanager.entity.json.ResponseJson;
import com.usermanager.entity.json.UserJson;
import com.usermanager.service.UserService;
import com.usermanager.utility.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;

@RestController
public class ApiController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserService userService;

    @Autowired
    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users")
    public String registerUser(@RequestBody UserJson userJson) {

        ResponseJson response = new ResponseJson(true);

        try {
            userService.registerUser(userJson);
        }
        catch (ConstraintViolationException ex) {

            response.setSuccess(false);
            response.setInformation("The submitted parameters are invalid!");
            logger.info("Unsuccessful registration attempt: {}", ex.getMessage());
        }
        catch (IllegalArgumentException ex) {

            response.setSuccess(false);
            response.setInformation(ex.getMessage());
            logger.info("Unsuccessful registration attempt: {}", ex.getMessage());
        }

        return JsonUtil.toJson(response);
    }

    @GetMapping(value = "/users")
    public String listUsers() {
        List<HashMap<String, String>> users = userService.getUsers();

        return JsonUtil.toJson(users);
    }

    @DeleteMapping(value = "/delete")
    public String deleteUser(@RequestParam(value="userId") long userId) {

        return JsonUtil.toJson(userService.deleteUser(userId));
    }
}
