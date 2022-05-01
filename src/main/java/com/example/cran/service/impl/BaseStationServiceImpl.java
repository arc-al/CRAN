package com.example.cran.service.impl;

import com.example.cran.entity.BaseStation;
import com.example.cran.mapper.BaseStationMapper;
import com.example.cran.service.IBaseStationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cran.utils.CoordinateTrans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ---
 * @since 2022-04-17
 */
@Service
public class BaseStationServiceImpl extends ServiceImpl<BaseStationMapper, BaseStation> implements IBaseStationService {


    @Autowired
    private BaseStationMapper baseStationMapper;

    @Autowired
    private IBaseStationService baseStationService;

    @Override
    public int getMaxid() {
        if(baseStationService.list().size()==0){
            return 1;
        } else {
            return baseStationMapper.getMaxid();
        }
    }

    @Override
    public boolean deleteAll() {
        return baseStationMapper.deleteAll();
    }

    @Override
    public List<Object> counterCover(String longitude1, String longitude2, String latitude1, String latitude2) {
        //节点类
        class Node {
            public double longitude;
            public double latitude;
        }
        //读取基站数据
        List<BaseStation> list = baseStationService.list();
        double lowerLeftLongitude = Double.parseDouble(longitude1);    //左下经度
        double lowerLeftLatitude = Double.parseDouble(latitude2);    //左下纬度
        double topRightLongitude = Double.parseDouble(longitude2);    //右上经度
        double topRightLatitude = Double.parseDouble(latitude1);    //右上纬度
        double originalLongitude = lowerLeftLongitude;    //左下经度
        double originalLatitude = lowerLeftLatitude;    //左下纬度
        double arealength = 50;
        double longitudeUnit = lowerLeftLongitude < topRightLongitude ? arealength * 0.006 / 500.0 : -arealength * 0.006 / 500.0;
        double latitudeUnit = lowerLeftLatitude < topRightLatitude ? arealength * 0.00445 / 500.0 : -arealength * 0.00445 / 500.0;
        int row = (int) Math.abs((lowerLeftLatitude - topRightLatitude) / (latitudeUnit * 2));
        int column = (int) Math.abs((lowerLeftLongitude - topRightLongitude) / (longitudeUnit * 2));
        Node[][] grid_location = new Node[row][column];    //记录每个网格中心点的经纬度坐标
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (i == 0 && j == 0) {
                    Node node = new Node();
                    node.longitude = originalLongitude + longitudeUnit;
                    node.latitude = originalLatitude + latitudeUnit;
                    grid_location[i][j] = node;
                } else {
                    Node node = new Node();
                    node.longitude = originalLongitude + longitudeUnit + 2 * longitudeUnit * j;
                    node.latitude = originalLatitude + latitudeUnit + 2 * latitudeUnit * i;
                    grid_location[i][j] = node;
                }
            }
        }
        double[][] grid_fieldStrength = new double[row][column];
        double max = -1000;//计算接收场强度的区间
        double min = 0;
        double pathLoss = 0;
        int[][] grid_color = new int[row][column];
        int RRU_count = list.size();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                //因为网格到基站越近，其接受场强度最大，所以只要取距离该网格最近的那个基站进行计算即可
                double min_distance = Double.MAX_VALUE;    //距离当前网格最近的基站到当前网格的距离
                int transPower = 0;    //距离当前网格最近的基站的传输功率
                double Z = 0;
                for (int k = 0; k < RRU_count; k++) {
                    double distance = computeDistance(grid_location[i][j].longitude, grid_location[i][j].latitude,
                            list.get(k).getX(), list.get(k).getY()) / 1000;    //以km为单位
                    if (distance < min_distance) {
                        min_distance = distance;
                        transPower = list.get(k).getTranspower();//获取功率
                        Z = list.get(k).getZ();
                    }
                }
                if (min_distance<2) {
                    pathLoss = 46.3 +(44.9-6.55*log(Z, 10))*log(min_distance, 10)+33.9*log(1800, 10)-((1.1*log(1800, 10)-0.7)*1-1.56*log(1800, 10)+0.8)-13.82 *log(Z, 10); //       COST-231Hata大城市模型
                    grid_fieldStrength[i][j]=transPower-pathLoss;
                }
                if (min_distance>=2) {
                    grid_fieldStrength[i][j]=-100;// 默认超过1KM、的时候  接受到的场强很小、
                }
                if (max<grid_fieldStrength[i][j]) {
                    max=grid_fieldStrength[i][j];
                }
                if (min>grid_fieldStrength[i][j]) {
                    min=grid_fieldStrength[i][j];
                }
            }
        }
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                //int colorNum=5;//颜色划分的数目
                if(grid_fieldStrength[i][j]>=min&&grid_fieldStrength[i][j]<min+(max-min)/6){
                    grid_color[i][j]=5;
                }else if(grid_fieldStrength[i][j]>=min+(max-min)/6&&grid_fieldStrength[i][j]<min+2*(max-min)/6){
                    grid_color[i][j]=4;
                }else if(grid_fieldStrength[i][j]>=min+2*(max-min)/6&&grid_fieldStrength[i][j]<min+3*(max-min)/6){
                    grid_color[i][j]=3;
                }else if(grid_fieldStrength[i][j]>=min+3*(max-min)/6&&grid_fieldStrength[i][j]<min+4*(max-min)/6){	//最强
                    grid_color[i][j]=2;
                }else if(grid_fieldStrength[i][j]>=min+4*(max-min)/6&&grid_fieldStrength[i][j]<min+5*(max-min)/6){
                    grid_color[i][j]=1;
                }
                else {
                    grid_color[i][j]=0;	//最强
                }
//                System.out.println(grid_fieldStrength[i][j]+" "+grid_color[i][j]);
            }
//            System.out.println();
        }
        //把i，j和color发给前端
        List<List<Integer>> grid = new ArrayList<>();
        for(int i=0;i<row;i++){
            for(int j=0;j<column;j++){
                List<Integer> item = new ArrayList<>();
                item.add(j);
                item.add(i);
                item.add(grid_color[i][j]);
                grid.add(item);
            }
        }
        List<Object> result = new ArrayList<>();
        result.add(grid);
        result.add(row);
        result.add(column);
        result.add(latitudeUnit*2);
        result.add(longitudeUnit*2);
        return result;

    }

    @Override
    public boolean saveData(BaseStation baseStation) {
        baseStation.setX(CoordinateTrans.longitudeToX(baseStation.getLongitude()));
        baseStation.setY(CoordinateTrans.latitudeToY(baseStation.getLatitude()));
        baseStation.setZ(150.0);
        if(baseStation.getId()==null){
            baseStation.setId(baseStationService.getMaxid()+1);
            return baseStationService.save(baseStation);
        } else {
            return baseStationService.updateById(baseStation);
        }
    }

    private double computeDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        double Lat1 = rad(latitude1); // 纬度
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;//两点纬度之差
        double b = rad(longitude1) - rad(longitude2); //经度之差
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(Lat1) * Math.cos(Lat2) * Math.pow(Math.sin(b / 2), 2)));//计算两点距离的公式
        s = s * 6378137.0;//弧长乘地球半径（半径为米）
        s = Math.round(s * 10000d) / 10000d;//精确距离的数值
        return s;
    }

    private double rad(double d) {
        return d * Math.PI / 180.00;
    }
    private double log(double value,double base){
        return Math.log(value)/Math.log(base);
    }

}
