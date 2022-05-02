package com.example.cran.simulation;

import com.example.cran.simulation.Config.ConfigJavaController;
import com.example.cran.simulation.Config.ConfigTti;
import com.example.cran.simulation.Config.CppConfig;

public class ThreadBackGround extends Thread{
    public ThreadBackGround() {
    }

    public ThreadBackGround(Runnable target) {
        super(target);
    }

    public ThreadBackGround(String name) {
        super(name);
    }

    public ThreadBackGround(ThreadGroup group, Runnable target) {
        super(group, target);
    }

    public ThreadBackGround(ThreadGroup group, String name) { // 实际调用的是这个构造函数
        super(group, name);
    }

    public ThreadBackGround(Runnable target, String name) {
        super(target, name);
    }

    public ThreadBackGround(ThreadGroup group, Runnable target, String name) {
        super(group, target, name);
    }

    public ThreadBackGround(ThreadGroup group, Runnable target, String name, long stackSize) {
        super(group, target, name, stackSize);
    }

    public void run() {
        // 将输出流重定向为控制台
        // StreamToTextArea blah = StreamToTextArea.getInstance();
        // System.setOut(blah.getPs());

        System.out.println("准备进入后台C++程序运行");
        try {
            System.out.println("网络流量模型" + ConfigTti.vEnumNetworkTrafficModel.name());
            System.out.println("资源调度模型" + ConfigTti.vEnumResourceSheldual.name());
            System.out.println("用户业务量配置模型" + ConfigTti.vEnumUeTrafficModel.name());
            System.out.println("用户移动性模型" + ConfigTti.vMov.name());
            System.out.println("路损模型" + ConfigTti.vPathLossModel.name());
            System.out.println("频带模型" + ConfigTti.vBandwidthResourceModel.name());
            System.out.println("有线链路模型" + ConfigTti.vWirePathModel.name());
            Thread.sleep(2000);
            CppConfig aConfig = new CppConfig(); // 声明本地方法setConfiguration();
            switch (ConfigJavaController.vCaseVersion) {
                case vDefaultDemo: // 调用默认装配的case
                    aConfig.setConfiguration();
                    System.out.println("C++调用成功！");
                    break;
                default:
                    System.out.println("该功能目前未实现");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // cpp模块已经结束
        System.out.println("cpp_单个TTi线程 结束。数据结果返回数据库");
    }
}
