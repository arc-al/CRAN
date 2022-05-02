package com.example.cran.simulation;

import com.example.cran.entity.ControlParameter;
import com.example.cran.service.*;
import com.example.cran.simulation.Config.ConfigJavaController;
import com.example.cran.simulation.Config.EnumSamplingFunction;
import com.example.cran.simulation.Config.EnumSamplingInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;

@RestController
@RequestMapping("/simulation")
public class run {

    @Autowired
    private IControlParameterService controlParameterService;
    @Autowired
    private IBusinessOutputService businessOutputService;
    @Autowired
    private IBusinessService businessService;
    @Autowired
    private IQiepianthroughputService  qiepianthroughputService;
    @Autowired
    private IDelayService delayService;
    @Autowired
    private ITotalthroughputService totalthroughputService;



    private static final long serialVersionUID = 1L;

    public Timer timer;
    public static Long startTime;
    public static Long endTime;

    public static ThreadTaskMain main_thread;



    @GetMapping
    public boolean run() {
        int SamplingUnit = 0;    //抽样单位
        int TTILength = 0;    //TTI时长
        int SimuT = 0;    //仿真总时长
        int SamplingNum = 0;    //抽样时刻TTI数
        int vSamplingFunction = 0;    //抽样类型(0:等间隔抽样 1:自定义抽样)
        String SamplingInterval = "";    //抽样间隔（等间隔抽样的抽样间隔）
        //新增的四个参数
        int ServiceType;//部署业务类型
        int SubCarrierSpacing;//子载波间隔
        int DataLength;//数据包长度
        double DecodingErrorRate;//译码错误率


        try {
            //从数据库中取出控制参数信息
            List<ControlParameter> controlParameters = controlParameterService.list();
            SamplingNum = controlParameters.get(0).getSamplingNum();
            TTILength = controlParameters.get(0).getTtiLength();
            SimuT = controlParameters.get(0).getSimuT();
            //SamplingNum=rs.getInt("SamplingNum");//写多余了。没有读取SamplingUnit，没有实现，默认0，即TTI
            vSamplingFunction = controlParameters.get(0).getVsamplingFunction();
            SamplingInterval = controlParameters.get(0).getSamplingInterval();
            //新增
            ServiceType = controlParameters.get(0).getServiceType();
            SubCarrierSpacing = controlParameters.get(0).getSubcarrierSpacing();
            DataLength = controlParameters.get(0).getDataLength();
            DecodingErrorRate = controlParameters.get(0).getDecodingErrorRate();


            //每次运行前将当前TTI设置为0
            controlParameters.get(0).setTti(0);
            controlParameterService.updateById(controlParameters.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }


        ConfigJavaController.vTtiTimeLength = TTILength;    //获取TTI时长
        ConfigJavaController.vIntervalUnit = EnumSamplingInterval.get_model(SamplingUnit);    //获取抽样单位
        ConfigJavaController.vSamplingFunction = EnumSamplingFunction.get_model(vSamplingFunction);    //获取抽样类型
        ConfigJavaController.vSimuT = SimuT;    //获取仿真总时长，单位由抽样单位决定

        //这里对等间隔抽样的处理没看懂
        if (ConfigJavaController.vSamplingFunction == EnumSamplingFunction.vEqualInterval) {    //等间隔抽样
            ConfigJavaController.vSamplingNum = SamplingNum;    //获取抽样时刻TTI数，即每个抽样时刻抽取的TTI数
            int interval = Integer.parseInt(SamplingInterval);
            ConfigJavaController.vSamplingTime = new int[(int) ConfigJavaController.vSimuT / interval + 1];    //注意interval+1没有加括号
            for (int i = 0; i < (int) ConfigJavaController.vSimuT / interval + 1; i++) {
                ConfigJavaController.vSamplingTime[i] = interval * i;//等间隔抽样的总TTI仿真数=(vSimT+1)*SamplingNum, 因为interval在数据库中为1且没有改变
            }
            System.out.println("抽样时刻点的长度为" + ConfigJavaController.vSamplingTime.length);
            System.out.println("抽样时刻的抽样TTI个数为" + ConfigJavaController.vSamplingNum);
        } else {    //自定义抽样
            //未实现
        }

        //将总体仿真的TTI个数TotalTtiNum存至数据库
        try {
            List<ControlParameter> controlParameters = controlParameterService.list();
            controlParameters.get(0).setTotalTtiNum(ConfigJavaController.vSamplingTime.length * ConfigJavaController.vSamplingNum);
            controlParameterService.updateById(controlParameters.get(0));
            businessOutputService.deleteAll(); //开始仿真前清空bussinessoutput表的数据
            businessService.deleteAll(); //开始仿真前清空Business表的数据
            qiepianthroughputService.deleteAll(); //开始仿真前清空QiePianThroughput表的数据
            totalthroughputService.deleteAll(); //开始仿真前清空TotalThroughput表的数据
            delayService.deleteAll(); //开始仿真前清空Delay表的数据
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!main_thread.isAlive()) {
            System.loadLibrary("NativeTestJni");//加载库1
//			System.loadLibrary("NativeProCase");//加载库2
//			System.loadLibrary("NativeProCase.v13");//加载库1
//			System.load("C://NativeProCase.v13.dll");

            System.load("src\\main\\java\\com\\example\\cran\\simulation\\sourceCode\\CRANC++.dll");
//			System.load("D:\\CRAN\\CRAN项目合集\\CRAN项目合集\\CRAN\\CRAN\\src\\com\\run\\Project2.dll");
            //新增

            main_thread = new ThreadTaskMain(com.example.cran.simulation.main.vMyGroup, "main procedure");    //这里又构造了一遍，感觉没有必要
            main_thread.start();

            SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");//设置日期格式

            //下面一行是D:\CRAN下的log日志文件的路径
            ConfigJavaController.vLogCppFile = ConfigJavaController.vLogCppFile.substring(0, 11) + df.format(new Date()) + ".txt";
            boolean runFlag = true;    //运行标志
            boolean pauseFlag = false;    //暂停标志
        }
        return true;
    }
}
