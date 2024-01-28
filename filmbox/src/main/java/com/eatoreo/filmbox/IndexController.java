package com.eatoreo.filmbox;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class IndexController {

    @GetMapping("/")
    public ModelAndView Index() {
        var m = new ModelAndView("index");
        return m;
    }

    @GetMapping("/dbh")
    public ModelAndView getMethodName() {
        var m = new ModelAndView("filminfo");
        return m;
    }
}
