package org.linfa.monitor.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
//    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
