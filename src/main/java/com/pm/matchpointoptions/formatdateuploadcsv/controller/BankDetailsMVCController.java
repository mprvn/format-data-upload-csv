package com.pm.matchpointoptions.formatdateuploadcsv.controller;

import com.pm.matchpointoptions.formatdateuploadcsv.entity.BankDetails;
import com.pm.matchpointoptions.formatdateuploadcsv.model.BankDetailsVO;
import com.pm.matchpointoptions.formatdateuploadcsv.service.BankDetailsService;
import com.pm.matchpointoptions.formatdateuploadcsv.service.CsvFileProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class BankDetailsMVCController {

    @Autowired private BankDetailsService bankDetailsService;
    @Autowired private CsvFileProcessorService csvFileProcessorService;

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/show-bank-details")
    public String showBankDetails(Model model) {

        List<BankDetails> bankDetails = bankDetailsService.getBankDetails();
        model.addAttribute("bankDetails",bankDetails);
        return "index";
    }

    @PostMapping("/add-bank-details")
    public String addBankDetails(BankDetailsVO bankDetailsVO, Model model) {
        bankDetailsService.saveBankDetails(bankDetailsVO);
        return "redirect:/show-bank-details";
    }

    @PostMapping("/upload")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {
            model.addAttribute("bankDetails",csvFileProcessorService.save(file)); ;
            }
        return "redirect:/show-bank-details";
    }
}
