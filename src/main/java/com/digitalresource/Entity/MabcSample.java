package com.digitalresource.Entity;

import lombok.Data;

@Data
public class MabcSample {
	private int mabc_id;
	private String file_name;
	private String file_origin_name;
	private String crop;
	private String mabc_category;
	private String regist_person;
	private String regist_date;
	private String detail_note;
}
