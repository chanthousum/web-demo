//package com.setec.configurations;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.builder().username("user").password(passwordEncoder.encode("123@")).roles("USER").build();
//        UserDetails admin=User.builder().username("admin").password(passwordEncoder.encode("123@")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/").permitAll()
//                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(
//                        form->form
//                                //.loginPage("/login")
//                                .defaultSuccessUrl("/",true)
//                )
//                .logout(
//                        logout -> logout
//                                .logoutUrl("/logout")
//                                .logoutSuccessUrl("/login?logout")
//                                .invalidateHttpSession(true)
//                                .clearAuthentication(true)
//                                .deleteCookies("JSESSIONID")
//                )
//        ;
//        return http.build();
//    }
//}
