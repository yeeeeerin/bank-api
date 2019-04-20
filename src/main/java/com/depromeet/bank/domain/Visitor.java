package com.depromeet.bank.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Visitor {
    @Id
    @GeneratedValue
    @Column(name = "visitor_id")
    private Long id;
    private Long memberId;
    private Integer visitorOrder;
    private Long point;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private Visitor(Long memberId, Integer visitorOrder, Long point) {
        this.memberId = memberId;
        this.visitorOrder = visitorOrder;
        this.point = point;
    }

    public static Visitor of(Long memberId, Integer visitorOrder, Long point) {
        return new Visitor(memberId, visitorOrder, point);
    }
}
