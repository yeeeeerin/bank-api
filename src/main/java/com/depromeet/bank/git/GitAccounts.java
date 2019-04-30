package com.depromeet.bank.git;

import lombok.Getter;

@Getter
public enum GitAccounts {
    YEIRN("https://github.com/yeeeeerin"),
    HAESUNG("https://github.com/junhaesung"),
    MUNSEONG("https://github.com/Puterism"),
    GIRIN("https://github.com/girin-dev"),
    JAEYEON("https://github.com/jaeyeon93");

    private String url;

    GitAccounts(String url) {
        this.url = url;
    }


}
