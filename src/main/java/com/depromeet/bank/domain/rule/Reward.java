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
    private static Reward DEFAULT = new Reward(0L);

    /**
     * 퍼센트로 나타낸 값 x 100, 소수점 절사
     */
    @Column
    private Long rate;

    private Reward(Long rate) {
        this.rate = Optional.ofNullable(rate).orElse(0L);
    }

    public static Reward from(Long rate) {
        return new Reward(rate);
    }

    public static Reward getDefault() {
        return DEFAULT;
    }
}
