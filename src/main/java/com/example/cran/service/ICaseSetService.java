package com.example.cran.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cran.entity.BaseStation;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


public interface ICaseSetService  {
    boolean CaseSet() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    String[][] getData(String path) throws IOException;

    List<Object> getObject(String[][] result, String classPath) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}
