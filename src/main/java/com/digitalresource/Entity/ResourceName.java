package com.digitalresource.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceName {
    private int resource_name_id;
    private String resource_name;

    public ResourceName(String resource_name){
        this.resource_name = resource_name;
    }
}
