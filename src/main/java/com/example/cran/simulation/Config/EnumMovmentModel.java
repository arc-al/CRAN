package com.example.cran.simulation.Config;

/**
 * 用户移动模型
 * vAllRandomModel：全随机模型
 * vPatialRandomModel：部分随机模型
 * vDirectionRandomModel：定向随机模型（未实现）
 *
 */
public enum EnumMovmentModel {
    //private int pre[]={1,2,3};
    vAllRandomModel(0),//完全随机模型
    vPatialRandomModel(1),//部分随机模型
    vDirectionRandomModel(2),//定向随机模型
    ;


    private int vIndex;

    private EnumMovmentModel() {
    }
    EnumMovmentModel(int b){
        this.vIndex=b;
    };
    int get_value(){
        return vIndex;
    }
    public static EnumMovmentModel get_model(int vIndex){
        switch (vIndex) {
            case 0:
                return EnumMovmentModel.vAllRandomModel;
            case 1:
                return EnumMovmentModel.vPatialRandomModel;
            case 2:
                return EnumMovmentModel.vDirectionRandomModel;
            default:
                return EnumMovmentModel.vAllRandomModel;
        }
    }
}
