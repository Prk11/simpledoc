package ua.od.psrv.simpledoc.server.controllers.Guides;

import java.util.ArrayList;
import java.util.List;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;
import ua.od.psrv.simpledoc.server.repository.CitizencategoryRepository;
import ua.od.psrv.simpledoc.server.repository.CitizenstatusRepository;
import ua.od.psrv.simpledoc.server.controllers.PrepareController;
import ua.od.psrv.simpledoc.server.models.SelectedCitizencategory;
import ua.od.psrv.simpledoc.server.models.SelectedCitizenstatus;
import ua.od.psrv.simpledoc.server.models.data.Citizen;
import ua.od.psrv.simpledoc.server.repository.CitizenRepository;

@Log
@Controller
@RequestMapping("/guides/citizen")
public class CitizenController extends PrepareController {

    private final String rootfolder = "guides/citizen";

    @Autowired
    private CitizenRepository repository;

    @Autowired
    private CitizenstatusRepository repositoryStatuses;

    @Autowired
    private CitizencategoryRepository repositoryCategories;

    @GetMapping({"/", "list", "index"})
    public ModelAndView listAction() {
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit",false);
        prepareNewBundle(mav);
        return mav;
    }

    @GetMapping("new")
    public ModelAndView newAction() {
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        prepareNewBundle(mav);       
        getPrestyle().add("tags");
        getPostscript().add("tags");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit",true);
        mav.addObject("item",new Citizen());
        List<SelectedCitizenstatus> selectStatuses = new ArrayList<>();
        repositoryStatuses.findAll().forEach(
            (item) -> {
                SelectedCitizenstatus selectedCitizenstatus = new SelectedCitizenstatus(item);                
                selectStatuses.add(selectedCitizenstatus);
            }
        );
        mav.addObject(
            "listStatuses", 
            selectStatuses
        );

        List<SelectedCitizencategory> selectCategories = new ArrayList<>();
        repositoryCategories.findAll().forEach(
            (item) -> {
                SelectedCitizencategory selectedCitizencategory = new SelectedCitizencategory(item);
                selectCategories.add(selectedCitizencategory);
            }
        );
        mav.addObject(
            "listCategories", 
            selectCategories
        );

        return mav;
    }

    /**
     * @param id
     * @return
     */
    @GetMapping("edit/{id}")
    public ModelAndView editAction(@PathVariable Long id) {
        Optional<Citizen> itemOptional = repository.findById(id);
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        prepareNewBundle(mav);
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit", id);
        // getPrestyle().add("tags");
        // getPostscript().add("tags");

        List<SelectedCitizenstatus> selectStatuses = new ArrayList<>();
        repositoryStatuses.findAll().forEach(
            (item) -> {
                SelectedCitizenstatus selectedCitizenstatus = new SelectedCitizenstatus(item);
                if (itemOptional.isPresent() && itemOptional.get().getStatus().contains(item)) {
                    selectedCitizenstatus.setSelected(true);
                }
                selectStatuses.add(selectedCitizenstatus);
            }
        );
        mav.addObject(
            "listStatuses", 
            selectStatuses
        );

        List<SelectedCitizencategory> selectCategories = new ArrayList<>();
        repositoryCategories.findAll().forEach(
            (item) -> {
                SelectedCitizencategory selectedCitizencategory = new SelectedCitizencategory(item);
                if (itemOptional.isPresent() && itemOptional.get().getCategory().contains(item)) {
                    selectedCitizencategory.setSelected(true);
                }
                selectCategories.add(selectedCitizencategory);
            }
        );
        mav.addObject(
            "listCategories", 
            selectCategories
        );
            
        return mav;
    }

    /**
     * @param model
     * @return
     */
    @PostMapping("save")
    public ModelAndView saveAction(@RequestParam Map<String,Object> model) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(model);
            log.info(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (model.containsKey("Id")) {
            try {
                Citizen item;
                Long id = Long.parseLong(model.get("Id").toString());
                String fullname = model.get("Fullname").toString();
                String address = model.get("Address").toString();
                Optional<Citizen> itemOptional = repository.findById(id);
                if (itemOptional.isPresent() && id>0) {
                    item = itemOptional.get();
                }
                else {
                    item = new Citizen();
                }
                Object _Categories = model.get("Category");
                // List<SelectedCitizencategory> selectCategories = new ArrayList<>();
                // if (_Categories instanceof Collection) {
                // selectCategories = new ArrayList<>((Collection<SelectedCitizencategory>)_Categories);
                // }
                log.info(_Categories.toString()+"="+_Categories.getClass().getName());
                item.setFullname(fullname);
                item.setAddress(address);
                repository.save(item);
            }
            catch  (Exception ex) { 
                log.warning(ex.getMessage());
            }
        }
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        prepareNewBundle(mav);
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit",false);
        return mav;
    }
    
    @GetMapping("delete/{id}")
    public ModelAndView deleteAction(@PathVariable Long id) {
        Optional<Citizen> itemOptional = repository.findById(id);
        if (itemOptional.isPresent()) {
            Citizen item = itemOptional.get();
            item.setDeleted(true);
            repository.save(item);
        }
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        prepareNewBundle(mav);
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit",false);
        return mav;
    }
    
    @GetMapping("restore/{id}")
    public ModelAndView restoreAction(@PathVariable Long id) {
        Optional<Citizen> itemOptional = repository.findById(id);
        if (itemOptional.isPresent()) {
            Citizen item = itemOptional.get();
            item.setDeleted(false);
            repository.save(item);
        }
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        prepareNewBundle(mav);
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit",false);

        return mav;
    }
    
}
