package utility;

import com.littlepay.dto.Fare;
import com.littlepay.utility.TapFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TapFileReaderTest {

    @Test
    public void getFaresTable() throws IOException {

        Map<String, List<Fare>> faresTable = new TapFileReader().getFaresTable();
        // just testing for one key is sufficient for this test
        Assertions.assertTrue(faresTable.entrySet().stream().anyMatch(fare->fare.getKey().equals("Stop1")));
        Assertions.assertEquals(List.of(Fare.builder()
                        .source("Stop1")
                        .cost(BigDecimal.valueOf(Long.parseLong("325")))
                        .currency("$")
                        .destination("Stop2")
                        .multiplier(BigDecimal.valueOf(Long.parseLong("100")))
                .build(),
                Fare.builder()
                        .source("Stop1")
                        .destination("Stop3")
                        .cost(BigDecimal.valueOf(Long.parseLong("730")))
                        .currency("$")
                        .multiplier(BigDecimal.valueOf(Long.parseLong("100")))
                        .build()
                )
        ,faresTable.get("Stop1"));
    }
}
