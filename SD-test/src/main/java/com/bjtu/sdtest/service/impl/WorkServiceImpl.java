package com.bjtu.sdtest.service.impl;

import com.bjtu.sdtest.mapper.DatasetMapper;
import com.bjtu.sdtest.model.Logic;
import com.bjtu.sdtest.service.StorageService;
import com.bjtu.sdtest.exception.FileStorageException;
import com.bjtu.sdtest.pojo.table.Dataset;
import com.bjtu.sdtest.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class WorkServiceImpl implements WorkService {
    private String ModelLocation;
    @Autowired
    private DatasetMapper datasetMapper;
    public WorkServiceImpl() {
        this.ModelLocation = "D:\\Application\\Code\\softwaretest\\software-defect-testing\\SD-test\\src\\main\\resources\\model\\model1.hh";
    }
    @Override
    public boolean predict(String dataset_location) throws IOException {
        Logic logic = Logic.readObject(ModelLocation);
        return logic.testLabel(logic.test(logic.readData(dataset_location)));
    }
}