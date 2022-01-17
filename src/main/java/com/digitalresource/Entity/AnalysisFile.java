package com.digitalresource.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("AnalysisFile")
public class AnalysisFile
{
	private int analysis_file_id;
	private int user_id;
	private int analysis_type;
	private String analysis_file;
	private String analysis_origin_file;
	private String create_date;
	private String modify_date;
}