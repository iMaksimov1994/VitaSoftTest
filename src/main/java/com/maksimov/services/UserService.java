package com.maksimov.services;

import com.maksimov.entities.User;

import java.util.List;

public interface UserService {
    User addUser(String userName, String password, String roles);

    //by admin
    List<User> listOfUsers();

    User findUserByNameOrSubName(String nameOrSubName);

    User setOperatorRole(Long userId);
}
