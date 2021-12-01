package com.digitalresource.Entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Trait {

  private int trait_id;
  private int crop_id;
  private String trait_description;
  private String trait_year;
  private int trait_file;
  private LocalDate create_date;
  private LocalDate modify_date;
  private int feature_count;

}
