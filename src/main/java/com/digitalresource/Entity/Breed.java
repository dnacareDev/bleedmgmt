package com.digitalresource.Entity;

import lombok.Data;

@Data
public class Breed {
	private int breed_id;
	private int resource_id;
	private String create_date;
	private int row_file;
	private String variety_name;

	private int detail_count;						// 세부정보 수
}
