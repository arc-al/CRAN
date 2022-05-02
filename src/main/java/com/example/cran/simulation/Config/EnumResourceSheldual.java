package com.example.cran.simulation.Config;

/**
 * 资源调度模型
 * vRR：轮询
 * vPF：最大载干比
 * vFairness：公平调度
 *
 */

public enum EnumResourceSheldual {
    vRR("RoundRobin",0),
    vPF("MC",1),
    vFairness("Proportional fairness",2);
    private String vName;
    private int vIndex;

    EnumResourceSheldual(String a,int b){
        this.vName=a;
        this.vIndex=b;
    }

    int get_value(){
        return vIndex;
    }
    public static EnumResourceSheldual get_model(int vIndex){
        switch (vIndex) {
            case 0:
                return vRR;
            case 1:
                return vPF;
            case 2:
                return vFairness;
            default:
                return vRR;
        }
    }
}
