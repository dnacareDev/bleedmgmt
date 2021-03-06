package com.digitalresource.Entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Trait {

  private int trait_id;
  private int crop_id;
  private String trait_description;
  private int trait_year;
  private String trait_file;
  private String trait_origin_file;
  private LocalDate create_date;
  private LocalDate modify_date;
  private int feature_count;
  private String crop_category;
  private String category_name;
  private String crop_name;
  private int feature_group;

}
