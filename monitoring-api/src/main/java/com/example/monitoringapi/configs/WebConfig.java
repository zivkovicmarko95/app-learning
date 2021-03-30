package com.example.monitoringapi.configs;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.monitoringapi.filter.JwtHeaderFilter;
import com.example.monitoringapi.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {
    
    /* 
        This class is used for Web security configuration and user needs to have valid 
        JWT token to access any of the endpoints from this component
    */

    private final JwtHeaderFilter jwtHeaderFilter;

    private final MonitoringApiAuthenticationProvider monitoringApiAuthenticationProvider;

    private final UserDetailsServiceImpl userDetailsService;

    private String[] publicUrls = { "/actuator/health" };

    @Autowired
    public WebConfig(JwtHeaderFilter jwtHeaderFilter, MonitoringApiAuthenticationProvider monitoringApiAuthenticationProvider,
                UserDetailsServiceImpl userDetailsService) {
        this.jwtHeaderFilter = jwtHeaderFilter;
        this.monitoringApiAuthenticationProvider = monitoringApiAuthenticationProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(monitoringApiAuthenticationProvider)
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
