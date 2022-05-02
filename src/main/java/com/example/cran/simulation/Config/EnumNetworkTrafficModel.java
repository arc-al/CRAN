package com.example.cran.simulation.Config;

/**
 * 默认
 *
 */
public enum EnumNetworkTrafficModel {
    vPreTrafficLoadModel(0);//预置业务量
    private String vName;
    private int vIndex;

    EnumNetworkTrafficModel(int b){
        this.vIndex=b;
    }
    int get_value(){
        return vIndex;
    }
    public static EnumNetworkTrafficModel get_model(int vIndex){
        switch (vIndex) {
            case 0:
                return vPreTrafficLoadModel;

            default:
                return vPreTrafficLoadModel;
        }
    }
}
