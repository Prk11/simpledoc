package ua.od.psrv.simpledoc.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.od.psrv.simpledoc.server.models.data.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
   
}
