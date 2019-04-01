package com.depromeet.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MaintenanceController {
    @GetMapping("/monitor/l7check")
    public String check() {
        return "OK";
    }
}
