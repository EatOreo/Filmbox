package com.eatoreo.filmbox;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String Index(Model model) {
        model.addAttribute("name", "Thymeleaf");
        return "index";
    }
}
