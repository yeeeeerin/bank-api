package com.depromeet.bank.domain.account;

import com.depromeet.bank.converter.AccountTypeConverter;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Account {

    public static final Long SYSTEM_ACCOUNT_ID = 0L;

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    //형용사 + 명사로 랜덤하게
    private String name;

    private String accountNumber;

    private Long balance;    //잔고

    private Double rate;     //이율

    @Convert(converter = AccountTypeConverter.class)
    private AccountType accountType;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;


}
