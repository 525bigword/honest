package com.xr.run.service;

import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

public interface StaticHtmlService {
    void genHtmlPage(String destpath,ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,String filename);
    void deleteHtmlPage(String fileName);
}
