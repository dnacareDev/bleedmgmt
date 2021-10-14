package com.digitalresource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

  @GetMapping("login")
  public ModelAndView getLoginPage(ModelAndView mv) {

    mv.setViewName("account/login");

    return mv;
  }

}
