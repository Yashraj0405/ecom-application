package com.app.ecom.service;

import com.app.ecom.entity.User;
import com.app.ecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //private List<User> userList = new ArrayList<>();


    public List<User> fetchAllUsers() {
        // Logic to fetch all users from the database
        return userRepository.findAll();
    }

    public Optional<User> fetchUserById(long id) {
        // Logic to fetch a user by ID from the database
        //return userList.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
        return userRepository.findById(id);
    }

    public String addUser(User user) {
        // Logic to create a new user in the database
        return userRepository.save(user).getId() + "";
    }

    public String updateUser(long id, User user) {

        //return  userList.stream().filter(u -> u.getId() == id).findFirst().map(existingUser -> {
        //    existingUser.setFirstName(user.getFirstName());
        //    existingUser.setLastName(user.getLastName());
        //    return "User updated successfully";
        //}).orElse("User not found");

        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            userRepository.save(existingUser);
            return "User updated successfully";
        }).orElse("User not found");
    }
}
