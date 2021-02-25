package productapi.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import productapi.filter.JwtHeaderFilter;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    /* 
        This class is used for Web security configuration and user needs to have valid 
        JWT token to access any of the endpoints from this component
    */
    
    private String[] publicUrls = { "/actuator/health", "/api/product/search/**", "/api/product/find/**" };

    @Autowired
    private JwtHeaderFilter jwtHeaderFilter;

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
            .anyRequest().authenticated();
    }

}
