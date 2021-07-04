package com.pm.matchpointoptions.formatdateuploadcsv.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.matchpointoptions.formatdateuploadcsv.entity.BankDetails;
import com.pm.matchpointoptions.formatdateuploadcsv.model.BankDetailsVO;
import com.pm.matchpointoptions.formatdateuploadcsv.service.BankDetailsService;
import com.pm.matchpointoptions.formatdateuploadcsv.service.CsvFileProcessorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {BankDetailsController.class})
@WebMvcTest(BankDetailsController.class)
class BankDetailsControllerTest {
    @Autowired
    WebApplicationContext wac;

    @MockBean
    private BankDetailsService bankDetailsService;
    @MockBean private CsvFileProcessorService csvFileProcessorService;
    @MockBean
    MultipartFile file;

    @Autowired private MockMvc mockMvc;
    @Test
    void saveBankDetails() throws Exception {

        String json =  "{\"bank\": \"Dutch Canada\",\n" +
                "                \"cardNumber\": \"5601-xxxx-xxxx-xxxx\",\n" +
                "                \"expiryDate\": \"2020-12-03\"\n" +
                "    }";
        BankDetailsVO bankDetailsVO = new BankDetailsVO();
        bankDetailsVO.setBank("Hdfc Bank");
        bankDetailsVO.setBank("1234-1234-1234-1234");
        bankDetailsVO.setExpiryDate("2ßß017-11-01");
        Mockito.when(bankDetailsService.saveBankDetails(bankDetailsVO)).thenReturn(getBankdetails());
        mockMvc.perform(post("/bankdetails").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    void getBankDetails() throws Exception {
        List<BankDetails> list = Arrays.asList(getBankdetails());
        Mockito.when(bankDetailsService.getBankDetails()).thenReturn(list);
        mockMvc.perform(get("/bankDetails"))
                .andExpect(status().isOk());

    }

//    @Test
//    void uploadCsvFiles() throws Exception {
//        List<BankDetails> list = Arrays.asList(getBankdetails());
//        Mockito.when(csvFileProcessorService.save(Mockito.any())).thenReturn(list);
//        File f = new File(BankDetailsMVCControllerTest.class.getResource("bankdetail.csv").getPath());
//        FileInputStream fis = new FileInputStream(f);
//        MockMultipartFile multipartFile = new MockMultipartFile("file", fis);
//        mockMvc.perform(post("/upload-csv-file").contentType(MediaType.MULTIPART_FORM_DATA).content(multipartFile.getBytes()))
//                .andExpect(status().isOk());
//
//    }

    private BankDetails getBankdetails(){
        return new BankDetails(
                "HDFC Bank",
                "1234-1234-1234-1234",
                LocalDate.parse("2017-11-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}