package com.depromeet.bank.service;

import com.depromeet.bank.vo.GraphValue;

import java.util.List;

public interface GraphService {
    List<GraphValue> getDepromeetSessionData();

    List<GraphValue> getAirPollutionData();

    List<GraphValue> getKakaotalkData();
}
