package ua.od.psrv.simpledoc.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.od.psrv.simpledoc.server.models.data.Citizenstatus;

public interface CitizenstatusRepository  extends JpaRepository<Citizenstatus,Integer> {
    
}
