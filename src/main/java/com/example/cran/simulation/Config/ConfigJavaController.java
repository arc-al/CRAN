package com.example.cran.simulation.Config;

import java.util.Arrays;

public final class ConfigJavaController {
    public static String vSoftName="Cran平台";
    public static EnumDemoCase vCaseVersion=EnumDemoCase.vDefaultDemo;	//装配方案，可以理解为版本吧
    public static EnumSamplingInterval vIntervalUnit=EnumSamplingInterval.vTTI;	//仿真抽样单位
    public static EnumSamplingFunction vSamplingFunction=EnumSamplingFunction.vEqualInterval;	//抽样方式，是等间隔抽样还是自定义抽样
    public static double vSimuT;	//仿真总时长，单位由仿真抽样单位决定
    public static int vTtiTimeLength=1;	//TTI时长，单位是ms
    public static int vSamplingNum=10;	//每个抽样时刻抽取的TTI个数
    public static int vSamplingTime[]={0,1,2,3};	//抽样的时刻
    public static boolean vFlagPause=true;	//true：表示当前正在运行，false：表示暂停
    public static boolean vFlagOptimazation=true;	//是否启用优化
    public static boolean vMeetOptimization=false;	//判断是否满足优化
    public volatile static boolean vFlagOptiTriggle=false;
    public static String vLogCppFile="D:\\CRAN\\Log\\"+515+".txt";	//log日志的地址，每运行一次修改一下文件名还是统一文件，待商榷
    public volatile static String vLogList="log信息";
    public static int vUnitCounter;
    public static int vSubUniteCounter;

    /**进行时常检测时候需要利
     * 用vTTI的时间长度进行核对数据
     * 的抽样次数是否大于抽样单
     * 位的规定*/
    public static void check() {
        switch (vIntervalUnit) {
            case vTTI:
                assert vSamplingNum*vTtiTimeLength<1000 && vSamplingTime.length<vSimuT;//单位ms
                break;
            case vMinute:
                assert vSamplingNum*vTtiTimeLength<60000 && vSamplingTime.length<vSimuT;//单位ms
            case vHour:
                assert vSamplingTime.length<vSimuT;
            case vDay:
                assert vSamplingTime.length<vSimuT;
            default:
                assert vSamplingTime.length<vSimuT;
                break;
        }
    }

    /**
     * 用户设置等间距抽样
     * @param m:每m个抽样单位TTI进行抽样
     */
    public static void setSamplingEqual(EnumSamplingInterval vUnit,double vSimuT,int m,int vSamplingNum) {
        //设置抽样的时刻vSimplingTime[]
        vIntervalUnit=vUnit;
        ConfigJavaController.vSimuT=vSimuT;
        ConfigJavaController.vSamplingNum=vSamplingNum;
        int counter=0;
        while (true) {
            vSamplingTime[counter]+=m;
            /**防止大于仿真时常*/
            if (vSamplingTime[counter]>vSimuT) {
                break;
            }
            counter++;
        }

    }

    /**
     * 用户自定义抽样时刻
     * @param vUnit 抽样单位
     * @param vSimuT 抽样总时常
     * @param m   用户自定义抽样时刻
     * @param vSamplingNum 每个抽样时刻的抽样数
     */
    public static void setSamplingCustom(EnumSamplingInterval vUnit,double vSimuT,int m[],int vSamplingNum) {
        //设置抽样的时刻vSimplingTime[]
        vIntervalUnit=vUnit;
        ConfigJavaController.vSimuT=vSimuT;
        ConfigJavaController.vSamplingNum=vSamplingNum;
        /**防止用户输入参数的顺序不对*/
        Arrays.sort(m);
        vSamplingTime=m;
        if (vSamplingTime[m.length-1]>vSimuT) {
            System.err.println("系统参数错误:抽样时常最大值大于总时长");
        }
        /**防止大于仿真时常*/
    }
}
