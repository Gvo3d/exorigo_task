//package org.yakimovdenis.exorigo_task.configuration;
//
///**
// * Created by Gvozd on 31.12.2016.
// */
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").failureUrl("/login?error").defaultSuccessUrl("/", false)
//                .and().logout().logoutSuccessUrl("/login?logout").invalidateHttpSession(true).logoutUrl("/logout").logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .and().authorizeRequests()
//                .antMatchers("/fileapi/**").hasAnyAuthority("USER", "ADMIN")
//                .antMatchers("/res/**").permitAll()
//                .antMatchers("/dynamic/**").permitAll()
//                .antMatchers("/", "/home").permitAll()
//                .antMatchers("/registration").anonymous()
//                .antMatchers("/admin").hasAuthority("ADMIN")
//                .antMatchers("/useronly").hasAnyAuthority("USER", "ADMIN");
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }
//
//    @Bean
//    BCryptPasswordEncoder encoder() {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
//        return encoder;
//    }
//
//    @Bean
//    DaoAuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setPasswordEncoder(encoder());
//        authenticationProvider.setUserDetailsService(userDetailsService());
//        return authenticationProvider;
//    }
//
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }
//}