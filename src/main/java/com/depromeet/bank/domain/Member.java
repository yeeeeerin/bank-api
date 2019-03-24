package com.depromeet.bank.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    private Long socialId;

    private String name;

    private String profileHref;


}
