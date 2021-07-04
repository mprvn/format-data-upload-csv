package com.pm.matchpointoptions.formatdateuploadcsv.service;

import com.pm.matchpointoptions.formatdateuploadcsv.entity.BankDetails;
import com.pm.matchpointoptions.formatdateuploadcsv.model.BankDetailsVO;
import com.pm.matchpointoptions.formatdateuploadcsv.repository.BankDetailsRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BankDetailsServiceTest {

    @Mock private BankDetailsRepository bankDetailsRepository;

    private BankDetails bankDetailsMock = getBankdetails();

    @InjectMocks private BankDetailsService bankDetailsService;


    @Before("")
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test void saveBankDetails() {
        Mockito.when(bankDetailsRepository.save(Mockito.any(BankDetails.class))).thenReturn(
                bankDetailsMock
        );
        BankDetailsVO bankDetailsVO = new BankDetailsVO();
        bankDetailsVO.setBank("HDFC Bank");
        bankDetailsVO.setBank("1234-1234-1234-1234");
        bankDetailsVO.setExpiryDate("2017-11-01");
        BankDetails details =  bankDetailsService.saveBankDetails(bankDetailsVO);
        assertEquals(details.getBank(),bankDetailsMock.getBank() );
        assertEquals(details.getCardNumber(),bankDetailsMock.getCardNumber() );
    }

    private BankDetails getBankdetails(){
        return new BankDetails(
                "HDFC Bank",
                "1234-1234-1234-1234",
                LocalDate.parse("2017-11-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @Test
    void getBankDetails() {
        BankDetails bankDetails = getBankdetails();
        List<BankDetails> list =  Arrays.asList(bankDetails );
        Mockito.when(bankDetailsRepository.findAll()).thenReturn(list);
        List<BankDetails> bankDetailsList = bankDetailsService.getBankDetails();
        assertEquals(1,bankDetailsList.size());
        assertEquals(bankDetails.getBank(),bankDetailsList.get(0).getBank());
        assertEquals(bankDetails.getCardNumber(),bankDetailsList.get(0).getCardNumber());
        assertEquals(bankDetails.getExpiryDate(),bankDetailsList.get(0).getExpiryDate());
    }

    @Test
    void saveAllBankDetail() {
        List<BankDetails> list = Arrays.asList(getBankdetails());
        Mockito.when(bankDetailsRepository.saveAll(Mockito.any())).thenReturn(list);
        Mockito.when(bankDetailsRepository.findAll()).thenReturn(list);
       List<BankDetails> details =  bankDetailsService.saveAllBankDetail(list);
        assertEquals(details.get(0).getBank(),bankDetailsMock.getBank() );
        assertEquals(details.get(0).getCardNumber(),bankDetailsMock.getCardNumber() );

    }
}