package com.bjtu.sdtest.service.impl;

import com.bjtu.sdtest.mapper.DatasetMapper;
import com.bjtu.sdtest.service.StorageService;
import com.bjtu.sdtest.exception.FileStorageException;
import com.bjtu.sdtest.pojo.table.Dataset;
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
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class StorageServiceImpl implements StorageService {

    private Path fileStorageLocation;
    @Autowired
    private DatasetMapper datasetMapper;
    public StorageServiceImpl() {

    }

    @Override
    public String storeFile(MultipartFile file, Dataset dataset) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("File name contains invalid path sequence");
            }
            dataset.setDatasetName(fileName);
            this.fileStorageLocation = Paths.get("D:\\Application\\Code\\softwaretest\\software-defect-testing\\SD-test\\src\\main\\resources\\csv\\"+dataset.getName());
            try {
                Files.createDirectories(this.fileStorageLocation);
            } catch (Exception ex) {
                throw new FileStorageException("Could not create directory where the uploaded files will be stored");
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            dataset.setDatasetLocation(targetLocation.toString());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            datasetMapper.insert(dataset);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again later!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try{
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            }else{
                throw new FileStorageException("File not found" + fileName);
            }
        }catch (MalformedURLException ex){
            throw new FileStorageException("File not found" + fileName);
        }
    }
}