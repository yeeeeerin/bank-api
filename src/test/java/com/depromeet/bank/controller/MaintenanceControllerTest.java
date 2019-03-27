package com.depromeet.bank.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MaintenanceController.class)
@AutoConfigureMockMvc
public class MaintenanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void healthCheck() throws Exception {
        mockMvc.perform(get("/monitor/l7check"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("OK"));
    }
}