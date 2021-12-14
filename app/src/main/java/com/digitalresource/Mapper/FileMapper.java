package com.digitalresource.Mapper;

import com.digitalresource.Entity.File;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    public int registFile(File file);

    public File selectFileByid(int file_id);

    public int deleteFile(int file_id);
}
