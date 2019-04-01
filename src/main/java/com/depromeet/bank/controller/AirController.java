package com.depromeet.bank.controller;

import com.depromeet.bank.service.AirSerivce;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.ws.Response;

@RestController
@RequestMapping("air")
public class AirController {
    private static final Logger logger =  LoggerFactory.getLogger(AirController.class);

    @Autowired
    private AirSerivce airSerivce;

    @GetMapping("/{stationName}")
    public ResponseEntity<String> getByStationnation(@PathVariable String stationName) {
        logger.info("controller parameter : {}", stationName);
        return airSerivce.updateByStationname(stationName);
    }
}
