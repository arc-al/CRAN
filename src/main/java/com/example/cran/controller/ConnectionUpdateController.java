package com.example.cran.controller;

import com.example.cran.service.IConnectionUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class ConnectionUpdateController {

    @Autowired
    private IConnectionUpdateService connectionUpdateService;

    @PostMapping
    public boolean bs_terminalUpdate(){
        return connectionUpdateService.bs_terminalUpdate();
    }
}
