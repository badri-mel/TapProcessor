package com.littlepay.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.littlepay.dto.Fare;
import com.littlepay.dto.Tap;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TapFileReader {
    private static final String TAP_FILE_PATH="src/main/resources/taps.csv";
    private static final String FARES_FILE_PATH="src/main/resources/Fares.json";


    public List<Tap> getTaps(String filename) throws FileNotFoundException {
        CSVReader csvreader = new CSVReader(new FileReader(filename));
        return new CsvToBeanBuilder<Tap>(csvreader)
                .withType(Tap.class)
                .build().parse();
    }

    //Map of(Source,List<Fare>)
    public Map<String,List<Fare>> getFaresTable() throws IOException {
        ObjectMapper objectMapper= new ObjectMapper();
        List<Fare> listofFares = objectMapper.readValue(new File(FARES_FILE_PATH), new TypeReference<>() {
        });

        return listofFares.stream().collect(Collectors.groupingBy(Fare::getSource, Collectors.toList()));
    }

}
