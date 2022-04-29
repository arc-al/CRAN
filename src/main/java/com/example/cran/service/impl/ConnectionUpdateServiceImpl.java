package com.example.cran.service.impl;

import com.example.cran.entity.BaseStation;
import com.example.cran.entity.Terminal;
import com.example.cran.service.IBaseStationService;
import com.example.cran.service.IConnectionUpdateService;
import com.example.cran.service.ITerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ConnectionUpdateServiceImpl implements IConnectionUpdateService {

    @Autowired
    private ITerminalService terminalService;

    @Autowired
    private IBaseStationService baseStationService;

    /**
     * 更新基站与终端之间的连接关系，更新基站表中”连接终端数“字段，更新终端”关联基站id“字段
     * 终端只能连接一个基站，基站可连接多个终端
     * @return
     */
    @Override
    public boolean bs_terminalUpdate() {
        //获取基站表中的所有数据
        List<BaseStation> bsList = baseStationService.list();
        //获取终端表中的所有数据
        List<Terminal> terminalList = terminalService.list();

        //以每个终端为单位，计算出距离该终端最近的基站id，修改该终端连接的基站id
        Map<Integer,Integer> bsConnTerminalNum = new HashMap<>(); //统计每个基站连接终端的数量
        for (BaseStation bs: bsList) bsConnTerminalNum.put(bs.getId(), 0);
        for (Terminal tm: terminalList){
            double minDis = 10000;
            Integer minDisBsId = -1;
            double dis = 0;
            for (BaseStation bs: bsList){
                dis = Math.sqrt( Math.pow(tm.getLongitude()-bs.getLongitude(), 2) +
                        Math.pow(tm.getLatitude()-bs.getLatitude(), 2));
                if(dis < minDis) {
                    minDisBsId=bs.getId();
                    minDis = dis;
                }
            }
            bsConnTerminalNum.put(minDisBsId,bsConnTerminalNum.get(minDisBsId)+1); //对应基站增加一个终端连接数
            tm.setConnBs(minDisBsId); //修改基站对应连接基站Id
        }

        //修改数据库
        //更新基站表中”连接终端数“字段
        boolean bsFlag = false;
        for(BaseStation bs: bsList){
            bs.setConnTerminalNum(bsConnTerminalNum.get(bs.getId())); //最终修改基站连接终端数的字段
            bsFlag = baseStationService.updateById(bs);
        }

        //更新终端”关联基站id“字段
        boolean tmFlag = false;
        for(Terminal tm: terminalList){
            tmFlag = terminalService.updateById(tm);
        }

        return bsFlag&&tmFlag;
    }
}
