package com.bjtu.sdtest.service;

import com.bjtu.sdtest.pojo.table.Dataset;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String storeFile(MultipartFile file, Dataset dataset);
    Resource loadFileAsResource(String fileName);
}