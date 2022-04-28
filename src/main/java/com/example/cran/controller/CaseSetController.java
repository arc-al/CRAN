package com.example.cran.controller;

import com.example.cran.service.ICaseSetService;
import com.example.cran.service.impl.CaseSetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


@RestController
@RequestMapping("/case-set")
public class CaseSetController {

    @Autowired
    private ICaseSetService caseSetService;

    @PostMapping
    public boolean caseSet() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return caseSetService.CaseSet();
    }


}
