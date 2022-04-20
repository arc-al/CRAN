package com.example.cran.controller;

import cn.hutool.core.collection.CollUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/echarts")
public class EchartsController {
    public Result get(){
        Map<String,Object> map = new HashMap<>();
        map.put("x", CollUtil.newArrayList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        map.put("y", CollUtil.newArrayList(150, 230, 224, 218, 135, 147, 260));
        return (Result) map;
    }
}
