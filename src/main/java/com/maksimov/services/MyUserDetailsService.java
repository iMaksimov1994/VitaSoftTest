package com.maksimov.services;

import com.maksimov.entities.MyUserDetails;
import com.maksimov.entities.User;
import com.maksimov.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findUserByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("User not found " + userName));
        return user.map(MyUserDetails::new).get();
    }
}
