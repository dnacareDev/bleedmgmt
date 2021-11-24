package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CharacterController {

    @RequestMapping("character")
    public ModelAndView characterList(ModelAndView mv){
        mv.setViewName("resource/Character_list");
        return mv;
    }
}
