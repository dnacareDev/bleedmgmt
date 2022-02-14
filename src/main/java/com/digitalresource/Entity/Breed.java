package com.digitalresource.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Breed {
	private int breed_id;
	private int resource_id;
	private String create_date;
	private int row_file;
	private String variety_name;

	private String standard_data;
	private int detail_count;						// 세부정보 수
	private List<StandardList> standardList;
	private int standard_count;
}
