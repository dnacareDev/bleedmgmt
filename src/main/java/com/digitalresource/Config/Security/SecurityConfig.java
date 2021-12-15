package com.digitalresource.Config.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig
{
//	@Autowired
//	private AdminServiceImpl adminService;

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @Order(1)
    public static class AdminSecurityConfig extends WebSecurityConfigurerAdapter
    {
        private final AuthenticationSuccessHandler successHandler;
        private final AuthenticationProvider provider;

        @Autowired
        public AdminSecurityConfig(CustomAuthenticationProvider authenticationProvider, CustomAuthenticationSuccessHandler authenticationSuccessHandler)
        {
            this.provider = authenticationProvider;
            this.successHandler = authenticationSuccessHandler;
        }

        protected void configure(HttpSecurity http) throws Exception
        {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/join").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("user_username")
                    .passwordParameter("user_password")
                    .successHandler(successHandler)
                    .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .permitAll();
        }

        // 대조
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception
        {
//			auth.userDetailsService(adminService).passwordEncoder(passwordEncoder());
            auth.authenticationProvider(provider);
        }

        @Override
        public void configure(WebSecurity web) throws Exception
        {
            web.ignoring().antMatchers("/assets/**", "/css/**", "/img/**", "/dist/**", "/upload/**");
        }
    }

    @Configuration
    @Order(2)
    public static class RestSecurityConfig extends WebSecurityConfigurerAdapter
    {
        private final AuthenticationSuccessHandler successHandler;
        private final AuthenticationProvider provider;

        @Autowired
        public RestSecurityConfig(CustomAuthenticationProvider authenticationProvider, CustomAuthenticationSuccessHandler authenticationSuccessHandler)
        {
            this.provider = authenticationProvider;
            this.successHandler = authenticationSuccessHandler;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception
        {

        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception
        {
            return super.authenticationManagerBean();
        }
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect()
    {
        return new SpringSecurityDialect();
    }
}