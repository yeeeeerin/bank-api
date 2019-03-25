package com.depromeet.bank.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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


}
