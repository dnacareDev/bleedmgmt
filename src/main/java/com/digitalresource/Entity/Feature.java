package com.digitalresource.Entity;

import lombok.Data;

@Data
public class Feature {
    private int feature_id;
    private int feature_num;
    private String feature_name;
    private String feature_expression;
    private String feature_level;
    private String feature_investigate;
}
