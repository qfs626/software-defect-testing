package com.bjtu.sdtest.model;
import com.csvreader.CsvReader;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;

import java.util.*;
import java.io.*;

/**
 * <Description> Logic
 *
 * @author 26802
 * @version 1.0
 * @ClassName Logic
 * @taskId
 * @see com.bjtu.sdtest.model
 */
public class Logic implements Serializable {

    private static final long serialVersionUID = 32660034130636461L;
    private List<Double> array_w;
    private double a = 0.01; //学习率
    private int numofW = 0; //参数个数
    private double loss = 0.0; //用于保存loss

    public Logic() {
    }

    public Logic(double a, int numofW) {
        this.a = a;
        this.numofW = numofW;
        array_w = new ArrayList<>(numofW);

        Random random = new Random();
        //初始化参数数组
        for (int i = 0; i < numofW; i++) {
            array_w.add(random.nextDouble()/100);
        }
    }

    private double sigmoid(double x){
        return 1 / (1 + Math.exp(-x));
    }

    //预测方法
    public double predict(List<Double> array_x){
        double result = 0.0;
        for (int i = 0; i < array_x.size(); i++) {
            if(i == 0){
                result += array_w.get(i);
            }else {
                result += array_w.get(i) * array_x.get(i-1);
            }
        }
        return sigmoid(result);
    }

    //损失函数
    public double costFunction(List<Double> array_x,int y){
        double _y = predict(array_x);
        return -y*Math.log(_y) - (1 - y)*Math.log(1 - _y);
    }

    //更新参数函数
    public void update(List<List<Double>> data){
        List<Double> savaArray = new ArrayList<>(numofW);
        for (int i = 0; i < numofW; i++) {
            savaArray.add(0.0);
        }
        int length = data.size();
        //求和
        for (int i = 0; i < length; i++) {
            List<Double> array = data.get(i);
            for (int i1 = 0; i1 < numofW; i1++) {
                if(i1 == 0){
                    Double tempDouble = savaArray.get(i1);
                    Double predictValue = predict(array.subList(0,array.size()-1));
                    savaArray.set(i1,tempDouble + predictValue - array.get(array.size()-1));
                }else {
                    Double tempDouble = savaArray.get(i1);
                    Double predictValue = predict(array.subList(0,array.size()-1));
                    Double tempValue = (predictValue - array.get(array.size()-1))*array.get(i1-1);
                    savaArray.set(i1,tempDouble + tempValue);
                }
            }
        }
        //更新
        for (int i = 0; i < numofW; i++) {
            double temp = array_w.get(i) - a * (savaArray.get(i) / length);
            array_w.set(i,temp);
        }
        
    }

    //训练
    public void train(int round,int patchSize,List<List<Double>> data){
        List<Double> lossArray = new ArrayList<>();
        List<Double> accArray = new ArrayList<>();
        int nowIndex = 0;
        System.out.println("Start training....");

        for (int i = 0; i < round; i++) {
            System.out.println("round " + i);
            while (nowIndex + patchSize <= data.size()){
                update(data.subList(nowIndex,nowIndex + patchSize));
                List<Double> result = test(data.subList(nowIndex, data.size()));
                nowIndex += patchSize;
            }
            update(data.subList(nowIndex,data.size()));
//            //计算此轮损失
            List<Double> result = test(data.subList(nowIndex, data.size()));
            nowIndex = 0;
        }
        System.out.println("模型正在保存");
        try {
            this.saveTxt();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //acc输出函数
    public List<Double> test(List<List<Double>> data){
        int length = data.size();
        int right = 0;

        for (int i = 0; i < length; i++) {
            List<Double> array = data.get(i);
            double predict = predict(array.subList(0, numofW - 1));
            if(array.get(array.size()-1) == 1.0 && predict >= 0.5){
                right++;
            }
            if(array.get(array.size()-1) == 0.0 && predict < 0.5){
                right++;
            }
        }

        System.out.println("-----------------The acc is " + (right / 1.0 /length));
        List<Double> result = new ArrayList<>();

        result.add(right/1.0 / length);
        return result;
    }

    //读取
    public List<List<Double>> readData(String path) throws IOException {
        List<List<Double>> result = new ArrayList<>();

        // 第一参数：读取文件的路径 第二个参数：分隔符（不懂仔细查看引用百度百科的那段话） 第三个参数：字符集
        CsvReader csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));

        csvReader.readHeaders();

        // 读取每行的内容
        int num = 62;
        while (csvReader.readRecord()) {
            List<Double> temp = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                if (i != num-1)
                    temp.add(Double.parseDouble(csvReader.get(i)));
                else{
                    if(csvReader.get(i).equals("clean")){
                        temp.add(1.0);
                    }else {
                        temp.add(0.0);
                    }
                }
            }
            result.add(temp);
        }
        return result;

    }

    //保存
    public void save(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/model/model1.hh"));
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveTxt() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Double aDouble : array_w) {
            stringBuilder.append(aDouble.toString()).append(",");
        }
        FileOutputStream fileOutputStream = null;
        File file = new File("src/main/resources/model/test.txt\"");
        if(!file.exists()){
            file.createNewFile();
        }
        fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(stringBuilder.toString().getBytes("gbk"));
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    //用于测试
    /**
     * @return true表示clean,false表示buggy
     * */
    public boolean testLabel(List<Double> array_x){
        double predict = predict(array_x);
        return predict >= 0.5;
    }

    //反序列化对象
    public static Logic readObject(String modelLocation){
        File file = new File(modelLocation);
        Logic logic = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            logic = (Logic) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logic;
    }
    //读取保存的参数
    public void readFromTxt() throws IOException {
//        //乔芳盛
//        File file = new File("D:\\Application\\Code\\softwaretest\\software-defect-testing\\SD-test\\src\\main\\resources\\model\\test.txt");// Text文件
        //谢志贤
        File file = new File("src/main/resources/model/test.txt");// Text文件
        BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
        String s = null;
        s = br.readLine();
        if(s == null)
            return ;
        String[] strings = s.split(",");
        for (int i = 0; i < strings.length; i++) {
            this.array_w.set(i,Double.parseDouble(strings[i]));
        }
        br.close();

    }
    public static void main(String[] args) {
        Logic logic = new Logic(0.01,62);
        try {
            logic.readFromTxt();
            System.out.println("sda");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
