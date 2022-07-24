package com.HCL.service;


import com.HCL.dto.NACEDTO;
import com.HCL.entity.NACE;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface NACEService {


    public Boolean putNACEDetails(MultipartFile multipartFile);

    public NACEDTO getNACEDetails(Long orderId);

    public Boolean validateCSV(MultipartFile file);


}
