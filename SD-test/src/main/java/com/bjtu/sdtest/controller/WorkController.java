package com.bjtu.sdtest.controller;

import com.bjtu.sdtest.Resp.UploadFileResponse;
import com.bjtu.sdtest.exception.FileStorageException;
import com.bjtu.sdtest.service.StorageService;
import com.bjtu.sdtest.service.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.bjtu.sdtest.pojo.table.Dataset;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class WorkController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final WorkService workService;
    private List<String> supportFileFormats = new ArrayList<>(Arrays.asList("csv,txt".split(",")));

    public WorkController(WorkService workservice) {
        this.workService = workservice;
    }

    @PostMapping("/predict")
    public boolean predict(String dataset_location) throws IOException {
        return workService.predict(dataset_location);
    }
}