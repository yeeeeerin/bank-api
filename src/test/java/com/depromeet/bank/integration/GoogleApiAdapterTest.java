package com.depromeet.bank.integration;

import com.depromeet.bank.adapter.google.CellAddress;
import com.depromeet.bank.adapter.google.GoogleApiAdapter;
import com.depromeet.bank.adapter.google.GoogleApiResponse;
import com.depromeet.bank.adapter.google.Range;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoogleApiAdapterTest {
    @Autowired
    private GoogleApiAdapter googleApiAdapter;

    @Test
    public void 구글_API_요청__디프만_시트가_존재하는지() {
        // given
        String sheetId = "1KGnz_r44ntfQ3d-Q7977oLscdj1CtOtax5eR1R7D1_I";
        Range range = Range.of(
                CellAddress.of("D", 2),
                CellAddress.of("D", 85)
        );
        // when
        GoogleApiResponse googleApiResponse = googleApiAdapter.findBySheetIdAndRange(sheetId, range).orElse(null);
        // then
        assertThat(googleApiResponse).isNotNull();
        assertThat(googleApiResponse.getSheets().size()).isEqualTo(1);
    }
}
