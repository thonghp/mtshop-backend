package com.mtshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired private CustomerOAuth2UserService oAuth2UserService;
//    @Autowired private OAuth2LoginSuccessHandler oauth2LoginHandler;
//    @Autowired private DatabaseLoginSuccessHandler databaseLoginHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/account_details", "/update_account_details", "/orders/**",
//                        "/cart", "/address_book/**", "/checkout", "/place_order", "/reviews/**",
//                        "/process_paypal_order", "/write_review/**", "/post_review").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
//                .successHandler(databaseLoginHandler)
//                .permitAll()
//                .and()
//                .oauth2Login()
//                .loginPage("/login")
//                .userInfoEndpoint()
//                .userService(oAuth2UserService)
//                .and()
//                .successHandler(oauth2LoginHandler)
//                .and()
//                .logout().permitAll()
//                .and()
//                .rememberMe()
//                .key("1234567890_aBcDeFgHiJkLmNoPqRsTuVwXyZ")
//                .tokenValiditySeconds(14 * 24 * 60 * 60)
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//        ;
        http.authorizeRequests().anyRequest().permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
