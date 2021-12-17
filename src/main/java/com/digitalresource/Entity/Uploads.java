package com.digitalresource.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("Uploads")
public class Uploads
{
  private int uploads_id;
  private String uploads_file;
  private String uploads_origin_file;
  private int uploads_type;						// 파일 유형(0: 첨부파일, 1: 엑셀파일)
  private int uploads_cat;						// 파일 카테고리
  private int row_num;
  private String create_date;
  private String modify_date;

  private int breed_file_id;
}
