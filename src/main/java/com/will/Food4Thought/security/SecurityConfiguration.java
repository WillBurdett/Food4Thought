//package com.will.Food4Thought.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // set config on the auth object
//        auth.inMemoryAuthentication()
//                .withUser("foo")
//                .password("foo")
//                .roles("ADMIN");
//    }
//
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        // NoOp is not encoding our password
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                    .antMatchers("/meals").hasRole("ADMIN")
//                .antMatchers("/user").permitAll()
//                .and().formLogin();
//    }
//}
