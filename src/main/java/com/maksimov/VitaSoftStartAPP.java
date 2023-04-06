package com.maksimov;


import com.maksimov.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class VitaSoftStartAPP {
    public static void main(String[] args) {
        SpringApplication.run(VitaSoftStartAPP.class, args);
    }
}
