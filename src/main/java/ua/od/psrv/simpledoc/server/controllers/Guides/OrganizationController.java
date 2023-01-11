package ua.od.psrv.simpledoc.server.controllers.Guides;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.java.Log;
import ua.od.psrv.simpledoc.server.models.data.Organization;
import ua.od.psrv.simpledoc.server.repository.OrganizationRepository;

/**
 * Контроллер для довідника підприємств
 */
@Log
@Controller
@RequestMapping("/guides/organization")
public class OrganizationController {
    private final String rootfolder = "guides/organization";

    @Autowired
    private OrganizationRepository repository;

    @GetMapping({"/", "list", "index"})
    public ModelAndView listAction() {
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit",false);

        return mav;
    }

    @GetMapping("new")
    public ModelAndView newAction() {
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        return mav;
    }

    @GetMapping("edit/{id}")
    public ModelAndView editAction(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject("edit", id);
        mav.addObject(
            "list", 
            repository.findAll()
        );
        return mav;
    }

    @PostMapping("save")
    public ModelAndView saveAction(
        @RequestParam Optional<Long> Id,
        @RequestParam Optional<String> Name,
        @RequestParam Optional<String> Code
        ) 
    {
        ModelAndView mav = new ModelAndView("redirect:/"+rootfolder+"/");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        try {
            Organization itemOrganization = new Organization();
            if (Id.isPresent()) {
                mav.addObject("edit", Id.get());
                Optional<Organization> item = repository.findById(Id.get());
                if (item.isPresent()) {
                    itemOrganization = item.get();
                }
            }
            if (Name.isPresent()) itemOrganization.setName(Name.get());
            if (Code.isPresent()) itemOrganization.setCode(Code.get());
            itemOrganization=repository.save(itemOrganization);
            log.info("Запис збережено! Id="+itemOrganization.getId());
        }
        catch (Exception ex) {
            log.warning(ex.getMessage());
        }
        return mav;
    }

    @GetMapping("delete/{id}")
    public ModelAndView deleteAction(@PathVariable Optional<Long> id) {
        ModelAndView mav = new ModelAndView("redirect:/"+rootfolder+"/");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        try {
            Organization itemOrganization = new Organization();
            if (id.isPresent()) {
                mav.addObject("edit", id.get());
                Optional<Organization> item = repository.findById(id.get());
                if (item.isPresent()) {
                    itemOrganization = item.get();
                }
            }
            itemOrganization.setDeleted(true);
            itemOrganization=repository.save(itemOrganization);
            log.info("Запис збережено! Id="+itemOrganization.getId());
        }
        catch (Exception ex) {
            log.warning(ex.getMessage());
        }

        return mav;
    }

    @GetMapping("restore/{id}")
    public ModelAndView restoreAction(@PathVariable Optional<Long> id) {
        ModelAndView mav = new ModelAndView("redirect:/"+rootfolder+"/");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        try {
            Organization itemOrganization = new Organization();
            if (id.isPresent()) {
                mav.addObject("edit", id.get());
                Optional<Organization> item = repository.findById(id.get());
                if (item.isPresent()) {
                    itemOrganization = item.get();
                }
            }
            itemOrganization.setDeleted(false);
            itemOrganization=repository.save(itemOrganization);
            log.info("Запис збережено! Id="+itemOrganization.getId());
        }
        catch (Exception ex) {
            log.warning(ex.getMessage());
        }        
        return mav;
    }

    

}
