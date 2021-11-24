package com.digitalresource.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CharacterController {

    @RequestMapping("character")
    public ModelAndView characterList(ModelAndView mv){
        mv.setViewName("resource/Character_list");
        return mv;
    }

    @PostMapping("search-character")
    @ResponseBody
    public Map<String, Object> searchCharacter(@RequestParam("sample_name") String sample_name, @RequestParam("page_num") int page_num, @RequestParam("limit") int limit){
        Map<String,Object> map = new HashMap<>();
        return map;
    }
}
