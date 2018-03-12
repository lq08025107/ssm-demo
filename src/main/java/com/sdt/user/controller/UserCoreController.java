package com.sdt.user.controller;

import com.sdt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@Scope(value="prototype")
@RequestMapping("user")
public class UserCoreController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "demo", method = RequestMethod.GET)
    public ModelAndView userDemo(ModelMap map){
        //map.put("name", "liuqiang");
        map.put("name", userService.findUserByEmail("test@test.com").getNickname());
        return new ModelAndView("user/demo");
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView userIndex(){
        return new ModelAndView("user/index");
    }
}
