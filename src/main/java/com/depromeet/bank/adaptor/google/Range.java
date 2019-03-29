package com.depromeet.bank.adaptor.google;

import lombok.Value;

@Value(staticConstructor = "of")
public class Range {
    private final CellAddress from;
    private final CellAddress to;

    public String format() {
        return from.getColumn() + from.getRow() + ":" + to.getColumn() + to.getRow();
    }
}
