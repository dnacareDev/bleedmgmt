package com.digitalresource.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@Data
@Alias("StandardList")
public class StandardList {
	private int standard_id;
	private int breed_id;
	private int detail_id;
	private String file_id;
	private String standard_data;

	private int resource_id;
	private int breed_row;
}
 