package com.depromeet.bank.domain.rule;

import com.depromeet.bank.converter.ComparisonTypeConverter;
import com.depromeet.bank.converter.NotTypeConverter;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Condition {
    /**
     * 어떤 데이터를 어떻게 가공해야 할지 (일별 미세먼지, 주별 출석률 등)
     */
    @Column
    private DataType dataType;

    /**
     * 목표값
     */
    @Column
    private Long goal;

    /**
     * 데이터를 조회할 기간
     */
    @Embedded
    private Period period;

    /**
     * 비교 연산자
     */
    @Column
    @Convert(converter = ComparisonTypeConverter.class)
    private ComparisonType comparisonType;

    /**
     * 조건 결과를 판단할 때, 명제에 NOT 연산을 해야하는지
     */
    @Column
    @Convert(converter = NotTypeConverter.class)
    private NotType notType;

    public Condition(DataType dataType,
                     Long goal,
                     Period period,
                     ComparisonType comparisonType,
                     NotType notType) {
        this.dataType = dataType;
        this.goal = goal;
        this.period = period;
        this.comparisonType = comparisonType;
        this.notType = notType;
    }

    public static Condition of(DataType dataType,
                               Long goal,
                               Period period,
                               ComparisonType comparisonType) {
        return new Condition(dataType, goal, period, comparisonType, NotType.POSTIVE);
    }

    public boolean satisfied() {
        return false;
    }
}
