package com.space.datahub.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TemplateController implements ErrorController {
    @RequestMapping("/")
    public ModelAndView index () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "404";
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

    @RequestMapping("/motoryzacja")
    public ModelAndView cars () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cars");
        return modelAndView;
    }

    @RequestMapping("/elektronika")
    public ModelAndView elect () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("elect");
        return modelAndView;
    }

    @RequestMapping("/moda")
    public ModelAndView pretty () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pretty");
        return modelAndView;
    }

    @RequestMapping("/dom-i-ogrod")
    public ModelAndView house () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("house");
        return modelAndView;
    }

    @RequestMapping("/products")
    public ModelAndView products () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products");
        return modelAndView;
    }

    @RequestMapping("/product")
    public ModelAndView product () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("product");
        return modelAndView;
    }

    @RequestMapping("/order")
    public ModelAndView order () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("order");
        return modelAndView;
    }

    @RequestMapping("/thank")
    public ModelAndView thank () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("thank");
        return modelAndView;
    }

    @RequestMapping("/myorders")
    public ModelAndView myorders () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("myorders");
        return modelAndView;
    }

    @RequestMapping("/bag")
    public ModelAndView bag () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("bag");
        return modelAndView;
    }

    @RequestMapping("/admin")
    public ModelAndView admin () {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}