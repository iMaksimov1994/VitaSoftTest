package com.maksimov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/addUser**").permitAll()
                .antMatchers("/getUsers**").hasAnyRole("ADMIN")
                .antMatchers("/findUserByNameOrSubName**").hasAnyRole("ADMIN")
                .antMatchers("/setOperatorRole**").hasAnyRole("ADMIN")
                .antMatchers("/createRequisition**").hasAnyRole("USER")
                .antMatchers("/listOfRequisitionsForUser**").hasAnyRole("USER")
                .antMatchers("/editRequisition**").hasAnyRole("USER")
                .antMatchers("/sendRequisition**").hasAnyRole("USER")
                .antMatchers("/listRequisitionsForOperator**").hasAnyRole("OPERATOR")
                .antMatchers("/listRequisitionsByNameOrSubNameForOperator**").hasAnyRole("OPERATOR")
                .antMatchers("/approveRequisition**").hasAnyRole("OPERATOR")
                .antMatchers("/rejectRequisition**").hasAnyRole("OPERATOR")
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
