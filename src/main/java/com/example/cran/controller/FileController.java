package com.example.cran.controller;

import cn.hutool.core.io.FileUtil;
import com.example.cran.mapper.FilesMapper;
import com.example.cran.service.IFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;



@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private IFilesService filesService;

    @Value("${files.upload.path}")
    private String fileUploadPath;


    @Resource
    private FilesMapper filesMapper;

    @PostMapping("/base-station/upload")
    public String bsUpload(@RequestParam MultipartFile file) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String bsDownloadURL = filesService.upload(file);
        boolean b = filesService.modifyDB("com.example.cran.entity.BaseStation", file.getOriginalFilename(), "base-station");
        if(b) return bsDownloadURL;
        else return null;
    }

    @PostMapping("/terminal/upload")
    public String terminalUpload(@RequestParam MultipartFile file) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String terminalDownloadURL = filesService.upload(file);
        boolean b = filesService.modifyDB("com.example.cran.entity.Terminal", file.getOriginalFilename(), "terminal");
        if(b) return terminalDownloadURL;
        else return null;
    }

    @PostMapping("/mec/upload")
    public String mecUpload(@RequestParam MultipartFile file) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String mecDownloadURL = filesService.upload(file);
        boolean b = filesService.modifyDB("com.example.cran.entity.Mec", file.getOriginalFilename(), "mec");
        if(b) return mecDownloadURL;
        else return null;
    }

    @GetMapping("{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        //根据文件的唯一标识码获取文件
        File downloadFile = new File(fileUploadPath + fileName);
        //设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("appliaction/octet-stream");
        //读取文件的字节流
        os.write(FileUtil.readBytes(downloadFile));
        os.flush();
        os.close();
    }

}
