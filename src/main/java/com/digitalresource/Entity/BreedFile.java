package com.digitalresource.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("BreedFile")
public class BreedFile {
  
  private int breed_file_id;
  private Integer breed_id;
  private int file_type;
  private String breed_file_title;
  private String breed_file_contents;
  private String create_date;
  private String modify_date;

  private String uploads_file;
  private String uploads_origin_file;
}
