package ua.od.psrv.simpledoc.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.od.psrv.simpledoc.server.models.data.Docgroup;
import ua.od.psrv.simpledoc.server.models.data.Numerator;

public interface NumeratorRepository extends JpaRepository<Numerator, Integer> {
    
    Numerator findByDocgroupAndSectionIgnoreCase(Docgroup docgroup, String section);
    
}
