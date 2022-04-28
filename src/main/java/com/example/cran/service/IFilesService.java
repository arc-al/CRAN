package com.example.cran.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cran.entity.Files;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface IFilesService extends IService<Files> {
    String upload(MultipartFile file) throws IOException;

    boolean modifyDB(String classPath, String fileName,String tableName) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
