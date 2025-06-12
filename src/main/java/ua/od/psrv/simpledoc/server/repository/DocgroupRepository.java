package ua.od.psrv.simpledoc.server.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.od.psrv.simpledoc.server.models.data.Docgroup;


public interface DocgroupRepository extends JpaRepository<Docgroup, Integer>{
       
    Docgroup findByName(String name);
   
    List<Docgroup> findByParent(Docgroup parent);

    List<Docgroup> findByParentOrderByNodeAscNameAsc(Docgroup parent);
    
    @Query("select d from Docgroup d where d.node=true order by d.name")
    List<Docgroup> findNodes();
   

}
