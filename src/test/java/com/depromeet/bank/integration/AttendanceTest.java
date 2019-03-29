package com.depromeet.bank.integration;

import com.depromeet.bank.domain.attendance.DepromeetSessionType;
import com.depromeet.bank.repository.AttendanceRepository;
import com.depromeet.bank.service.AttendanceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AttendanceTest {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Test
    public void 출석부_fetch_하면_Attendance_테이블의_row_가_생성되어야함() {
        // given
        DepromeetSessionType depromeetSessionType = DepromeetSessionType.MARCH_SIXTEENTH;
        assertThat(attendanceRepository.findAll().size()).isEqualTo(0);
        // when
        attendanceService.fetch(depromeetSessionType);
        // then
        assertThat(attendanceRepository.findAll().size()).isEqualTo(1);
    }
}

