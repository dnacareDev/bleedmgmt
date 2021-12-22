package com.digitalresource.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Alias("ChartCount")
public class ChartCount {

  private int img_count;
  private int doc_count;

}
