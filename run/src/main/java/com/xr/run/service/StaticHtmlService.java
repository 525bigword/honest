package com.xr.run.service;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface StaticHtmlService {
    void genHtmlPage(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response);
    void deleteHtmlPage(String fileName);
}
