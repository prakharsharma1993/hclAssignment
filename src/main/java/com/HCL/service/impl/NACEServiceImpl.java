package com.HCL.service.impl;

import com.HCL.dto.NACEDTO;
import com.HCL.entity.NACE;
import com.HCL.service.NACEService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.HCL.repository.NaceRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class NACEServiceImpl implements NACEService {

    @Autowired
    NaceRepo naceRepo;

    @Override
    public Boolean putNACEDetails(MultipartFile file) {
        try {
            List<NACE> naces = importCSV(file.getInputStream());
            naceRepo.saveAll(naces);
        } catch (IOException e) {
            log.info("Failed to store data " + e.getMessage());
            throw new RuntimeException("Error while importing data :: "+e.getMessage());
        }
        return true;
    }

    @Override
    public NACEDTO getNACEDetails(Long orderId) {

        NACE nace = naceRepo.findByOrderId(orderId);
        if (Objects.nonNull(nace)) {
            NACEDTO nacedto = new NACEDTO();
            nacedto.setOrder(nace.getOrderId());
            nacedto.setLevel(nace.getLevel());
            nacedto.setCode(nace.getCode());
            nacedto.setParent(nace.getParentId());
            nacedto.setDescription(nace.getDescription());
            nacedto.setItemIncludes(nace.getItemIncludes().replaceAll("\n", ""));
            nacedto.setItemAlsoIncludes(nace.getItemAlsoIncludes().replaceAll("\n", ""));
            nacedto.setRulings(nace.getRulings().replaceAll("\n", ""));
            nacedto.setItemExcludes(nace.getItemExcludes().replaceAll("\n", ""));
            nacedto.setReferenceToISICRev(nace.getReferenceToISICRev());
            return nacedto;
        } else {
            return null;
        }

    }

    public static String TYPE = "text/csv";

    public Boolean validateCSV(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<NACE> importCSV(InputStream is) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser records = new CSVParser(bufferedReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
            List<NACE> dataList = new ArrayList<NACE>();
            Iterable<CSVRecord> csvRecords = records.getRecords();
            for (CSVRecord csvRecord : csvRecords) {

                // We can add column names in constant files
                NACE data = new NACE();
                data.setOrderId(Long.parseLong(csvRecord.get("Order")));
                data.setLevel(Integer.parseInt(csvRecord.get("Level")));
                data.setCode(csvRecord.get("Code"));
                data.setParentId(csvRecord.get("Parent"));
                data.setDescription(csvRecord.get("Description"));
                data.setItemIncludes(csvRecord.get("This item includes"));
                data.setItemAlsoIncludes(csvRecord.get("This item also includes"));
                data.setRulings(csvRecord.get("Rulings"));
                data.setItemExcludes(csvRecord.get("This item excludes"));
                data.setReferenceToISICRev(csvRecord.get("Reference to ISIC Rev. 4"));
                dataList.add(data);
            }
            return dataList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }
}
