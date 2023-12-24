package com.search.trek.resource;

import com.search.trek.application.FileServiceApplication;
import com.search.trek.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/search/trek/file")
public class FileResource {

    @Autowired
    private FileServiceApplication fileServiceApplication;

    @PostMapping("/upload")
    public Result upload(@NonNull @RequestParam("file") MultipartFile file){
        return fileServiceApplication.upload(file);
    }

}
