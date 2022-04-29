package com.example.cran.service.impl;

import com.example.cran.entity.MatchDemand;
import com.example.cran.entity.Sla;
import com.example.cran.entity.Slicing;
import com.example.cran.mapper.SlaMapper;
import com.example.cran.service.ISlaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cran.service.ISlicingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ---
 * @since 2022-04-19
 */
@Service
public class SlaServiceImpl extends ServiceImpl<SlaMapper, Sla> implements ISlaService {

    @Autowired
    private SlaMapper slaMapper;

    @Resource
    private ISlaService slaService;

    @Resource
    private ISlicingService slicingService;


    @Override
    public List<Object> selectSla(MatchDemand matchDemand) throws IllegalAccessException {
        //将matchDemand中的匹配字段数值化
        Map<String,Integer> md = new HashMap<>();
        for(Field f: matchDemand.getClass().getDeclaredFields()){
            f.setAccessible(true);
            String name = f.getName();
            if(!name.equals("typicalName")&&!name.equals("ueNum")&&!name.equals("id")){
                Object value = f.get(matchDemand);
                if(value.equals("高")  ){
                    md.put(name,5);
                } else if (value.equals("中高") ){
                    md.put(name,4);
                } else if (value.equals("中")){
                    md.put(name,3);
                } else if (value.equals("中低")){
                    md.put(name,2);
                } else if (value.equals("低")){
                    md.put(name,1);
                }
            }
            if(name.equals("id")) md.put(name,matchDemand.getId());
        }

        //将sla中的匹配字段数值化
        List<Map<String,Integer>> slaTotal = new ArrayList<Map<String,Integer>>();
        List<Sla> slasList = slaService.list();
        for(int i=0; i<slasList.size(); i++){
            Map<String,Integer> map = new HashMap<>();
            for(Field f: slasList.get(i).getClass().getDeclaredFields()) {
                f.setAccessible(true);
                String name = f.getName();
                if(!name.equals("typicalName")&&!name.equals("ueNum")&&!name.equals("id")){
                    Object value = f.get(slasList.get(i));
                    if(value.equals("高")  ){
                        map.put(name,5);
                    } else if (value.equals("中高") ){
                        map.put(name,4);
                    } else if (value.equals("中")){
                        map.put(name,3);
                    } else if (value.equals("中低")){
                        map.put(name,2);
                    } else if (value.equals("低")){
                        map.put(name,1);
                    }
                }
                if(name.equals("id")) map.put(name,slasList.get(i).getId());
            }
            slaTotal.add(map);
        }

        //最佳匹配sla应该是各项匹配项最接近，也就是数值相减最小的
        //遍历所有sla，寻找与matchDemand最相近的sla
        List<Slicing> slicingTotal = slicingService.list();
        int minMatchNum = 100;
        int[] index = new int[slaTotal.size()+1];
        int j=0; //用于index列表增加元素的自增指针
        Integer bestSlaId = -1;
        for(int i=0;i<slaTotal.size();i++){
            Map<String,Integer> map = slaTotal.get(i);
            int matchNum = 0;
            matchNum += map.get("uplinkBw")-md.get("uplinkBw")+
                    map.get("downlinkBw")-md.get("downlinkBw") +
                    map.get("eteDelay")-md.get("eteDelay")+
                    map.get("resIsoLevel")-md.get("resIsoLevel") +
                    map.get("secIsoLevel")-md.get("secIsoLevel")+
                    map.get("priority")-md.get("priority") ;
            if(map.get("uplinkBw")-md.get("uplinkBw")>=0
                    && map.get("downlinkBw")-md.get("downlinkBw")>=0
                    && map.get("eteDelay")-md.get("eteDelay")>=0
                    && map.get("resIsoLevel")-md.get("resIsoLevel")>=0
                    && map.get("secIsoLevel")-md.get("secIsoLevel")>=0
                    && map.get("priority")-md.get("priority")>=0){
                index[j++] = map.get("id");
                if(minMatchNum>matchNum){
                    minMatchNum=matchNum;
                    bestSlaId = map.get("id");
                }
            }
        }
        String bestSLAType = "";
        //获取最佳匹配的SLA的类型
        for(Slicing sc:slicingTotal){
            if(sc.getId() == bestSlaId){
                bestSLAType = sc.getType();
            }
        }

        //获取与匹配的最佳sla相关的切片列表
        List<Slicing> matchSlicing = new ArrayList<>();
        for(int i=0;i<slicingTotal.size();i++){
            for(int t=0;t<index.length;t++)
            if(slicingTotal.get(i).getId() == index[t]){
                matchSlicing.add(slicingTotal.get(i));
            }
        }
        List<Object> result = new ArrayList<>();
        result.add(matchSlicing);
        result.add(bestSLAType);
        return result;
    }



}
