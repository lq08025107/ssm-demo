package com.sdt.user.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope(value="prototype")
@RequestMapping("user")
public class UserCoreController {

    @RequestMapping(value = "demo", method = RequestMethod.GET)
    public ModelAndView userDemo(){
        System.out.println("success");
        return new ModelAndView("user/demo");

    }
}
