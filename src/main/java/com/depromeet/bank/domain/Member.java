package com.depromeet.bank.domain;


import com.depromeet.bank.domain.account.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Member {
    public static final Long SYSTEM_MEMBER_ID = 0L;

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private Long socialId;

    private String name;

    private Integer cardinalNumber;

    private String profileHref;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();


}
