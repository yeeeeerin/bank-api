package com.depromeet.bank.domain.data.git;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Commit {

    @Id
    @GeneratedValue
    private Long id;

    private Integer commitCount;

    private LocalDateTime dataTime;
}
