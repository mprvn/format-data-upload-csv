package com.pm.matchpointoptions.formatdateuploadcsv.service;

import com.pm.matchpointoptions.formatdateuploadcsv.entity.BankDetails;
import com.pm.matchpointoptions.formatdateuploadcsv.model.BankDetailsVO;
import com.pm.matchpointoptions.formatdateuploadcsv.repository.BankDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankDetailsService {

    @Autowired
    private BankDetailsRepository bankDetailsRepository;

    public BankDetails saveBankDetails(BankDetailsVO bankDetailsVo) {
        return bankDetailsRepository.save(getBankDetails(bankDetailsVo));
    }

    private BankDetails getBankDetails(BankDetailsVO bankDetailsVo) {
        final BankDetails  bankDetails = new BankDetails();
        bankDetails.setBank(bankDetailsVo.getBank());
        bankDetails.setCardNumber(bankDetailsVo.getCardNumber());
        bankDetails.setExpiryDate( LocalDate.parse(bankDetailsVo.getExpiryDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd")) );
        return bankDetails;
    }

    public List<BankDetails> getBankDetails() {
        List<BankDetails> bankDetails = bankDetailsRepository.findAll();
        if(!CollectionUtils.isEmpty(bankDetails)) {
            bankDetails = bankDetails.stream().sorted(
                    Comparator.comparing(BankDetails::getExpiryDate,Comparator.reverseOrder())).collect(Collectors.toList());
            bankDetails =  formateCardNumber(bankDetails);
        }
        return bankDetails;
    }

    /**
     *
     * @param bankDetails list of bankdetils
     * @return list of bankdetails
     */
    private List<BankDetails> formateCardNumber(List<BankDetails> bankDetails) {
        if(!CollectionUtils.isEmpty(bankDetails)) {
            bankDetails =    bankDetails.stream().map(x -> {
                        String[] strArray = x.getCardNumber().split("-");
                        for (int i = 1; i < strArray.length; i++) {
                            strArray[i] = strArray[i].replaceAll("\\d", "x");
                        }
                        x.setCardNumber(String.join("-", strArray));
                        return x;
                    }
            ).collect(Collectors.toList());
        }
        return bankDetails;
    }

    public List<BankDetails> saveAllBankDetail(List<BankDetails> bankDetails) {
         bankDetailsRepository.saveAll(bankDetails);
        return getBankDetails();
    }
}
