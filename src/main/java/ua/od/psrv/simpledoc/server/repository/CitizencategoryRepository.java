package ua.od.psrv.simpledoc.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.od.psrv.simpledoc.server.models.data.Citizencategory;

public interface CitizencategoryRepository  extends JpaRepository<Citizencategory,Integer> {
    
}
