package com.depromeet.bank.domain.rule;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Optional;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@EqualsAndHashCode
public class Reward {
    private static final double RATE_DEFAULT = 0.0;
    private static final Reward DEFAULT = new Reward(RATE_DEFAULT);

    /**
     * 투자금을 기준으로 보상해주어야 하는 비율. (단위 미정)
     */
    @Column
    private Double rate;

    private Reward(Double rate) {
        this.rate = Optional.ofNullable(rate).orElse(RATE_DEFAULT);
    }

    public static Reward from(Double rate) {
        return new Reward(rate);
    }

    public static Reward getDefault() {
        return DEFAULT;
    }
}
