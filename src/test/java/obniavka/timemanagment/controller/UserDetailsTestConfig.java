package obniavka.timemanagment.controller;

import obniavka.timemanagment.domain.UserDto;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

@TestConfiguration
public class UserDetailsTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService(){
        return (String name) -> UserDto.builder().email(name).password("password").role("admin".equals(name)?"ROLE_ADMIN" : "ROLE_USER").build();
    }

}
