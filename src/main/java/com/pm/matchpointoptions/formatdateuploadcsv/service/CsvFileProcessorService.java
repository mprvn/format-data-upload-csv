package com.pm.matchpointoptions.formatdateuploadcsv.service;

import com.pm.matchpointoptions.formatdateuploadcsv.entity.BankDetails;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CsvFileProcessorService {
    public static String TYPE = "text/csv";

    @Autowired BankDetailsService bankDetailsService;

    public static List<BankDetails> processCSVtoBankDetails(InputStream csvFileStream){

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(csvFileStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<BankDetails> bankDetails = new ArrayList<BankDetails>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            String banKName = "";
            String cardNumber = "";
            LocalDate date ;
            for (CSVRecord csvRecord : csvRecords) {
                banKName = csvRecord.get("Bank");
                cardNumber =  csvRecord.get("Card Number").trim();
                date = LocalDate.parse(csvRecord.get("Expiry Date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                BankDetails bankDetail = new BankDetails(banKName , cardNumber, date);

                bankDetails.add(bankDetail);
            }

            return bankDetails;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public List<BankDetails> save(MultipartFile file){
        List<BankDetails> bankDetails = Collections.emptyList();
        try {
            bankDetails =   processCSVtoBankDetails(file.getInputStream());
            bankDetailsService.saveAllBankDetail(bankDetails);
            bankDetails = bankDetailsService.getBankDetails();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bankDetails;
    }

    /**
     *
     * @param file csv file input
     * @return boolean
     */
    public static boolean hasCSVFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }
}
