package service;

import com.littlepay.dto.TripResponse;
import com.littlepay.service.TapProcessor;
import com.littlepay.utility.TapFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class TapProcessorTest {
    
    @Test
    public void testProcess() throws IOException {
        TapProcessor tapProcessor = new TapProcessor();
        TapFileReader tapFileReader= new TapFileReader();

        List<TripResponse> resultList = tapProcessor.process(tapFileReader.getTaps("src/main/resources/taps.csv"));
        Assertions.assertTrue(resultList.stream().filter(criteria->criteria.getDurationSeconds().equals(Long.parseLong("300")) &&
                criteria.getBusId().equals("Bus37")&&
                criteria.getChargeAmount().contentEquals("$3.2500") &&
                criteria.getPan().contentEquals("5500005555555559") &&
                criteria.getStatus().equals(TripResponse.Status.COMPLETED)
        ).findAny().isPresent());
    }


    @Test
    public void testProcessWithInCompleteTap() throws IOException {
        TapProcessor tapProcessor = new TapProcessor();
        TapFileReader tapFileReader= new TapFileReader();

        List<TripResponse> resultList = tapProcessor.process(tapFileReader.getTaps("src/main/resources/tapsInComplete.csv"));
        Assertions.assertTrue(resultList.stream().anyMatch(criteria->criteria.getDurationSeconds()==null &&
                criteria.getFromStopId().equals("Stop1") &&
                criteria.getBusId().equals("Bus37")&&
                criteria.getChargeAmount().contentEquals("$7.3000") &&
                criteria.getPan().contentEquals("5500005555555559") &&
                criteria.getStatus().equals(TripResponse.Status.INCOMPLETE)
        ));
    }

    @Test
    public void testProcessWithCancelledTrip() throws IOException {
        TapProcessor tapProcessor = new TapProcessor();
        TapFileReader tapFileReader= new TapFileReader();

        List<TripResponse> resultList = tapProcessor.process(tapFileReader.getTaps("src/main/resources/tapsCancelledScenario.csv"));
        Assertions.assertTrue(resultList.stream().anyMatch(criteria ->
                criteria.getDurationSeconds().equals(Long.parseLong("60")) &&
                        criteria.getFromStopId().equals("Stop1") &&
                        criteria.getCompanyId().equals("Company1") &&
                        criteria.getBusId().equals("Bus37") &&
                        criteria.getChargeAmount().contentEquals("$0.0000") &&
                        criteria.getPan().contentEquals("5500005555555559") &&
                        criteria.getStatus().equals(TripResponse.Status.CANCELLED)
        ));
    }


    @Test
    public void testProcessWithComplexCase() throws IOException {
        TapProcessor tapProcessor = new TapProcessor();
        TapFileReader tapFileReader= new TapFileReader();

        List<TripResponse> resultList = tapProcessor.process(tapFileReader.getTaps("src/main/resources/tapsComplexCase.csv"));

        Assertions.assertTrue(resultList.stream().filter(criteria->criteria.getDurationSeconds().equals(Long.parseLong("300")) &&
                criteria.getBusId().equals("Bus37")&&
                criteria.getChargeAmount().contentEquals("$3.2500") &&
                criteria.getPan().contentEquals("5500005555555559") &&
                criteria.getStatus().equals(TripResponse.Status.COMPLETED)
        ).findAny().isPresent());


        Assertions.assertTrue(resultList.stream().anyMatch(criteria->criteria.getDurationSeconds()==null &&
                criteria.getFromStopId().equals("Stop1") &&
                criteria.getBusId().equals("Bus39")&&
                criteria.getToStopId()==null &&
                criteria.getChargeAmount().contentEquals("$7.3000") &&
                criteria.getPan().contentEquals("5500005555555579") &&
                criteria.getStatus().equals(TripResponse.Status.INCOMPLETE)
        ));


        Assertions.assertTrue(resultList.stream().anyMatch(criteria ->
                        criteria.getDurationSeconds().equals(Long.parseLong("60")) &&
                        criteria.getFromStopId().equals("Stop1") &&
                                criteria.getToStopId().equals("Stop1") &&
                        criteria.getCompanyId().equals("Company1") &&
                        criteria.getBusId().equals("Bus38") &&
                        criteria.getChargeAmount().contentEquals("$0.0000") &&
                        criteria.getPan().contentEquals("5500005555555569") &&
                        criteria.getStatus().equals(TripResponse.Status.CANCELLED)
        ));
    }


    
}
