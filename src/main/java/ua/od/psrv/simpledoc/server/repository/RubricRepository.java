package ua.od.psrv.simpledoc.server.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.od.psrv.simpledoc.server.models.data.Rubric;


public interface RubricRepository extends JpaRepository<Rubric, Integer>{
       
    Rubric findByName(String name);
   
    List<Rubric> findByParent(Rubric parent);

    List<Rubric> findByParentOrderByNodeAscNameAsc(Rubric parent);
    
    @Query("select r from Rubric r where r.node=true order by r.name")
    List<Rubric> findNodes();
   

}
