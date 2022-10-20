package com.bjtu.sdtest.service;

import com.bjtu.sdtest.Resp.BaseResp;
import com.bjtu.sdtest.Resp.RespEnum;

import java.io.IOException;
import java.util.*;

public interface WorkService {
    BaseResp<RespEnum> predict(String dataset_location) throws IOException;

    <T> BaseResp<T> predict(List<Double> xList) throws IOException;
}