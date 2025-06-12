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
import ua.od.psrv.simpledoc.server.models.data.Rubric;
import ua.od.psrv.simpledoc.server.repository.RubricRepository;

@Controller
@RequestMapping("/guides/rubric")
@Log
public class RubricController {

    private final String rootfolder = "guides/rubric";

    @Autowired
    private RubricRepository rubricRepository;

    @GetMapping({ "/", "list", "/{parent}", "list/{parent}" })
    public ModelAndView listRubric(@PathVariable Optional<Integer> parent) {
        ModelAndView mav = new ModelAndView(rootfolder + "/list");
        mav.addObject("state", "view");
        Optional<Rubric> rubricParent = Optional.empty();
        if (parent.isPresent()) {
            if (parent.get() != -1)
                rubricParent = rubricRepository.findById(parent.get());
        }

        if (rubricParent.isEmpty()) {
            mav.addObject("rubrics", rubricRepository.findByParentOrderByNodeAscNameAsc(null));
            mav.addObject("oparent", null);
        } else {
            mav.addObject("rubrics", rubricRepository.findByParentOrderByNodeAscNameAsc(rubricParent.get()));
            mav.addObject("oparent", rubricParent.get());
        }
        return mav;
    }

    @GetMapping({ "/new", "new/{parent}" })
    public ModelAndView newRubric(
            @PathVariable Optional<Integer> parent) {
        Integer parentId = parent.isEmpty() ? -1 : parent.get();
        ModelAndView mav = new ModelAndView(rootfolder + "/list");
        mav.addObject("state", "edit");
        Rubric oedit;
        Optional<Rubric> rubricParent = Optional.empty();
        if (parentId > 0)
            rubricParent = rubricRepository.findById(parentId);
        if (rubricParent.isEmpty()) {
            mav.addObject("rubrics", rubricRepository.findByParentOrderByNodeAscNameAsc(null));
            mav.addObject("oparent", null);
            oedit = new Rubric();
        } else {
            mav.addObject("rubrics", rubricRepository.findByParentOrderByNodeAscNameAsc(rubricParent.get()));
            mav.addObject("oparent", rubricParent.get());
            oedit = new Rubric(rubricParent.get());
        }
        mav.addObject("oedit", oedit);
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editRubric(@PathVariable Optional<Integer> id) {
        log.info("start edit");
        Optional<Rubric> rubricEdit = Optional.empty();
        if (id.isPresent())
            rubricEdit = rubricRepository.findById(id.get());
        if (rubricEdit.isEmpty()) {
            ModelAndView mav = new ModelAndView("redirect:/list");
            mav.addObject("rubrics", rubricRepository.findByParentOrderByNodeAscNameAsc(null));
            mav.addObject("oparent", null);
            mav.addObject("state", "view");
            return mav;
        }
        Rubric oItem = rubricEdit.get();
        Rubric oParent = oItem.getParent();
        ModelAndView mav = new ModelAndView(rootfolder + "/list");
        mav.addObject("rubrics", rubricRepository.findByParentOrderByNodeAscNameAsc(oParent));
        mav.addObject("oparent", oParent);
        mav.addObject("state", "edit");
        mav.addObject("edit", oItem.getId());
        mav.addObject("oedit", oItem);
        log.info("end edit");
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView saveRubric(@RequestParam Map<String, String> model) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(model);
            log.info(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String name = model.get("Name").toString();
        String code = model.get("Code").toString();
        String node = model.get("Node").toString();
        Rubric oItem;
        if (!model.get("Id").equals("-1")) {
            Integer edit = Integer.parseInt(model.get("Id"));
            Optional<Rubric> rubricEdit = rubricRepository.findById(edit);
            oItem = rubricEdit.get();
        } else {
            String sParentId = model.get("ParentId");
            Optional<Rubric> rubricParent;
            if (sParentId == "" || sParentId == "-1") {
                oItem = new Rubric();
            } else {
                Integer iParentId = Integer.parseInt(sParentId);
                rubricParent = rubricRepository.findById(iParentId);
                if (rubricParent.isEmpty())
                    oItem = new Rubric();
                else
                    oItem = new Rubric(rubricParent.get());
            }
        }

        oItem.setCode(code);
        oItem.setName(name);
        Boolean b_node = Boolean.parseBoolean(node);
        oItem.setNode(b_node);
        oItem = rubricRepository.save(oItem);
        Rubric oParent = oItem.getParent();
        ModelAndView mav = new ModelAndView(rootfolder + "/list");
        mav.addObject("state", "view");
        mav.addObject("rubrics", rubricRepository.findByParentOrderByNodeAscNameAsc(oParent));
        mav.addObject("oparent", oParent);
        mav.addObject("edit", oItem.getId());
        return mav;
    }

    @GetMapping("delete/{delete}")
    public ModelAndView deleteRubric(@PathVariable Integer delete) {
        Optional<Rubric> rubricDelete = rubricRepository.findById(delete);
        if (rubricDelete.isEmpty()) {
            return new ModelAndView("redirect:" + rootfolder + "/list");
        }
        Rubric oItem = rubricDelete.get();
        Rubric oParent = oItem.getParent();
        ModelAndView mav = new ModelAndView(rootfolder + "/list");
        mav.addObject("state", "view");
        mav.addObject("rubrics", rubricRepository.findByParentOrderByNodeAscNameAsc(oParent));
        mav.addObject("oparent", oParent);
        mav.addObject("delete", delete);
        oItem.setDeleted(true);
        rubricRepository.save(oItem);
        return mav;
    }

    @GetMapping("restore/{restore}")
    public ModelAndView restoreRubric(@PathVariable Integer restore) {
        Optional<Rubric> rubricRestore = rubricRepository.findById(restore);
        if (rubricRestore.isEmpty()) {
            return new ModelAndView("redirect:" + rootfolder + "/list");
        }
        Rubric oItem = rubricRestore.get();
        oItem.setDeleted(true);
        rubricRepository.save(oItem);
        Rubric oParent = oItem.getParent();
        ModelAndView mav = new ModelAndView(rootfolder + "/list");
        mav.addObject("state", "view");
        mav.addObject("rubrics", rubricRepository.findByParentOrderByNodeAscNameAsc(oParent));
        mav.addObject("oparent", oParent);
        mav.addObject("delete", restore);
        oItem.setDeleted(false);
        rubricRepository.save(oItem);
        return mav;

    }

}
