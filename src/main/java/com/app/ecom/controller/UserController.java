package com.app.ecom.controller;

import com.app.ecom.entity.User;
import com.app.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    //@Autowired
    private final UserService userService;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping
    //@RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        return new  ResponseEntity<>(userService.fetchAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> fetchUserById(@PathVariable long id) {
        Optional<User> user = userService.fetchUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        String msg =  "User created successfully with id: " + userService.addUser(user);
        return  new ResponseEntity<>(msg,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody User updatedUser) {
        // Logic to update an existing user in the database
            String msg = userService.updateUser(id, updatedUser);
            if(msg.equals("User not found")) return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);
        return ResponseEntity.ok("User updated successfully");
    }
}
