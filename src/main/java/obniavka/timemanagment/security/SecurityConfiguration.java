package obniavka.timemanagment.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private static final String LOGIN = "/login";

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, UserDetailsService userDetailsService) throws Exception {
        http
                .authorizeRequests()
               // .antMatchers("/image/**").permitAll()
                .antMatchers("/image/**", "/convert", "/js/**", "/styles/**", "/login/**").permitAll()
                .antMatchers("/test").permitAll()
              //  .antMatchers("/js/**").permitAll()
               // .antMatchers("/styles/**").permitAll()
              //  .antMatchers("/login/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage(LOGIN).permitAll()//**for custom login page
                .loginProcessingUrl("/doLogin") //the url to submit username and password
                .failureForwardUrl("/login/failure")
                .successHandler(customAuthenticationSuccessHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl(LOGIN)
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .rememberMe().userDetailsService(userDetailsService)
                .key("uniqueAndSecret")
                .and()
                .headers()
                .contentTypeOptions()
                .disable()
                .and()
                .csrf().disable();


        return http.build();
    }



}

