package com.example.cran.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cran.entity.BaseStation;
import com.example.cran.entity.Files;
import com.example.cran.entity.Mec;
import com.example.cran.entity.Terminal;
import com.example.cran.mapper.FilesMapper;
import com.example.cran.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


@Service
public class FilesServiceImpl extends ServiceImpl<FilesMapper, Files> implements IFilesService {

    @Autowired
    private FilesMapper filesMapper;

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Autowired
    private IBaseStationService baseStationService;
    @Autowired
    private ITerminalService terminalService;
    @Autowired
    private IMecService mecService;

    @Autowired
    private ICaseSetService caseSetService;

    @Override
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();  //单位kbit
        //先存储到磁盘
        File uploadParentFile = new File(fileUploadPath);
        //判断配置的文件目录是否存在，若不存在就创建一个新的文件目录
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        File uploadFile = new File(fileUploadPath + originalFilename);
        //如果文件已经存在，则覆盖文件
        if(uploadFile.exists()){
            uploadFile.delete();
        }
        //上传文件到磁盘
        file.transferTo(uploadFile);
        //获取文件在磁盘的唯一标识md5，以便识别是否重复上传相同文件，避免浪费磁盘空间
        String md5 = SecureUtil.md5(uploadFile);
        //从数据库查询是否存在相同的记录
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5",md5);
        List<Files> dbFiles = filesMapper.selectList(queryWrapper);
        String url = "";
        if(dbFiles.size()!=0){
            url = dbFiles.get(0).getUrl();
        } else {
            //数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://localhost:8090/file/" + originalFilename;
        }
        //存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        filesMapper.insert(saveFile);
        return url;
    }

    @Override
    public boolean modifyDB(String classPath, String fileName,String tableName) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String[][] data = caseSetService.getData("files\\" + fileName);
        List<Object> rsList = caseSetService.getObject(data, classPath);
        boolean flagSave = false;
        if(tableName.equals("base-station")){
            baseStationService.deleteAll();
            for(Object baseStation:rsList){
                flagSave=baseStationService.save((BaseStation) baseStation);
                if(!flagSave) break;
            }
        }else if(tableName.equals("terminal")){
            terminalService.deleteAll();
            for(Object terminal:rsList){
                flagSave=terminalService.save((Terminal) terminal);
                if(!flagSave) break;
            }
        }else if(tableName.equals("mec")){
            mecService.deleteAll();
            for(Object mec:rsList){
                flagSave=mecService.save((Mec) mec);
                if(!flagSave) break;
            }
        }

        return flagSave;
    }


}
