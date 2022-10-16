package com.bjtu.sdtest.service;

import com.bjtu.sdtest.Resp.BaseResp;
import com.bjtu.sdtest.Resp.RespEnum;

import java.io.IOException;

public interface WorkService {
    BaseResp<RespEnum> predict(String dataset_location) throws IOException;
}