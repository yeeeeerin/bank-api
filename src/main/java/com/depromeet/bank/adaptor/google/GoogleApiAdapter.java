package com.depromeet.bank.adaptor.google;

import java.util.Optional;

public interface GoogleApiAdapter {
    Optional<GoogleApiResponse> findBySheetIdAndRange(String sheetId, Range range);
}
