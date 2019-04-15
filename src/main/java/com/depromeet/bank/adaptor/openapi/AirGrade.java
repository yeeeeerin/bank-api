package com.depromeet.bank.adaptor.openapi;

public enum AirGrade {
    FIRST(1), SECOND(2), THIRD(3), FOUTH(4), FIFTH(5), SIXTH(6), SEVENTH(7), EIGHTH(8);

    private Integer grade;

    AirGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getGrade() {
        return grade;
    }
}
