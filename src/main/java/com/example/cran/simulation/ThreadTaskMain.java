package com.example.cran.simulation;

import com.example.cran.simulation.Config.ConfigJavaController;
import com.example.cran.simulation.Config.EnumSamplingInterval;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ThreadTaskMain extends Thread{
    private volatile boolean vIsSuspend = false; // volatile不懂
    public static int vUnitCounter = 0; // 抽样时刻计数器
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
    TimeZone t = sdf.getTimeZone();
    public double fProgress = 0; // 用于显示进度条

    long startTime=System.currentTimeMillis();
    long pauseTime = 0; // 这两个变量用于记录暂停的时间
    long endTime = 0;

    public ThreadTaskMain() {
        super();
    }

    public ThreadTaskMain(ThreadGroup tag, String aim) { // 实际上只调用了这个构造函数
        super(tag, aim);
    }

    public synchronized void stopThread() {
        vIsSuspend = true;
    }

    @Override
    public void run() {
        t.setRawOffset(0);
        sdf.setTimeZone(t);
        //将输出流重新定向为控制台
//		StreamToTextArea blah = StreamToTextArea.getInstance();
//		System.setOut(blah.getPs());

        System.out.println("进入主程序");
        boolean vLargeScale = false;

        //判断大尺度，即如果抽样单位不是TTI，就表示是大尺度
        if (ConfigJavaController.vIntervalUnit != EnumSamplingInterval.vTTI) {
            vLargeScale = true;
            System.out.println("大尺度运行中");
        }

        //第一层循环，控制抽样时刻，中断
        try {
            System.out.println("------------------------" + ConfigJavaController.vSamplingTime.length);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        ConfigJavaController.vFlagPause = true;// 在每次线程开始时，将暂停变量还原（true表示当前处于运行状态）
        vUnitCounter = 0;
        fProgress = 0;
        //第一层循环
        BreakLabel: while (vUnitCounter < ConfigJavaController.vSamplingTime.length && !vIsSuspend) {
            // run3.textField_2.setText(Integer.toString(ConfigJavaControler.vSamplingTime[ThreadTaskMain.vUnitCounter]));	//运行状态的第一个文本框
            GetRunStatusAndRuntimeServlet.vUnitCounter=ConfigJavaController.vSamplingTime[ThreadTaskMain.vUnitCounter];
            int vSubUniteCounter = 0; // 抽样时刻内计数器

            boolean vOptimizationStart = false; // 每一个抽样时刻的优化结束标志
            // 第二层循环，控制抽样时刻内部的n个抽样，并进行数据存储
            Optimization: while (vSubUniteCounter < ConfigJavaControler.vSamplingNum && !vIsSuspend) {
                if (vIsSuspend)
                    continue BreakLabel;
                /** 跳出循环监测点 */
                fProgress = (double) ((vUnitCounter) * ConfigJavaControler.vSamplingNum + vSubUniteCounter + 1)
                        / (double) (ConfigJavaControler.vSamplingTime.length * (ConfigJavaControler.vSamplingNum));
//				run3.bar.setValue((int) (fProgress * 100));	//进度条
//				run3.textField_3.setText(Double.toString(fProgress));	//运行时间
//				run3.textField_4.setText(Integer.toString(vSubUniteCounter));	//运行状态的第二个文本框
                GetRunInfoServlet.vUnitCounter=vUnitCounter;
                GetRunInfoServlet.vSubUniteCounter=vSubUniteCounter;
                GetRunStatusAndRuntimeServlet.vSubUniteCounter=vSubUniteCounter;

                if (!ConfigJavaControler.vFlagPause) { // 如果当前处于暂停状态（false表示处于暂停状态）
                    pauseTime += System.currentTimeMillis() - endTime; // 更新pauseTime
                }
                endTime = System.currentTimeMillis();
                if (ConfigJavaControler.vFlagPause) { // 如果当前处于在运行状态
                    System.out.println("系统运行中");
//					run3.textField_3.setText(sdf.format(new Date(endTime - run3.startTime - pauseTime)));	//运行时间
                    GetRunStatusAndRuntimeServlet.runtime=sdf.format(new Date(endTime-startTime-pauseTime));	//可能有问题
                    try {
                        //跳出循环监测点
                        if (vIsSuspend)
                            continue BreakLabel;

                        //表示进入新的一轮循环
                        if (vOptimizationStart) {
                            System.out.println(
                                    "--优化后的结果--，当前时段信息-------" + ConfigJavaControler.vSamplingTime[vUnitCounter]
                                            + "----当前的时刻-----" + vSubUniteCounter);
                        } else { // 只执行这个分支
                            System.out.println(
                                    "--未优化的结果--，当前时段信息-------" + ConfigJavaControler.vSamplingTime[vUnitCounter]
                                            + "----当前的时刻-----" + vSubUniteCounter);
                        }
                        //进行相关配置
                        System.out.println("每个TTI配置信息加载中");

                        //启动调用C++的线程
                        ThreadBackGround vBackGroundThread = new ThreadBackGround(main.vMyGroup,
                                "back groud procedure");
                        vBackGroundThread.start();

                        //主控制线程的保持等待，保持每次只有一个TTI线程在运行
                        while (vBackGroundThread.isAlive()) {

                        }

                        vSubUniteCounter++;
                    } catch (Exception e) {
                    }
                } else { // 如果当前处在暂停状态
                    System.out.println("线程TTI暂停中");
                }
            }
            vUnitCounter++;
        }

        System.out.println("运行结束");
        // run3.bar.setValue(0);
    }

}
