package com.digitalresource.Controller;

import com.digitalresource.Entity.Crop;
import com.digitalresource.Service.Cropservice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CropController {
    private Cropservice cropService;

    @PostMapping("/regist-crop")
    @ResponseBody
    public boolean registCrop(@ModelAttribute Crop crop){
        boolean result = cropService.registCrop(crop);
        return false;
    }

    @GetMapping("/crop-list")
    @ResponseBody
    public List<Crop> SelectCropList(){
        List<Crop> cropList = cropService.selectCropList();
        return cropList;
    }
}