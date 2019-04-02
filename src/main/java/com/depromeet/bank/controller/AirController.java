package com.depromeet.bank.controller;

import com.depromeet.bank.service.impl.AirPollutionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api")
public class AirController {

    @Autowired
    private AirPollutionServiceImpl airService;

    @GetMapping("/{stationName}")
    public String getByStationnation(@PathVariable String stationName) throws UnsupportedEncodingException {
        return airService.updateByStationName(stationName);
    }

}
