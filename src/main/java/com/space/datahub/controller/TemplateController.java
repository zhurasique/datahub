package com.space.datahub.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TemplateController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

//    @RequestMapping("/error")
//    public String handleError() {
//        return "404";
//    }

    @RequestMapping("/")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView login () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping("/registration")
    public ModelAndView registration () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping("/dzial/motoryzacja")
    public ModelAndView cars () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cars");
        return modelAndView;
    }

    @RequestMapping("/dzial/elektronika")
    public ModelAndView elect () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("elect");
        return modelAndView;
    }

    @RequestMapping("/dzial/moda")
    public ModelAndView pretty () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pretty");
        return modelAndView;
    }

    @RequestMapping("/dzial/dom-i-ogrod")
    public ModelAndView house () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("house");
        return modelAndView;
    }

    @RequestMapping("/products")
    public ModelAndView type () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products");
        return modelAndView;
    }

    @RequestMapping("/admin")
    public ModelAndView admin () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}
