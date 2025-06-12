package ua.od.psrv.simpledoc.server.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.od.psrv.simpledoc.server.models.data.Docgroup;
import ua.od.psrv.simpledoc.server.models.data.Numberremove;

public interface NumberremoveRepository extends JpaRepository<Numberremove,Long> {
    
    Set<Numberremove> findByDocgroupAndSectionIgnoreCaseOrderByNumDesc(Docgroup docgroup, String section);
}
