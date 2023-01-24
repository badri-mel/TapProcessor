package utility;

import com.littlepay.dto.Tap;
import com.littlepay.dto.TripResponse;
import com.littlepay.utility.TripFileWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TripFileWriterTest {


    @Test
    public void testWriter() throws IOException, CsvValidationException {
        TripFileWriter writer= new TripFileWriter();
        writer.writeCsv("trips.csv", List.of(TripResponse.builder()
                        .status(TripResponse.Status.COMPLETED)
                        .pan("3222222222222")
                        .started(LocalDateTime.now())
                        .finished(LocalDateTime.now())
                        .durationSeconds(10L)
                        .companyId("12222")
                        .chargeAmount("$123.000")
                .build()));
        CSVReaderHeaderAware csvHeaderReader= new CSVReaderHeaderAware(new FileReader("trips.csv"));

        Map<String, String> records = csvHeaderReader.readMap();


        Assertions.assertEquals("COMPLETED",records.get("Status") );
        Assertions.assertFalse(records.get("Finished").isBlank());
        Assertions.assertFalse(records.get("Started").isBlank());
        Assertions.assertEquals("",records.get("BusID") );
        Assertions.assertEquals("12222",records.get("CompanyId") );
        Assertions.assertEquals("$123.000",records.get("ChargeAmount") );
        Assertions.assertEquals("",records.get("FromStopId"));
        Assertions.assertEquals("",records.get("ToStopId"));
        Assertions.assertEquals("10",records.get("DurationSecs"));





    }
}
