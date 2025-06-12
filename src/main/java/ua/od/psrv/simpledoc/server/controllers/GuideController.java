package ua.od.psrv.simpledoc.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guides")
public class GuideController {
    private final String rootfolder = "guides";

    @GetMapping({"/","list"})
    public String list(){
        return rootfolder+"/list";
    }

}
