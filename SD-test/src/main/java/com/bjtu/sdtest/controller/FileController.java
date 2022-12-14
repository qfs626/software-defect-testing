package com.bjtu.sdtest.controller;

import com.bjtu.sdtest.Resp.UploadFileResponse;
import com.bjtu.sdtest.exception.FileStorageException;
import com.bjtu.sdtest.service.StorageService;
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
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final StorageService storageService;

    private List<String> supportFileFormats =new ArrayList<>(Arrays.asList("csv,txt".split(",")));

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    private boolean checkFormats(String fileFullName){
        String suffix = fileFullName.substring(fileFullName.lastIndexOf(".") + 1).toLowerCase();
        return supportFileFormats.stream().anyMatch(suffix::contains);
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file")MultipartFile file,String username) {
        if (!checkFormats(file.getOriginalFilename()))
            throw new FileStorageException("文件格式不符合要求");
        else {

            Dataset dataset = new Dataset();
            dataset.setName(username);
            String fileName = storageService.storeFile(file, dataset);
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(fileName).toUriString();

            return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        }
    }


    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = storageService.loadFileAsResource(fileName);

        String contentType = null;
        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException ex){
            logger.info("Could not determine file type");
        }

        if (contentType == null){
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}