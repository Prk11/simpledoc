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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.java.Log;
import ua.od.psrv.simpledoc.server.models.data.Docgroup;
import ua.od.psrv.simpledoc.server.models.data.DocgroupKind;
import ua.od.psrv.simpledoc.server.repository.DocgroupRepository;

@Controller
@RequestMapping("/guides/docgroup")
@Log
public class DocgroupController {

    private final String rootfolder = "guides/docgroup";

    @Autowired
    private DocgroupRepository docgroupRepository;

    @GetMapping({"/", "list", "/{parent}", "list/{parent}"})
    public ModelAndView listDocgroup(@PathVariable Optional<Integer> parent){       
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject("state", "view");
        Optional<Docgroup> docgroupParent = Optional.empty();
        if (!parent.isEmpty())
            docgroupParent = docgroupRepository.findById(parent.get());
        if (docgroupParent.isEmpty()) {
            mav.addObject("docgroups", docgroupRepository.findByParentOrderByNodeAscNameAsc(null));
            mav.addObject("oparent",null);
        }
        else {
            mav.addObject("docgroups", docgroupRepository.findByParentOrderByNodeAscNameAsc(docgroupParent.get()));
            mav.addObject("oparent",docgroupParent.get());
        }
        return mav;
    }

    @GetMapping({"/new","new/{parent}"})
    public ModelAndView newDocgroup(
        @PathVariable Optional<Integer> parent
        )
        {                     
            Integer parentId = parent.isEmpty()?-1:parent.get();           
            ModelAndView mav = new ModelAndView(rootfolder+"/list");
            mav.addObject("state", "edit");
            Docgroup oedit;
            Optional<Docgroup> docgroupParent = Optional.empty();
            if (parentId>0)
                docgroupParent = docgroupRepository.findById(parentId);
            if (docgroupParent.isEmpty()) {
                mav.addObject("docgroups", docgroupRepository.findByParentOrderByNodeAscNameAsc(null));
                mav.addObject("oparent",null);    
                oedit = new Docgroup();            
            }
            else {
                mav.addObject("docgroups", docgroupRepository.findByParentOrderByNodeAscNameAsc(docgroupParent.get()));
                mav.addObject("oparent",docgroupParent.get());
                oedit = new Docgroup(docgroupParent.get());
            }
            mav.addObject("oedit",oedit);
        return mav;
    }

    @GetMapping("edit/{edit}")
    public ModelAndView editDocgroup(@PathVariable Integer edit){       
        Optional<Docgroup> docgroupEdit = docgroupRepository.findById(edit);
        if (docgroupEdit.isEmpty()) {
            return new ModelAndView("redirect:/guides/docgroup/list");
        }
        Docgroup oItem = docgroupEdit.get();
        Docgroup oParent = oItem.getParent();
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject("docgroups", docgroupRepository.findByParentOrderByNodeAscNameAsc(oParent));
        mav.addObject("oparent",oParent);
        mav.addObject("state", "edit");
        mav.addObject("edit", edit);  
        mav.addObject("oedit", oItem);
        return mav;    
    }

    @PostMapping("save")
    public ModelAndView saveDocgroup(@RequestParam Map<String,String> model)
    {    
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(model);
            log.info(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        DocgroupKind kind = null;
        switch (model.get("Kind")) {
            case "In": {
                kind = DocgroupKind.In;
                break;
            }
            case "Let": {
                kind = DocgroupKind.Let;
                break;
            }
            case "Out": {
                kind = DocgroupKind.Out;
                break;
            }
        }
        String name = model.get("Name").toString();
        String index = model.get("Index").toString();
        String shablon = model.get("Shablon").toString();
        // Long lastnum = Long.parseLong(model.get("Lastnum"));
        // Integer nextnum = Integer.parseInt(model.get("Nextnum"));
        Docgroup oItem;
        if (!model.get("Id").equals("-1")) {
            Integer edit = Integer.parseInt(model.get("Id"));
            Optional<Docgroup> docgroupEdit = docgroupRepository.findById(edit);
            oItem = docgroupEdit.get();
        }
        else {
            String sParentId =  model.get("ParentId");
            Optional<Docgroup> docgroupParent;
            if (sParentId=="" || sParentId=="-1") {
                oItem = new Docgroup();
            }
            else {                                                    
                Integer iParentId = Integer.parseInt(sParentId);
                docgroupParent = docgroupRepository.findById(iParentId);              
                if (docgroupParent.isEmpty()) 
                    oItem = new Docgroup();
                else
                    oItem = new Docgroup(docgroupParent.get());
            }
        }
        oItem.setKind(kind); 
        oItem.setIndex(index);
        oItem.setName(name);
        oItem.setShablon(shablon);
        // oItem.setLastnum(lastnum);
        oItem.setNode(kind!=null);
        docgroupRepository.save(oItem);
        Docgroup oParent = oItem.getParent();
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject("state", "view");
        mav.addObject("docgroups", docgroupRepository.findByParentOrderByNodeAscNameAsc(oParent));
        mav.addObject("oparent",oParent);
        mav.addObject("edit", oItem.getId());     
        return mav;    
    }


    @GetMapping("delete/{delete}")
    public ModelAndView deleteDocgroup(@PathVariable Integer delete){  
        Optional<Docgroup> docgroupDelete = docgroupRepository.findById(delete);
        if (docgroupDelete.isEmpty()) {
            return new ModelAndView("redirect:"+rootfolder+"/list");
        }
        Docgroup oItem = docgroupDelete.get();
        Docgroup oParent = oItem.getParent();
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject("state", "view");
        mav.addObject("docgroups", docgroupRepository.findByParentOrderByNodeAscNameAsc(oParent));
        mav.addObject("oparent",oParent);
        mav.addObject("delete", delete);        
        oItem.setDeleted(true);
        docgroupRepository.save(oItem);
        return mav;
    }

    @GetMapping("restore/{restore}")
    public ModelAndView restoreDocgroup(@PathVariable Integer restore){       
        Optional<Docgroup> docgroupRestore = docgroupRepository.findById(restore);
        if (docgroupRestore.isEmpty()) {
            return new ModelAndView("redirect:"+rootfolder+"/list");
        }
        Docgroup oItem = docgroupRestore.get();
        oItem.setDeleted(true);
        docgroupRepository.save(oItem);
        Docgroup oParent = oItem.getParent();
        ModelAndView mav = new ModelAndView(rootfolder+"/list");
        mav.addObject("state", "view");
        mav.addObject("docgroups", docgroupRepository.findByParentOrderByNodeAscNameAsc(oParent));
        mav.addObject("oparent",oParent);
        mav.addObject("delete", restore);        
        oItem.setDeleted(false);
        docgroupRepository.save(oItem);       
        return mav;

    }



}
