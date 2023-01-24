package com.littlepay.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TripResponse {
    @CsvBindByName(column = "Started")
    @CsvBindByPosition(position = 0)
    @CsvDate(value ="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime started;
    @CsvBindByName(column = "Finished")
    @CsvBindByPosition(position = 1)
    @CsvDate(value ="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime finished;
    @CsvBindByName(column = "DurationSecs")
    @CsvBindByPosition(position = 2)
    private Long durationSeconds;
    @CsvBindByName(column = "FromStopId")
    @CsvBindByPosition(position = 3)
    private String fromStopId;

    @CsvBindByName(column = "ToStopId")
    @CsvBindByPosition(position = 4)
    private String toStopId;
    @CsvBindByName(column = "ChargeAmount")
    @CsvBindByPosition(position = 5)
    private String chargeAmount;
    @CsvBindByName(column = "CompanyId")
    @CsvBindByPosition(position = 6)
    private String companyId;
    @CsvBindByName(column = "BusID")
    @CsvBindByPosition(position = 7)
    private String busId;
    @CsvBindByName(column = "PAN")
    @CsvBindByPosition(position = 8)
    private String pan;

    @CsvBindByName(column = "Status")
    @CsvBindByPosition(position = 9)
    private Status status;

    public enum Status{
        COMPLETED,
        INCOMPLETE,
        CANCELLED
    }


    public TripResponse(Tap source, List<Fare> fareList){
        this.started=source.getDateTimeUtc();
        this.fromStopId=source.getStopId().trim();
        this.companyId=source.getCompanyId().trim();
        this.busId=source.getBusId().trim();
        this.pan= source.getPan().trim();
        this.status =Status.INCOMPLETE;

        this.chargeAmount=fareList.stream().map(Fare::getCost).max(Comparator.naturalOrder())
                .map(e->"$"+ e.divide(BigDecimal.valueOf(100L)).setScale(4,RoundingMode.HALF_UP))
                .orElse("$"+BigDecimal.ZERO);
    }




    public TripResponse(Tap start,Tap end,List<Fare> faresList){

        this.started=start.getDateTimeUtc();
        this.finished=end.getDateTimeUtc();
        this.durationSeconds=end.getDateTimeUtc().toEpochSecond(ZoneOffset.UTC)-start.getDateTimeUtc().toEpochSecond(ZoneOffset.UTC);
        this.fromStopId=start.getStopId().trim();
        this.toStopId=end.getStopId().trim();
        this.companyId=start.getCompanyId().trim();
        this.busId=start.getBusId().trim();
        this.pan=start.getPan().trim();
        this.status=start.getStopId().trim().equals(end.getStopId().trim()) ? Status.CANCELLED:Status.COMPLETED;
        this.chargeAmount= start.getStopId().trim().equals(end.getStopId().trim())? "$"+ BigDecimal.ZERO.setScale(4,RoundingMode.HALF_UP):getPriceAsString(end, faresList);

    }

    private static String getPriceAsString(Tap end, List<Fare> faresList) {
        Map<String, Fare> maps = faresList.stream().collect(Collectors.toMap(Fare::getDestination, Function.identity()));
        String result;

        if(Objects.equals(getFare(end, maps).getCost(), BigDecimal.ZERO)){
            result="$"+BigDecimal.ZERO.setScale(4,RoundingMode.HALF_UP);
        }else{
            result="$"+maps.get(end.getStopId().trim()).getCost().divide(BigDecimal.valueOf(100L)).setScale(4,RoundingMode.HALF_UP);
        }
        return result;
    }

    private static Fare getFare(Tap end, Map<String, Fare> maps) {
        //If start and end are the same
        if (!maps.containsKey(end.getStopId().trim())) {
            return Fare.builder()
                    .cost(BigDecimal.ZERO)
                    .build();
        } else {
            return maps.get(end.getStopId().trim());
        }
    }

}
