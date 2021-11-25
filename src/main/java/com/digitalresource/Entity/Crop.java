package com.digitalresource.Entity;

import lombok.Data;

@Data
public class Crop {
    private int crop_id;
    private int category_id;
    private String crop_name;
    private String create_date;
    private String modify_date;
}
