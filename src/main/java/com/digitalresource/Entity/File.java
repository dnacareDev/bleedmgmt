package com.digitalresource.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class File {
    private int file_id;
    private String file_name;
    private String file_original;
    private int file_type;
    private int row_num;
    private String create_date;
    private String modify_date;

    private MultipartFile file;
}
