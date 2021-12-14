package com.digitalresource.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Character {
    private int character_id;
    private int crop_id;

    private String character_description;
    private String character_year;

    private File character_file;

    private String create_date;
    private String modify_date;
    private int feature_count;
    private int character_file_id;

    private List<Feature> featureList;
}
