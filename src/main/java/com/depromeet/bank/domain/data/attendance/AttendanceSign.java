package com.depromeet.bank.domain.data.attendance;

import lombok.Getter;

@Getter
public enum AttendanceSign {
    PRESENT("O"),
    ABSENT("X");

    private String sign;

    AttendanceSign(String sign) {
        this.sign = sign;
    }

    public boolean equalsIgnoreCase(String anotherString) {
        return sign.equalsIgnoreCase(anotherString);
    }
}

