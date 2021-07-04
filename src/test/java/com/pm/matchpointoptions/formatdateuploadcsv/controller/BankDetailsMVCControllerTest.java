package com.pm.matchpointoptions.formatdateuploadcsv.controller;

import com.pm.matchpointoptions.formatdateuploadcsv.controller.BankDetailsMVCController;
import com.pm.matchpointoptions.formatdateuploadcsv.entity.BankDetails;
import com.pm.matchpointoptions.formatdateuploadcsv.model.BankDetailsVO;
import com.pm.matchpointoptions.formatdateuploadcsv.service.BankDetailsService;
import com.pm.matchpointoptions.formatdateuploadcsv.service.CsvFileProcessorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {BankDetailsMVCController.class})
@WebMvcTest(BankDetailsMVCController.class)
class BankDetailsMVCControllerTest {
    @Autowired
    WebApplicationContext wac;

    @MockBean private BankDetailsService bankDetailsService;
    @MockBean private CsvFileProcessorService csvFileProcessorService;
    @MockBean MultipartFile file;

    @Autowired private MockMvc mockMvc;

    @Test
    void index() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk()).andExpect(view().name("index"));
    }

    @Test
    void showBankDetails() throws Exception {
        BankDetails bankDetails = getBankdetails();
        List<BankDetails> list =  Arrays.asList(bankDetails );
        Mockito.when(bankDetailsService.getBankDetails()).thenReturn(list);
        mockMvc.perform(get("/show-bank-details"))
                .andExpect(status().isOk()).andExpect(view().name("index"));

    }


    @Test
    void addBankDetails() throws Exception {
        BankDetailsVO bankDetailsVO = new BankDetailsVO();
        bankDetailsVO.setBank("Hdfc Bank");
        bankDetailsVO.setBank("1234-1234-1234-1234");
        bankDetailsVO.setExpiryDate("2ßß017-11-01");
        Mockito.when(bankDetailsService.saveBankDetails(bankDetailsVO)).thenReturn(getBankdetails());
        mockMvc.perform(post("/add-bank-details"))
                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/show-bank-details"));
    }

//    @Test
//    void uploadCSVFile() throws Exception {
//        BankDetails bankDetails = getBankdetails();
//        List<BankDetails> list =  Arrays.asList(bankDetails );
//       File f = new File(BankDetailsMVCControllerTest.class.getResource("bankdetail.csv").getPath());
//        FileInputStream fis = new FileInputStream(f);
//        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
//
//        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
//        contentTypeParams.put("boundary", "265001916915724");
//        MediaType mediaType = new MediaType("multipart", "form-data", contentTypeParams);
//
//        Mockito.when(csvFileProcessorService.save(file)).thenReturn(list);
//    mockMvc.perform(post("/upload").contentType(mediaType).content(multipartFile.getBytes()))
//                .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/show-bank-details"));
//    }

    private BankDetails getBankdetails(){
        return new BankDetails(
                "HDFC Bank",
                "1234-1234-1234-1234",
                LocalDate.parse("2017-11-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}