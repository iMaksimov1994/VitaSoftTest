package com.maksimov.repositories;

import com.maksimov.entities.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByUserNameIsContainingIgnoreCaseAndRoles(String nameOrSubName, String roles);

    Optional<User> findUserByUserNameIsContainingIgnoreCase(String nameOrSubName);

    List<User> findUserByRoles(String roles);

    Optional<User> findUserByIdAndRoles(long id, String roles);
}
