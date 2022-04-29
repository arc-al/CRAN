package com.example.cran.simulation.Config;

/**
 * 系统频带资源
 * vResourceConcentation：总带宽
 * vResourceExcision：频带划分
 */
public enum EnumResourceModel {
    vResourceConcentation(0),//系统总带宽
    vResourceExcision(1),//频带划分
    ;

    private int vIndex;

    EnumResourceModel(int vIndex) {
        this.vIndex=vIndex;
    }
    int get_value(){
        return vIndex;
    }

    public static EnumResourceModel get_model(int vIndex){
        switch (vIndex) {
            case 0:
                return vResourceConcentation;
            case 1:
                return vResourceExcision;

            default:
                return vResourceConcentation;
        }
    }
}
