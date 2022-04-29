package com.example.cran.simulation.Config;

/**
 * 业务量模型
 * vVoIP：VOIP
 * vNonVoIP：非VOIP
 *
 */
public enum EnumUeTrafficModel {
    vVoIP("VoIP",0),vNonVoIP("NonVoIP",1);

    private String vName;
    private int vIndex;

    EnumUeTrafficModel(String a,int b){
        this.vName=a;
        this.vIndex=b;
    }

    int get_value(){
        return vIndex;
    }
    public static EnumUeTrafficModel get_model(int vIndex){
        switch (vIndex) {
            case 0:
                return vVoIP;
            case 1:
                return vNonVoIP;
            default:
                return vVoIP;
        }
    }
}
