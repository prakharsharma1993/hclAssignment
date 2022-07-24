package com.HCL.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.HCL.service.NACEService;
import org.springframework.web.multipart.MultipartFile;
import java.util.Objects;


@RestController
@RequestMapping(value = "/nace")
public class NACEController {


    //Swagger URL::  http://localhost:8080/hcl/swagger-ui.html


    @Autowired
    NACEService naceService;


    @ApiOperation("API is used to import Nace Data in Database ")
    @PostMapping("/importData")
    public ResponseEntity putNACEDetails(@RequestParam("file") MultipartFile file) {
       if (naceService.validateCSV(file)) {
            try {
                if (naceService.putNACEDetails(file))
                    return new ResponseEntity("Uploaded the file successfully: " + file.getOriginalFilename(), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity("Could not upload the file: " + file.getOriginalFilename() + " error Message" + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
            }
       }
        return new ResponseEntity("Invalid File.Please upload a valid csv file! ", HttpStatus.BAD_REQUEST);
    }


    @ApiOperation("API is used to fetch Nace Data details")
    @GetMapping(value = "/getData")
    public ResponseEntity getNACEDetails(@RequestParam Long orderId) {

        if (Objects.nonNull(naceService.getNACEDetails(orderId))) {
            return new ResponseEntity(naceService.getNACEDetails(orderId), HttpStatus.OK);

        } else {
            return new ResponseEntity("No data found", HttpStatus.NOT_FOUND);

        }
    }


}
