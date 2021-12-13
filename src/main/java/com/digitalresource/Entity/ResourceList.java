package com.digitalresource.Entity;

import lombok.Data;

@Data
public class ResourceList {
	private int resource_id;
	private int resource_name_id;
	private String resource_name;
	private int crop_id;
	private int trait_id;
	private String resource_template;
	private String resource_character_template_file;
	private int resource_use;
	private String create_date;
	private String modify_date;
	private int detail_count;
	private String trait_description;
	private String crop_name;
	private String use_name;
}
