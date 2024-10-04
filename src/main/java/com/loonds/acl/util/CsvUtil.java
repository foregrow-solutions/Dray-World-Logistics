package com.loonds.acl.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    public static List<String[]> importFromCsv(MultipartFile file, CSVFormat csvFormat) throws IOException {
        List<String[]> records = new ArrayList<>();
        try (CSVParser csvParser = new CSVParser(new InputStreamReader(file.getInputStream()), csvFormat)) {
            for (CSVRecord csvRecord : csvParser) {
                String[] record = new String[csvRecord.size()];
                for (int i = 0; i < csvRecord.size(); i++) {
                    record[i] = csvRecord.get(i);
                }
                records.add(record);
            }
        }
        return records;
    }
}
