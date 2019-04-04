package com.depromeet.bank.adaptor.openapi;

import com.depromeet.bank.exception.AirPollutionNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class ResponseParser {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    public JSONObject xmlToJson(String response) {
        return XML.toJSONObject(response);
    }

    public Optional<AirPollution> jsonToAirPollution(JSONObject json) throws IOException {
        JSONObject object = json.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
        return Optional.of(objectMapper().readValue(object.toString(), AirPollution.class));
    }
}
