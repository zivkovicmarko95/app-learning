package com.example.productapi.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.productapi.filter.JwtHeaderFilter;
import com.example.productapi.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {

    /* 
        This class is used for Web security configuration and user needs to have valid 
        JWT token to access any of the endpoints from this component
    */

    @Autowired
    private JwtHeaderFilter jwtHeaderFilter;

    @Autowired
    private ProductApiAuthenticationProvider productApiAuthenticationProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(productApiAuthenticationProvider)
            .userDetailsService(userDetailsService);
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
            .authorizeRequests().antMatchers(HttpMethod.GET, "/api/products/**").permitAll()
            .antMatchers(HttpMethod.DELETE, "/api/products/**").hasRole("SUPER_ADMIN")
            .antMatchers(HttpMethod.POST, "/api/products").hasAnyRole("SUPER_ADMIN", "ADMIN")
            .antMatchers("/actuator/health").permitAll()
            .anyRequest().authenticated();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
