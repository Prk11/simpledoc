package ua.od.psrv.simpledoc.server.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import ua.od.psrv.simpledoc.server.models.data.Docgroup;
import ua.od.psrv.simpledoc.server.repository.DocgroupRepository;

@Service
public class VariableService {

    @Autowired
    private DocgroupRepository docgroups;

    @Bean
    public List<Docgroup> Docgroups() {
        return docgroups.findNodes();
    }
}
