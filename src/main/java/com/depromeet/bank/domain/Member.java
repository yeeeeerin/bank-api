package com.depromeet.bank.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private Long socialId;

    private String name;

    private String profileHref;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();


}
