package ua.od.psrv.simpledoc.server.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("create")
    public String create(){
        return "user/create";
    }

    @GetMapping("list")
    public String list(){
        return "user/list";
    }

}
