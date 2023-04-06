package com.maksimov.controllers;

import com.maksimov.dto.ResponseResult;
import com.maksimov.entities.User;
import com.maksimov.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод создан для теста, чтобы заполнить БД сущностями доступен всем!!!
     *
     * @param userName
     * @param password
     * @param roles
     * @return
     */
    @PostMapping("/addUser")
    public ResponseEntity<ResponseResult<User>> addUser(@RequestParam String userName, @RequestParam String password,
                                                        @RequestParam String roles) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.userService.addUser(userName,
                    password, roles)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Смотреть список пользователей(ADMIN)
     *
     * @return
     */
    @GetMapping("/getUsers")
    public ResponseEntity<ResponseResult<List<User>>> getUsers() {
        return new ResponseEntity<>(new ResponseResult<>(null, this.userService.listOfUsers()), HttpStatus.OK);
    }

    /**
     * Искать конкретного пользователя по имени(части имени) (ADMIN)
     *
     * @param nameOrSubName
     * @return
     */
    @GetMapping("/findUserByNameOrSubName")
    public ResponseEntity<ResponseResult<User>> findUserByNameOrSubName(@RequestParam String nameOrSubName) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.userService.
                    findUserByNameOrSubName(nameOrSubName)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Назначать пользователям права оператора(ADMIN)
     *
     * @param userId
     * @return
     */
    @PutMapping("/setOperatorRole")
    public ResponseEntity<ResponseResult<User>> setOperatorRole(@RequestParam Long userId) {
        try {
            return new ResponseEntity<>(new ResponseResult<>(null, this.userService.
                    setOperatorRole(userId)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
