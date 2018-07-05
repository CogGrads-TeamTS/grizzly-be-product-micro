package com.ts.product.Security;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;
    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http).cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/cart").permitAll()
                .antMatchers(HttpMethod.DELETE, "/{id}", "/image/delete").hasAuthority("admin")
                .antMatchers(HttpMethod.PUT, "/edit/{id}", "/edit/{productId}/images").hasAuthority("admin")
                .antMatchers(HttpMethod.POST, "/add", "/{productId}/images/add").hasAuthority("admin")
                .anyRequest().permitAll();
    }
}