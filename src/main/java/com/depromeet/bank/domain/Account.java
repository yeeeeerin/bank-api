package com.depromeet.bank.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer balance;    //잔고

    private Double rate;        //이율

    @CreatedDate
    private LocalDateTime createDateTime;

    //todo 상품이 어떻게 만들어지냐에 따라 다를것 같아서 보류
    //private LocalDateTime expireDateTime;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private Set<Transaction> transactions = new HashSet<>();


}
