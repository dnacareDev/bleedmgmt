package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DataController {

    @RequestMapping("management-resource")
    public ModelAndView manageResource(ModelAndView mv) {
        mv.setViewName("resource/data_resource");
        return mv;
    }

    @RequestMapping("regist-resource")
    public ModelAndView registResourcePage(ModelAndView mv) {
        mv.setViewName("resource/regist_resource");
        return mv;
    }
}
