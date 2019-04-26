package com.depromeet.bank.adapter.google;

import java.util.List;
import java.util.Optional;

public interface GoogleApiAdapter {
    Optional<GoogleApiResponse> findBySheetIdAndRange(String sheetId, Range range);

    List<String> parseAttendanceSigns(GoogleApiResponse googleApiResponse);
}
