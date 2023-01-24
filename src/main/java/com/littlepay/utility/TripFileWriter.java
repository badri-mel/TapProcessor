package com.littlepay.utility;

import com.littlepay.dto.TripResponse;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TripFileWriter {



    @SneakyThrows
    public void writeCsv(String filename, List<TripResponse> rows) {

        CustomMappingStrategy columnStrategy = new CustomMappingStrategy();
        columnStrategy.setType(TripResponse.class);

        CSVWriter csvwriter= new CSVWriter(new FileWriter(filename));
        StatefulBeanToCsvBuilder csvBuilder = new StatefulBeanToCsvBuilder(csvwriter);

        csvBuilder
                .withMappingStrategy(columnStrategy)
                .build()
                .write(rows);
        csvwriter.close();
    }




}
