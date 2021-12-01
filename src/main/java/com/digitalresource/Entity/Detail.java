package com.digitalresource.Entity;

import lombok.Data;

@Data
public class Detail {
    private int detail_id;
    private int resource_id;
    private int detail_type;
    private String detail_name;
    private int detail_index;
    private int detail_info;
}
