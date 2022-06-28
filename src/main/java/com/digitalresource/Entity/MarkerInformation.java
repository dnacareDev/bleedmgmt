package com.digitalresource.Entity;

import lombok.Data;

@Data
public class MarkerInformation {
	private int marker_num;
	private String marker_file;
	private String marker_origin_file;
	private String crop_name;
	private String marker_category;
	private String regist_person;
	private String regist_date;
	private String detail_note;
}
