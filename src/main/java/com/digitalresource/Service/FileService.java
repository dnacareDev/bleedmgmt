package com.digitalresource.Service;

import com.digitalresource.Entity.File;

public interface FileService {
    public int registFile(File file);

    public File selectFileById(int file_id);

    public int deleteFile(int file_id);
}
