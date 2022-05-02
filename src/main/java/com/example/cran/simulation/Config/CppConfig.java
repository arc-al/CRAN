package com.example.cran.simulation.Config;

public class CppConfig {
    //实际上只启用了这个方法
    public native void setConfiguration();

    public native void setConfigCase0(ConfigTti vSample);	//默认配置一 传入对象，调用对象中OBJECT[]参数
    public native void setConfigCase1(ConfigTti vSample,int vMode);	//默认配置二 传入对象 及其他参数
    public native void setConfigCase2(ConfigTti vSample,String name);	//默认配置三,cpp中基础类型没有string 只能转为char
}
