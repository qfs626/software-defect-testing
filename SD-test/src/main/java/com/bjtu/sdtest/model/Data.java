package com.bjtu.sdtest.model;

/**
 * Created by HUAWEI on 2022/10/15.
 */
public class Data implements Comparable<Data>{

    //编号
    private long id;
    //特征
    private static int FeNum = 61;

    private double[] DataFeList = new double[FeNum];
    //分类
    private String type;
    //距离
    private double distance;

    public Data(){}

    public Data(double[] dataFeList ) {
        this.DataFeList = dataFeList;
    }


    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double[] getDataFeList() {
        return DataFeList;
    }

    public static int getFeNum() {
        return FeNum;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDataFeList(double[] dataFeList) {
        DataFeList = dataFeList;
    }

    public void setFeNum(int feNum) {
        FeNum = feNum;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Data o) {
        if (this.distance < o.getDistance()) {
            return -1;
        }else if (this.distance  > o.getDistance()) {
            return 1;
        }
        return 0;
    }

}
