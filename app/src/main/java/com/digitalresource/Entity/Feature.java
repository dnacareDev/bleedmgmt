package com.digitalresource.Entity;

import lombok.Data;

@Data
public class Feature {
    private int feature_id;
    private Double feature_num;
    private String feature_name;
    private String feature_expression;
    private String feature_level;
    private String feature_investigate;
    private String feature_file;
    private String feature_origin_file;
    private int feature_group;
}
