import com.littlepay.dto.Tap;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TapTest {

    private static final String TAP_FILE_PATH="src/main/resources/taps.csv";
    @Test
    void testTapParsingCsv() throws FileNotFoundException {
        //Note Doing these CSVReader is overkill , i can construct the objects.But the tests are written first so left the code as such no harm.
        FileReader reader = new FileReader(TAP_FILE_PATH);
        CSVReader csvReader = new CSVReader(reader);

        List<Tap> taps = new CsvToBeanBuilder<Tap>(csvReader)
                .withType(Tap.class)
                .build().parse();
        Assertions.assertTrue(taps.stream().anyMatch(tap -> tap.getDateTimeUtc().equals(LocalDateTime.parse(" 22-01-2018 13:00:00",
                DateTimeFormatter.ofPattern(Tap.DD_MM_YYYY_HH_MM_SS)))
                && tap.getTapType().equals(" ON")
                && tap.getStopId().equals(" Stop1")
                && tap.getCompanyId().equals(" Company1")
                && tap.getBusId().equals(" Bus37")
                && tap.getPan().equals(" 5500005555555559")

        ));
        Assertions.assertTrue(taps.stream().anyMatch(tap -> tap.getDateTimeUtc().equals(LocalDateTime.parse(" 22-01-2018 13:05:00",
                DateTimeFormatter.ofPattern(Tap.DD_MM_YYYY_HH_MM_SS)))
                        && tap.getTapType().equals(" OFF")
                        && tap.getStopId().equals(" Stop2")
                        && tap.getCompanyId().equals(" Company1")
                        && tap.getBusId().equals(" Bus37")
                        && tap.getPan().equals(" 5500005555555559")
                ));


    }
}
