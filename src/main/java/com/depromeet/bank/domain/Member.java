package com.depromeet.bank.domain;


import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.vo.MemberValue;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
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

    public Member update(MemberValue memberValue) {
        Assert.notNull(memberValue, "'memberValue' must not be null");

        String name = memberValue.getName();
        if (name != null) {
            this.name = name;
        }
        Integer cardinalNumber = memberValue.getCardinalNumber();
        if (cardinalNumber != null) {
            this.cardinalNumber = cardinalNumber;
        }
        String profileImageUrl = memberValue.getProfileImageUrl();
        if (profileImageUrl != null) {
            this.profileHref = profileImageUrl;
        }
        return this;
    }
}
