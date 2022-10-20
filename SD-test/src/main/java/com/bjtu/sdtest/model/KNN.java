package com.bjtu.sdtest.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by HUAWEI on 2022/10/15.
 */
public class KNN {

    private List<Data> dataList;

    public KNN(List<Data> DataList) {

        this.dataList = DataList;

    }

    private static List<Data> initDataSet(String fileName) throws IOException {
        long id = 1;
        List<Data> list = new ArrayList<Data>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            Data data = new Data();
            data.setId(id);
            String[] s = line.split(",");
            for(int i = 0;i < Data.getFeNum();i++){
                data.getDataFeList()[i] = Double.parseDouble(s[i]);
            }
            data.setType(s[Data.getFeNum()]);
            list.add(data);
            id++;
        }
        return list;
    }


    //归一化
    public static void normalization(List<Data> DataList){

        //找出最大值最小值
        double[] maxFe = new double[Data.getFeNum()];
        double[] minFe = new double[Data.getFeNum()];
        for(Data data:DataList){
            for(int i = 0;i < Data.getFeNum();i++){
                if(data.getDataFeList()[i] > maxFe[i]){
                    maxFe[i] = data.getDataFeList()[i];
                }
                if(data.getDataFeList()[i] < minFe[i]){
                    minFe[i] = data.getDataFeList()[i];
                }
            }
        }

        //归一化
        for(Data data:DataList){
            for(int i = 0;i < Data.getFeNum();i++){
                if((maxFe[i] - minFe[i])!= 0) {
                    data.getDataFeList()[i] = (data.getDataFeList()[i] - minFe[i])/(maxFe[i] - minFe[i]);
                }
            }
        }
    }

    // 欧式距离计算
    public static double oudistance(Data data1, Data data2) {
        double temp = 0;
        for(int i = 0;i < Data.getFeNum();i++)
        temp += Math.pow(data1.getDataFeList()[i] - data2.getDataFeList()[i], 2);
        return Math.sqrt(temp);
    }

    //算法核心
    public static boolean knn(Data data, List<Data> dataset, int k) {
        //将总数据中每个点 与 单个测试点 计算距离并赋值到 总数据的每一个点中
        for (Data data2 : dataset) {
            double distance = oudistance(data, data2);
            data2.setDistance(distance);
        }
        // 对距离进行排序，小到大排序
        Collections.sort(dataset);
        // 从前k个样本中，找到出现频率最高的类别
        int type1 = 0, type2 = 0;
        //取前k个数据，也是取最靠近单个测试数据点的K个点
        for (int i = 0; i < k; i++) {
            Data d = dataset.get(i);
            if (d.getType().equals("clean")) {
                type1++;
            } else if (d.getType().equals("buggy")) {
                type2++;
            }
        }
        if (type1 > type2) {
            return true;
        }else {
            return false;
        }
    }

    public static int load_k(){
        StringBuilder result = new StringBuilder();
        int k = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader("D:/1.txt"));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        k = Integer.parseInt(result.toString().trim());
        return k;
    }
    public static void train()throws IOException{
        int i = 0;
        int k = 1;
        int best_k = 0;
        int max_k = 100;
        double best_score = 1;
        String type = null;
        while(k < max_k){
            List<Data> testDataSet = initDataSet("C:/Users/HUAWEI/Desktop/1/software-defect-testing/SD-test/src/main/resources/csv/PDE.csv");
            List<Data> testDataSet2 = initDataSet("C:/Users/HUAWEI/Desktop/1/software-defect-testing/SD-test/src/main/resources/csv/JDT.csv");
            List<Data> testDataSet3 = initDataSet("C:/Users/HUAWEI/Desktop/1/software-defect-testing/SD-test/src/main/resources/csv/Lucene.csv");
            //归一化数据
            testDataSet.addAll(testDataSet2);
            testDataSet.addAll(testDataSet3);
            //normalization(testDataSet);
            List<Data> newTestDataSet = testDataSet.subList(0,300);
            List<Data> newDataSet = testDataSet.subList(300,3000);
            int errorCount = 0;
            for (Data data : newTestDataSet) {
                //根据总样本推测它的类型
                if(knn(data, newDataSet, k)){
                    type = "clean";
                }else {
                    type = "buggy";
                }

                if ( !type.equals(data.getType())) {
                    errorCount++;
                }
                //System.out.println(i + ": " + "期望类型:"+type+"-------实际类型:"+data.getType());
                i++;
            }
            double rage = ((double) errorCount) / ((double) newTestDataSet.size());
            System.out.println("正确率：" + (1 - rage));
            if(rage < best_score){
                best_score = rage;
                best_k = k;
            }
            k += 2;
        }
        System.out.println("最好的k:" + best_k + "正确率：" + (1-best_score));//k = 15

    }

    public static void save(int k,List<Data> dataList)throws IOException{
        PrintStream ps = new PrintStream("D:/1.txt");
        //可能会出现异常，直接throws就行了
        System.setOut(ps);
        //把创建的打印输出流赋给系统。即系统下次向 ps输出
        System.out.println(k);

        ps.close();
    }

    public static void test(List<Data> dataList)throws IOException{

        int k = load_k();
        int i = 1;
        String type = null;
        List<Data> testData = dataList;
        List<Data> testDataSet = initDataSet("C:/Users/HUAWEI/Desktop/1/software-defect-testing/SD-test/src/main/resources/csv/PDE.csv");
        //归一化数据
        normalization(testDataSet);
        normalization(testData);

        for (Data data : testData) {
            //根据总样本推测它的类型
            if(knn(data, testDataSet, k)){
                type = "clean";
            }else {
                type = "buggy";
            }
            System.out.println(i + ": " + "期望类型:"+type);
            i++;
        }
    }

    public static boolean predict(List<Double> array_x)throws IOException{
//        List<Data> testDataSet = initDataSet("C:/Users/HUAWEI/Desktop/1/software-defect-testing/SD-test/src/main/resources/csv/PDE.csv");
//        List<Data> testDataSet2 = initDataSet("C:/Users/HUAWEI/Desktop/1/software-defect-testing/SD-test/src/main/resources/csv/JDT.csv");
//        List<Data> testDataSet3 = initDataSet("C:/Users/HUAWEI/Desktop/1/software-defect-testing/SD-test/src/main/resources/csv/Lucene.csv");
          //谢志贤
        List<Data> testDataSet = initDataSet("H:\\gitrepository\\software-defect-testing\\SD-test\\src\\main\\resources\\csv\\PDE.csv");
        List<Data> testDataSet2 = initDataSet("H:\\gitrepository\\software-defect-testing\\SD-test\\src\\main\\resources\\csv\\JDT.csv");
        List<Data> testDataSet3 = initDataSet("H:\\gitrepository\\software-defect-testing\\SD-test\\src\\main\\resources\\csv\\Lucene.csv");
        testDataSet.addAll(testDataSet2);
        testDataSet.addAll(testDataSet3);
        double[] arr = new double[array_x.size()];
        int i = 0;
        for(Double d : array_x) {
            arr[i++] = d.doubleValue();
        }
        Data data = new Data(arr);
        return knn(data,testDataSet,15);
    }

    public static void main(String[] args){

        try {
            train();
        }catch (IOException e){
            e.printStackTrace();
        }

    }



}