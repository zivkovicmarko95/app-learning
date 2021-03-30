package com.example.orderbackupapi.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.orderbackupapi.filter.JwtHeaderFilter;
import com.example.orderbackupapi.services.UserDetailsServiceImpl;

@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {
    
    /* 
        This class is used for Web security configuration and user needs to have valid 
        JWT token to access any of the endpoints from this component
    */

    private final JwtHeaderFilter jwtHeaderFilter;

    private final OrderBackupApiAuthenticationProvider orderBackupApiAuthenticationProvider;

    private final UserDetailsServiceImpl userDetailsService;

    private String[] publicUrls = { "/actuator/health" };

    @Autowired
    public WebConfig(JwtHeaderFilter jwtHeaderFilter, OrderBackupApiAuthenticationProvider orderBackupApiAuthenticationProvider,
                UserDetailsServiceImpl userDetailsServiceImpl) {
        this.jwtHeaderFilter = jwtHeaderFilter;
        this.orderBackupApiAuthenticationProvider = orderBackupApiAuthenticationProvider;
        this.userDetailsService = userDetailsServiceImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(orderBackupApiAuthenticationProvider)
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .cors()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint((req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .addFilterAfter(jwtHeaderFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests().antMatchers(publicUrls).permitAll()
            .antMatchers("/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
            .anyRequest().authenticated();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
