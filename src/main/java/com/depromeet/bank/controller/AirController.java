package com.depromeet.bank.controller;

import com.depromeet.bank.service.impl.AirServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("air")
public class AirController {
    private static final Logger logger =  LoggerFactory.getLogger(AirController.class);

    @Autowired
    private AirServiceImpl airService;

//    @GetMapping("/{stationName}")
//    public ResponseEntity<String> getByStationnation(@PathVariable String stationName) throws UnsupportedEncodingException {
//        return airService.updateByStationName(stationName);
//    }
    @GetMapping("/{stationName}")
    public String getByStationnation(@PathVariable String stationName) throws UnsupportedEncodingException {
        return airService.updateByStationName(stationName);
    }

}
