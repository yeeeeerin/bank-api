package com.depromeet.bank.domain.data.git;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Commit {

    @Id
    @GeneratedValue
    @Column(name = "commit_id")
    private Long id;

    private Integer commitCount;

    @CreatedDate
    private LocalDateTime createAt;

}
