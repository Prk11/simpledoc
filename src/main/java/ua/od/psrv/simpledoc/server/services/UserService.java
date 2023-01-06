package ua.od.psrv.simpledoc.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ua.od.psrv.simpledoc.server.models.data.Userentry;
import ua.od.psrv.simpledoc.server.repository.UsersRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UsersRepository users;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) 
        throws UsernameNotFoundException 
    {
        Optional<Userentry> user = users.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return user.get();
    }
    
}
