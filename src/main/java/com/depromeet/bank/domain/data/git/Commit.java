package com.depromeet.bank.domain.data.git;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Commit {

    @Id
    @GeneratedValue
    private Long id;

    private Integer commitCount;

    @CreatedDate
    private LocalDateTime dataTime;
}
