package com.digitalresource.Entity;

import lombok.Data;

@Data
public class ResourceAllList {
	private int resource_id;
	private int resource_name_id;
	private int crop_id;
	private String create_date;
	private String resource_name;
	private int user_group;
}
