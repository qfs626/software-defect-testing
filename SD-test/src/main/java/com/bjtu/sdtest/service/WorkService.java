package com.bjtu.sdtest.service;

import com.bjtu.sdtest.Resp.BaseResp;
import com.bjtu.sdtest.Resp.RespEnum;
import com.bjtu.sdtest.pojo.table.Dataset;

import java.io.IOException;
import java.util.*;

public interface WorkService {
    BaseResp<RespEnum> predict(String dataset_location) throws IOException;

    BaseResp<RespEnum> predict(List<Double> xList) throws IOException;
    BaseResp<List<Dataset>> list_dataset(String user_name);
}