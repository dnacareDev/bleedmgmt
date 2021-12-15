package com.digitalresource.ServiceImple;

import com.digitalresource.Entity.File;
import com.digitalresource.Mapper.FileMapper;
import com.digitalresource.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper mapper;

    @Override
    public int registFile(File file) {
        int result = -1;

        result = uploadFile(file);
        if(result < 0){
            return result;
        }
        result = mapper.registFile(file);
        if(result < 0){
            return result;
        }

        return result;
    }

    @Override
    public File selectFileById(int file_id) {
        File file = null;
        return file;
    }

    @Override
    public int deleteFile(int file_id){
        int result = -1;

        File file = selectFileById(file_id);
        if(file == null){
            //Err_cd
            return result;
        }
        result = destroyFile(file);
        if(result < 0){
            return result;
        }

        result = mapper.deleteFile(file_id);
        if(result < 0){
            //Err_cd
            return result;
        }

        return result;
    }

    private int uploadFile(File file){
        int result = -1;
        return result;
    }

    private int destroyFile(File file){
        int result = -1;
        return result;
    }
}
