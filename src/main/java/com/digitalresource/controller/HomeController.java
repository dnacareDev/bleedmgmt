package com.digitalresource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class HomeController {

  @GetMapping("/home")
  public ModelAndView getHome(ModelAndView mv) {

    mv.setViewName("home/resource_home");

    return mv;
  }

}
