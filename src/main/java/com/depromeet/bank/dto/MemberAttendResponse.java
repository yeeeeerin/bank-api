package com.depromeet.bank.dto;

import com.depromeet.bank.vo.VisitValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MemberAttendResponse {
    private Long point;
    private Long numberOfContinuousDays;
    private Long numberOfVisitor;
    private Boolean isVisitorBonus;

    private MemberAttendResponse(Long point,
                                 Long numberOfContinuousDays,
                                 Long numberOfVisitor,
                                 Boolean isVisitorBonus) {
        this.point = point;
        this.numberOfContinuousDays = numberOfContinuousDays;
        this.numberOfVisitor = numberOfVisitor;
        this.isVisitorBonus = isVisitorBonus;
    }

    public static MemberAttendResponse from(VisitValue visitValue) {
        return new MemberAttendResponse(
                visitValue.getPoint(),
                visitValue.getNumberOfContinuousDays(),
                visitValue.getNumberOfVisitor(),
                visitValue.isBonus()
        );
    }
}
