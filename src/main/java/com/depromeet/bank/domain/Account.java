package com.depromeet.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Long balance;    //잔고

    private Double rate;     //이율

    @CreatedDate
    private LocalDateTime createdAt;

    //todo 상품이 어떻게 만들어지냐에 따라 다를것 같아서 보류
    //private LocalDateTime expireAt;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    @Builder.Default
    private Set<Transaction> transactions = new HashSet<>();


}
