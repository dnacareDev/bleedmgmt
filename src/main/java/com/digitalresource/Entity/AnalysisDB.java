package com.digitalresource.Entity;

import lombok.Data;

@Data
public class AnalysisDB {
	private int analysis_id;
	private String crop_name;
	private String analysis_category;
	private String detail;
	private String note;
	private String regist_date;
}
