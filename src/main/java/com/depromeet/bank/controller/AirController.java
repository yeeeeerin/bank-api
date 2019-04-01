package com.depromeet.bank.controller;

import com.depromeet.bank.service.impl.AirServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("air")
public class AirController {
    private static final Logger logger =  LoggerFactory.getLogger(AirController.class);

    @Autowired
    private AirServiceImpl airService;

    @GetMapping("/{stationName}")
    public ResponseEntity<String> getByStationnation(@PathVariable String stationName) throws UnsupportedEncodingException {
        return airService.updateByStationName(stationName);
    }
}
