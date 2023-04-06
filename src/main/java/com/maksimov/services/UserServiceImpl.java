package com.maksimov.services;

import com.maksimov.entities.User;
import com.maksimov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(String userName, String password, String roles) {
        User user = new User(userName, this.passwordEncoder.encode(password), roles);
        try {
            return this.userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new IllegalArgumentException("User already exist");
        }
    }

    @Override
    public List<User> listOfUsers() {
        return this.userRepository.findUserByRoles("ROLE_USER");
    }

    @Override
    public User findUserByNameOrSubName(String nameOrSubName) {
        User user = this.userRepository.findUserByUserNameIsContainingIgnoreCaseAndRoles(nameOrSubName, "ROLE_USER")
                .orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found!");
        }
        return user;
    }

    @Override
    public User setOperatorRole(Long userId) {
        User user = this.userRepository.findUserByIdAndRoles(userId, "ROLE_USER").orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found!");
        }
        user.setRoles("ROLE_OPERATOR");
        return this.userRepository.save(user);
    }
}
