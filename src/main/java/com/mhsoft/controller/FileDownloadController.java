package com.mhsoft.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhsoft.utils.DownloadFile;
import com.mhsoft.utils.Utils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileDownloadController {

    @RequestMapping(value = "/api/cca/download" , method = RequestMethod.POST)
    public ResponseEntity<Resource> downloadFile (@RequestHeader String Authorization, @RequestBody String downloadFile)
            throws JsonProcessingException, IOException {
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);
        ObjectMapper objectMapper = new ObjectMapper();
        DownloadFile fileData = objectMapper.readValue( downloadFile, DownloadFile.class);




        // Set the appropriate content type for the response
       String  slipName = fileData.getSlipLink().concat("\\").concat(fileData.getSlipName());
        System.out.println(slipName);
        // File file = new File(slipName);
        //  Resource resource = new UrlResource(file.toURI());



        Resource resource = new FileSystemResource(slipName);

        // Set the appropriate content type for the response
        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);

    }

    @RequestMapping(value = "/api/download" , method = RequestMethod.POST)
    public ResponseEntity<Resource> downloadFileCustomerFile (@RequestHeader String Authorization,
                                                              @RequestBody String downloadFile)
            throws JsonProcessingException, IOException {
        int USER_ID = 0;
        String token = Utils.getInstance().getTokenFromAuthKey(Authorization);
        ObjectMapper objectMapper = new ObjectMapper();
        DownloadFile fileData = objectMapper.readValue( downloadFile, DownloadFile.class);




        // Set the appropriate content type for the response
        String  slipName = fileData.getSlipLink().concat("\\").concat(fileData.getSlipName());
        System.out.println(slipName);
        // File file = new File(slipName);
        //  Resource resource = new UrlResource(file.toURI());



        Resource resource = new FileSystemResource(slipName);

        // Set the appropriate content type for the response
        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);

    }
}
