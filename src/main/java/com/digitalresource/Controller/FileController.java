package com.digitalresource.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
  public String ChangeFileName(String extension) {
    String fileName = "";

    Date date = new Date();

    fileName = date.getTime() + "." + extension;

    return fileName;
  }

  @GetMapping(value = "/upload", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<Resource> download(String filename) {
    ResponseEntity<Resource> result = null;

    try {
      String originFileName = URLDecoder.decode(filename, "UTF-8");

      System.out.println("/upload/" + originFileName);
      Resource file = new FileSystemResource("/upload/" + originFileName);

      if (!file.exists()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

      String onlyFileName = originFileName.substring(originFileName.lastIndexOf("_") + 1);
      HttpHeaders header = new HttpHeaders();
      header.add("Content-Disposition", "attachment; filename=" + onlyFileName);

      result = new ResponseEntity<>(file, header, HttpStatus.OK);
      System.out.println(result);
    } catch (IOException e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

    return result;
  }


  // 파일 다운로드
  @ResponseBody
  @RequestMapping("downloadFile")
  public ResponseEntity<Object> DownloadFile(@RequestParam("uploads_file") String uploads_file) {
    String path = "upload/" + uploads_file;

    try {
      Path filePath = Paths.get(path);
      Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기

      File file = new File(path);

      HttpHeaders headers = new HttpHeaders();
      headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더

      return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
    }
  }
}