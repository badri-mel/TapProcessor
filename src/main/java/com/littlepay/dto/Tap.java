package com.littlepay.dto;

import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Tap {
    public static final String DD_MM_YYYY_HH_MM_SS = " dd-MM-yyyy HH:mm:ss";
    private Integer id;

    @CsvDate(value = DD_MM_YYYY_HH_MM_SS)
    private LocalDateTime dateTimeUtc;
    private String tapType;
    private String stopId;
    private String companyId;
    private String busId;
    private String pan;
}
