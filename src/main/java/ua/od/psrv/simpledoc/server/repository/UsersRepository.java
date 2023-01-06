package ua.od.psrv.simpledoc.server.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import ua.od.psrv.simpledoc.server.models.data.Userentry;

public interface UsersRepository extends CrudRepository<Userentry, Integer> {

    Optional<Userentry> findByUsername(String username);

    void save(UserDetails user);
    
}
