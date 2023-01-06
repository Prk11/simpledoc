package ua.od.psrv.simpledoc.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.od.psrv.simpledoc.server.models.data.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery,Integer>{
    
}
