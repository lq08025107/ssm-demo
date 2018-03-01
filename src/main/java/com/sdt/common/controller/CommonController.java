package com.sdt.common.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@Scope(value = "prototype")
@RequestMapping("common")
public class CommonController {

    @RequestMapping("404")
    public ModelAndView _404(HttpServletRequest request){
        ModelAndView view = new ModelAndView("common/404");
        return view;
    }

    @RequestMapping("500")
    public ModelAndView _500(HttpServletRequest request){
        ModelAndView view = new ModelAndView("common/500");
        return view;
    }
}
