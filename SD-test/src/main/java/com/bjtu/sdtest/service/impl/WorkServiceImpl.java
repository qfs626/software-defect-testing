package com.bjtu.sdtest.service.impl;

import com.bjtu.sdtest.Resp.BaseResp;
import com.bjtu.sdtest.Resp.RespEnum;
import com.bjtu.sdtest.model.Logic;
import com.bjtu.sdtest.service.WorkService;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class WorkServiceImpl implements WorkService {
    private String ModelLocation;

    public WorkServiceImpl() {
        this.ModelLocation = "D:\\Application\\Code\\softwaretest\\software-defect-testing\\SD-test\\src\\main\\resources\\model\\model1.hh";
    }
    @Override
    public BaseResp<RespEnum> predict(String dataset_location) throws IOException {
        Logic logic = new Logic(0.01,62);
        logic.readFromTxt();
        if(logic.testLabel(logic.test(logic.readData(dataset_location))))
            return BaseResp.success(RespEnum.HAVE_NO_BUG);
        else
            return BaseResp.success(RespEnum.HAVE_BUG);
    }
}