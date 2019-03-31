package com.depromeet.bank.adaptor.google;

import lombok.Value;

@Value(staticConstructor = "of")
public class CellAddress {
    private final String column;
    private final Integer row;
}
