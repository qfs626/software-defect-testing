package com.bjtu.sdtest.service;

import com.bjtu.sdtest.pojo.table.Dataset;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface WorkService {
    boolean predict(String dataset_location) throws IOException;
}