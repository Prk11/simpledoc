package ua.od.psrv.simpledoc.server.repository;

import org.springframework.data.repository.CrudRepository;

import ua.od.psrv.simpledoc.server.models.data.Department;

public interface DepartmentRepository extends CrudRepository<Department, Long> {
    
}
