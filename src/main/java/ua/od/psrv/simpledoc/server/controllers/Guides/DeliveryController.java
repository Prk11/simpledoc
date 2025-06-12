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
import ua.od.psrv.simpledoc.server.models.data.Delivery;
import ua.od.psrv.simpledoc.server.repository.DeliveryRepository;

@Log
@Controller
@RequestMapping("/guides/delivery")
public class DeliveryController {

    private final String rootfolder = "guides/delivery";

    @Autowired
    private DeliveryRepository deliveryRepository;

    @GetMapping({"/", "list", "index"})
    public ModelAndView listAction() {
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            deliveryRepository.findAll()
        );
        mav.addObject("edit",false);
        return mav;
    }

    @GetMapping("new")
    public ModelAndView newAction() {
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            deliveryRepository.findAll()
        );
        mav.addObject("edit",true);
        mav.addObject("item",new Delivery());
        return mav;
    }

    @GetMapping("edit/{id}")
    public ModelAndView editAction(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            deliveryRepository.findAll()
        );
        mav.addObject("edit", id);
        return mav;
    }

    @PostMapping("save")
    public ModelAndView saveAction(@RequestParam Map<String,String> model) {
        if (model.containsKey("Id")) {
            try {
                Delivery item;
                Integer id = Integer.parseInt(model.get("Id"));
                String name = model.get("Name");
                Optional<Delivery> delivery = deliveryRepository.findById(id);
                if (delivery.isPresent() && id>0) {
                    item = delivery.get();
                    item.setName(name);
                }
                else {
                    item = new Delivery();
                    item.setName(name);
                }
                deliveryRepository.save(item);
            }
            catch  (Exception ex) { 
                log.warning(ex.getMessage());
            }
        }
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            deliveryRepository.findAll()
        );
        mav.addObject("edit",false);
        return mav;
    }
    
    @GetMapping("delete/{id}")
    public ModelAndView deleteAction(@PathVariable Integer id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isPresent()) {
            Delivery item = delivery.get();
            item.setDeleted(true);
            deliveryRepository.save(item);
        }
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            deliveryRepository.findAll()
        );
        mav.addObject("edit",false);
        return mav;
    }
    
    @GetMapping("restore/{id}")
    public ModelAndView restoreAction(@PathVariable Integer id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        if (delivery.isPresent()) {
            Delivery item = delivery.get();
            item.setDeleted(false);
            deliveryRepository.save(item);
        }
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject(
            "list", 
            deliveryRepository.findAll()
        );
        mav.addObject("edit",false);

        return mav;
    }
    
}
