package com.digitalresource.Entity;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Alias("BreedFileSimple")
public class BreedFileSimple {
	
	private int breed_file_id;
	private Integer breed_id;

	private String uploads_file;
	private String uploads_origin_file;
}
