package com.depromeet.bank.adaptor.google;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@ToString
public class GoogleApiResponse {
    private String spreadsheetId;
    private Map<String, Object> properties;
    private List<Sheet> sheets;
    private String spreadsheetUrl;

    @Getter
    @ToString
    public static class Sheet {
        private Map<String, Object> properties;
        private List<SheetData> data;
    }

    @Getter
    @ToString
    public static class SheetData {
        private Integer startRow;
        private List<RowValues> rowData;
        private List<Object> rowMetaData;
        private List<Object> columnMetaData;
    }

    @Getter
    @ToString
    public static class RowValues {
        private List<RowValue> values;
    }

    @Getter
    @ToString
    public static class RowValue {
        private Map<String, Object> userEnteredValue;
        private Map<String, Object> effectiveValue;
        private String formattedValue;
        private Map<String, Object> userEnteredFormat;
        private Map<String, Object> effectiveFormat;
    }
}
