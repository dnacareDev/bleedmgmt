package com.digitalresource.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LogList {
	private int log_id;
	private String log_contents;
	private String log_create_date;
}
