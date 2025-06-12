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
import ua.od.psrv.simpledoc.server.models.data.Citizencategory;
import ua.od.psrv.simpledoc.server.repository.CitizencategoryRepository;

@Log
@Controller
@RequestMapping("/guides/socialcategory")
public class SocialcategoryController {

    private final String rootfolder = "guides/socialcategory";

    @Autowired
    private CitizencategoryRepository repository;

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
        mav.addObject("edit",true);
        mav.addObject("item",new Citizencategory());
        return mav;
    }

    @GetMapping("edit/{id}")
    public ModelAndView editAction(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit", id);
        return mav;
    }

    @PostMapping("save")
    public ModelAndView saveAction(@RequestParam Map<String,String> model) {
        if (model.containsKey("Id")) {
            try {
                Citizencategory item;
                Integer id = Integer.parseInt(model.get("Id"));
                String name = model.get("Name");
                Optional<Citizencategory> itemOptional = repository.findById(id);
                if (itemOptional.isPresent() && id>0) {
                    item = itemOptional.get();
                    item.setName(name);
                }
                else {
                    item = new Citizencategory();
                    item.setName(name);
                }
                repository.save(item);
            }
            catch  (Exception ex) { 
                log.warning(ex.getMessage());
            }
        }
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit",false);
        return mav;
    }
    
    @GetMapping("delete/{id}")
    public ModelAndView deleteAction(@PathVariable Integer id) {
        Optional<Citizencategory> itemOptional = repository.findById(id);
        if (itemOptional.isPresent()) {
            Citizencategory item = itemOptional.get();
            item.setDeleted(true);
            repository.save(item);
        }
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit",false);
        return mav;
    }
    
    @GetMapping("restore/{id}")
    public ModelAndView restoreAction(@PathVariable Integer id) {
        Optional<Citizencategory> itemOptional = repository.findById(id);
        if (itemOptional.isPresent()) {
            Citizencategory item = itemOptional.get();
            item.setDeleted(false);
            repository.save(item);
        }
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            repository.findAll()
        );
        mav.addObject("edit",false);

        return mav;
    }
    
}
