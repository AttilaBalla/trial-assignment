package com.usermanager.controller;

import com.usermanager.entity.json.ResponseJson;
import com.usermanager.entity.json.UserJson;
import com.usermanager.service.UserService;
import com.usermanager.utility.JsonUtil;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
public class ApiController {

    private UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String renderIndex() {

        return "index";
    }

    @PostMapping(value = "/register")
    public String registerUser(@RequestBody UserJson userJson) {

        ResponseJson response = userService.validateUserDetails(userJson);

        if(response.isSuccess()) {
            userService.registerUser(userJson);
        }

        return JsonUtil.toJson(response);
    }

    @GetMapping(value = "/users")
    public String listUsers() {
        List<HashMap<String, String>> users = userService.getUsers();

        if(users.size() < 1) {

            return JsonUtil.toJson( new ResponseJson(true, "No users in the database at this time."));
        }
        else {

            return JsonUtil.toJson(users);
        }
    }
}
