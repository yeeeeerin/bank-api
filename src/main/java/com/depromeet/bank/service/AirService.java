package com.depromeet.bank.service;

import org.springframework.http.ResponseEntity;

import javax.xml.ws.Response;
import java.io.UnsupportedEncodingException;

public interface AirService {
    ResponseEntity<String> updateByStationName(String stationName) throws UnsupportedEncodingException;

}
