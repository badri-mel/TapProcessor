package com.littlepay;

import com.littlepay.service.TapProcessor;
import com.littlepay.utility.TapFileReader;
import com.littlepay.utility.TripFileWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        TapProcessor tapProcessor = new TapProcessor();
        TapFileReader tapFileReader = new TapFileReader();
        TripFileWriter tripFileWriter = new TripFileWriter();

        tripFileWriter.writeCsv(args[1], tapProcessor.process(tapFileReader.getTaps(args[0])));
    }
}