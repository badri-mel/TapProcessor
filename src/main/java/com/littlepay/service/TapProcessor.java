package com.littlepay.service;

import com.littlepay.dto.Fare;
import com.littlepay.dto.Tap;
import com.littlepay.dto.TripResponse;
import com.littlepay.utility.TapFileReader;

import java.io.IOException;
import java.util.*;

public class TapProcessor {

    private Map<String, List<Fare>> faresTable;

    public List<TripResponse> process(List<Tap> taps) throws IOException {
        List<TripResponse> tripResponseList = new LinkedList<>();
        TapFileReader tapFileReader = new TapFileReader();
        Map<String, Tap> tapOn = new HashMap<>();
        faresTable = tapFileReader.getFaresTable();
        taps.forEach(tap -> {
            if (tapOn.keySet().contains(tap.getPan())) {
                Tap startTap = tapOn.remove(tap.getPan());
                List<Fare> listofEndTaps = faresTable.get(startTap.getStopId().trim());
                if (tap.getBusId().contentEquals(startTap.getBusId()) && tap.getCompanyId().contentEquals(startTap.getCompanyId())) {
                    tripResponseList.add(new TripResponse(startTap, tap, listofEndTaps));
                } else {
                    tripResponseList.add(new TripResponse(startTap, listofEndTaps));
                    tapOn.put(tap.getPan(), tap);
                }
            } else {
                if (tap.getTapType().trim().contentEquals("ON")) {
                    tapOn.put(tap.getPan(), tap);
                } else {
                    tripResponseList.add(new TripResponse(tap, faresTable.get(tap.getStopId().trim())));
                }
            }
        });

        tapOn.forEach((source,faresList)-> tripResponseList.add(new TripResponse(faresList,faresTable.get(faresList.getStopId().trim()))));

        return tripResponseList;
    }
}
