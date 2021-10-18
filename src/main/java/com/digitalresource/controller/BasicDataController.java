package com.digitalresource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/basic_data")
public class BasicDataController {

  @GetMapping("target")
  public ModelAndView getTargetList(ModelAndView mv) {

    mv.setViewName("/basic_data/target");

    return mv;
  }

  @GetMapping("target/insert")
  public ModelAndView getTargetInsert(ModelAndView mv) {

    mv.setViewName("/basic_data/new_target");

    return mv;
  }

  @GetMapping("seed_spec")
  public ModelAndView getSeedSpecList(ModelAndView mv) {

    mv.setViewName("/basic_data/seed_spec");

    return mv;
  }

  @GetMapping("seed_spec/insert")
  public ModelAndView getSeedSpecInsert(ModelAndView mv) {

    mv.setViewName("/basic_data/new_seed_spec");

    return mv;
  }

}
