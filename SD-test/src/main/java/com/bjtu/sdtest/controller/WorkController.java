package com.bjtu.sdtest.controller;

import com.bjtu.sdtest.Resp.BaseResp;
import com.bjtu.sdtest.Resp.RespEnum;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
    public BaseResp<RespEnum> predict(String dataset_location) throws IOException {
        return workService.predict(dataset_location);
    }

    @PostMapping("/upAndPredict")
    public BaseResp<RespEnum> upAndPredict(@RequestParam("file")MultipartFile file,String username){
        if (!checkFormats(file.getOriginalFilename()))
            throw new FileStorageException("文件格式不符合要求");
        else {

            //起手转成字符流

            InputStream is = null;
            try {
                List<Double> xList = new ArrayList<>();
                is = file.getInputStream();
                InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isReader);
                String[] strings = br.readLine().split(",");
                for (String string : strings) {
                    xList.add(Double.valueOf(string));
                }
                //关闭流，讲究
                br.close();
                return workService.predict(xList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return BaseResp.failed(RespEnum.DEFAULT_FAIL);
    }

    private boolean checkFormats(String fileFullName){
        String suffix = fileFullName.substring(fileFullName.lastIndexOf(".") + 1).toLowerCase();
        return supportFileFormats.stream().anyMatch(suffix::contains);
    }

    @PostMapping("/list_dataset")
    public BaseResp<List<Dataset>> list_dataset(String username){
        return workService.list_dataset(username);
    }
}