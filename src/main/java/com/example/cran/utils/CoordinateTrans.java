package com.example.cran.utils;


/**
 * 实现坐标间的转换
 *
 */
public class CoordinateTrans {
    //原点坐标,当前坐标是郑州光合大厦十字路口精确经纬
    public static final double ORIGIN_X=113.627181;
    public static final double ORIGIN_Y=34.748906;

    //相对原点的偏移（从数据库中读出来的X要先除以X的偏移值再加上原点的X坐标，Y也一样）
	public static final double OFFSET_X=12743.518382;
	public static final double OFFSET_Y=-72072.072072;

    //X转为经度
    public static double XToLongitude(double x){
        return x/OFFSET_X+ORIGIN_X;
    }

    //Y转为纬度
    public static double YToLatitude(double y){
        return y/OFFSET_Y+ORIGIN_Y;
    }

    //经度转为X
    public static double longitudeToX(double lo){
        return (lo-ORIGIN_X)*OFFSET_X;
    }

    //纬度转为Y
    public static double latitudeToY(double la){
        return (la-ORIGIN_Y)*OFFSET_Y;
    }
}
