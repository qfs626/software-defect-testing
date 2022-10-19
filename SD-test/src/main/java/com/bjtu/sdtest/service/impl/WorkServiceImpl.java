package com.bjtu.sdtest.service.impl;

import com.bjtu.sdtest.Resp.BaseResp;
import com.bjtu.sdtest.Resp.RespEnum;
import com.bjtu.sdtest.model.Logic;
import com.bjtu.sdtest.service.WorkService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class WorkServiceImpl implements WorkService {
    private String ModelLocation;

    private Logic logic = null;

    public WorkServiceImpl() {
//        //乔芳盛
//        this.ModelLocation = "D:\\Application\\Code\\softwaretest\\software-defect-testing\\SD-test\\src\\main\\resources\\model\\model1.hh";
        //谢志贤
        this.ModelLocation = "H:\\gitrepository\\software-defect-testing\\SD-test\\src\\main\\resources\\model\\model1.hh";
        logic = new Logic(0.01,62);
        try {
            logic.readFromTxt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public BaseResp<RespEnum> predict(String dataset_location) throws IOException {
        if(logic.testLabel(logic.readData(dataset_location).get(0)))
            return BaseResp.success(RespEnum.HAVE_NO_BUG);
        else
            return BaseResp.success(RespEnum.HAVE_BUG);
    }

    @Override
    public BaseResp<RespEnum> predict(List<Double> xList) throws IOException {
        if(logic.testLabel(xList))
            return BaseResp.success(RespEnum.HAVE_NO_BUG);
        else
            return BaseResp.success(RespEnum.HAVE_BUG);
    }
}