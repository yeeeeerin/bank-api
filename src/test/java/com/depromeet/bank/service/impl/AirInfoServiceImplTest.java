package com.depromeet.bank.service.impl;

import com.depromeet.bank.adapter.openapi.OpenApiAdapter;
import com.depromeet.bank.adapter.openapi.OpenApiAdapterImpl;
import com.depromeet.bank.adapter.openapi.OpenApiStationName;
import com.depromeet.bank.domain.data.airinfo.AirInfo;
import com.depromeet.bank.repository.AirInfoRepository;
import com.depromeet.bank.service.AirInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AirInfoServiceImplTest {

    private static final String servicekey = "wcHq6FOmjnHYiJ0N5t1DEiEjYfE9njsCuqWfxGkGTpNPlMNaAh3K%2FFQ9lHjrECOSGVrTvnaI5kakoMjjdmD3Ug%3D%3D";
    @Autowired
    private AirInfoRepository airInfoRepository;
    @Autowired
    private AirInfoService airInfoService;
    @Autowired
    private OpenApiAdapter openApiAdapter;
    @Autowired
    private RestTemplate restTemplate;
    private AirInfo airInfo;

    @Before
    public void setUp() {
        openApiAdapter = new OpenApiAdapterImpl(restTemplate, servicekey);
        airInfoService = new AirInfoServiceImpl(openApiAdapter, airInfoRepository);
    }

    @Ignore
    @Test
    public void createAirInfo() {
        airInfo = airInfoService.createAirInfoByStationName(OpenApiStationName.SEOUL);
        assertThat(airInfo.getStationName(), is("종로구"));
    }
}
