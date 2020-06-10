package com.space.datahub.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class TemplateController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);


        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "403";
            }
        }

        return "error";
    }

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
