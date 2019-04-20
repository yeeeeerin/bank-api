package com.depromeet.bank.integration;

import com.depromeet.bank.adapter.google.GoogleApiAdapter;
import com.depromeet.bank.adapter.google.GoogleApiResponse;
import com.depromeet.bank.adapter.google.Range;
import com.depromeet.bank.domain.account.JwtFactory;
import com.depromeet.bank.domain.data.attendance.Attendance;
import com.depromeet.bank.domain.data.attendance.DepromeetSessionType;
import com.depromeet.bank.helper.TestHelper;
import com.depromeet.bank.repository.AttendanceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AttendanceSynchronizationTest {

    private static final String AUTHORIZATION_TOKEN = "Bearer authorizationToken";
    private static final Long MEMBER_ID = 1L;

    @MockBean
    private JwtFactory jwtFactory;
    @MockBean
    private GoogleApiAdapter googleApiAdapter;
    @SpyBean
    private AttendanceRepository attendanceRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        when(jwtFactory.getMemberId(AUTHORIZATION_TOKEN)).thenReturn(Optional.of(MEMBER_ID));
    }

    @Test
    public void test() throws Exception {
        // given
        final Long attendanceId = 1L;
        Attendance attendance = TestHelper.createAttendance(
                attendanceId,
                0,
                0,
                DepromeetSessionType.APRIL_SIXTH.name(),
                LocalDateTime.now()
        );
        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(attendance));
        when(googleApiAdapter.findBySheetIdAndRange(anyString(), any(Range.class)))
                .thenReturn(Optional.of(new GoogleApiResponse()));
        when(googleApiAdapter.parseAttendanceSigns(any(GoogleApiResponse.class)))
                .thenReturn(Arrays.asList("O", "X", "X"));
        // when
        mockMvc.perform(post("/api/attendances/{attendanceId}/sync", attendanceId)
                .header("authorization", AUTHORIZATION_TOKEN))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.id").value(attendanceId))
                .andExpect(jsonPath("$.response.numberOfAttendee").value(1))
                .andExpect(jsonPath("$.response.numberOfAbsentee").value(2))
                .andExpect(jsonPath("$.response.sessionName").value(DepromeetSessionType.APRIL_SIXTH.name()));
    }
}
