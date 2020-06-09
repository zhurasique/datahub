package com.space.datahub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TemplateController {

    @RequestMapping("/")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
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
}
