package com.pm.matchpointoptions.formatdateuploadcsv.controller;

import com.pm.matchpointoptions.formatdateuploadcsv.entity.BankDetails;
import com.pm.matchpointoptions.formatdateuploadcsv.model.BankDetailsVO;
import com.pm.matchpointoptions.formatdateuploadcsv.service.BankDetailsService;
import com.pm.matchpointoptions.formatdateuploadcsv.service.CsvFileProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
public class BankDetailsController {

    @Autowired private BankDetailsService bankDetailsService;

    @Autowired private CsvFileProcessorService csvFileProcessorService;

    @PostMapping("/bankdetails")
    public BankDetails saveBankDetails(@RequestBody BankDetailsVO bankDetails) {
        return bankDetailsService.saveBankDetails(bankDetails);
    }
    @GetMapping("/bankdetails")
    public List<BankDetails> getBankDetails() {
        return bankDetailsService.getBankDetails();
    }

    @PostMapping("/upload-csv-file")
    public List<BankDetails> uploadCsvFiles(@RequestParam("file") MultipartFile file){
       List<BankDetails> bankDetails = Collections.emptyList();
       if (csvFileProcessorService.hasCSVFormat(file)) {
           bankDetails = csvFileProcessorService.save(file);
        }
       return bankDetails;
    }



}
