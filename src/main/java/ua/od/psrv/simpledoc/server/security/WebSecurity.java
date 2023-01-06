package ua.od.psrv.simpledoc.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {
       
    /**
     * @param http
     * @return
     * @throws Exception
     */
    @Bean 
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {    
        http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers(
                    "/webjars/**",
                    "/js/**", "/css/**", "/images/**",
                    "/","/index",
                    "/user/create","/user/login"
                    ).permitAll()
                .anyRequest().authenticated()
            ) 
            .httpBasic();
        return http.build();
    }
}
