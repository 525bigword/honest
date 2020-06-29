package com.xr.run.controller;

import com.xr.run.service.StaticHtmlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/th")
public class ThymeleafController {
    @Autowired
    private StaticHtmlService staticHtmlService;
    @RequestMapping("/index.html")
    public void index(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ModelAndView modelAndView=new ModelAndView( );
        modelAndView.addObject("name","haozz");
        modelAndView.setViewName("index");
//        map.addAttribute("name","haozz");
        staticHtmlService.genHtmlPage(modelAndView,httpServletRequest,httpServletResponse);
//        return modelAndView;
    }
}
