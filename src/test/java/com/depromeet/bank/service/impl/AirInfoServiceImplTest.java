package com.depromeet.bank.service.impl;

import com.depromeet.bank.adaptor.openapi.OpenApiAdaptor;
import com.depromeet.bank.domain.AirInfo;
import com.depromeet.bank.repository.AirInfoRepository;
import com.depromeet.bank.service.AirInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AirInfoServiceImplTest {

    @Mock
    private AirInfoRepository airInfoRepository;

    @Mock
    private AirInfoService airInfoService;

    @Autowired
    private OpenApiAdaptor openApiAdaptor;

    @Before
    public void setUp() {
        airInfoService = new AirInfoServiceImpl(openApiAdaptor, airInfoRepository);
    }

    @Test
    public void airinfo테스트() throws Exception {
        AirInfo airInfo = airInfoService.createAirInfoByStationName("광교동");
        airInfoService.createAirInfoByStationName("종로구");
        assertThat(airInfoRepository.getOne(1L).getStationName(), is("광교동"));
    }
}
