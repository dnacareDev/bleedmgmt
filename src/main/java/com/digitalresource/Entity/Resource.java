package com.digitalresource.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class Resource {
    private int resource_id;
    private int crop_id;
    private int resource_name_id;
    private String resource_name;
    private int detailCount;
    private int resource_use;

    private String create_date;
    private String modify_date;

    private int character_id;

//    private List<Detail> detailList;
    private String detailList;
    
    private String trait_id;

    private String resource_template;
    private int resource_template_id;
    private String resource_character_template_file;
    private int detail_count;
    private int resource_name_count;
    private int detail_id;
}
