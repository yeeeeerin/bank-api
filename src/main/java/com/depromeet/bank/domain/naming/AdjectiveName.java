package com.depromeet.bank.domain.naming;

public enum AdjectiveName {

    THRIFTY("검소한"),
    HUMBLE("겸손한"),
    GENEROUS("관대한"),
    POSITIVE("긍정적인"),
    OPTIMISTIC("낙천적인"),
    INTROVERT("내성적인"),
    KINDLY("착한"),
    FRIENDLY("다정한"),
    DETERMINED("단호한"),
    BOLD("대담한"),
    ATTRACTIVE("매력적인"),
    LIVELY("발랄한"),
    DILIGENT("부지런한"),
    AMIABLE("귀여운"),
    LOVELY("사랑스런"),
    CONSIDERATE("사려깊은"),
    HONEST("솔직한"),
    //부정적
    ARROGANT("거만한"),
    COWARDLY("겁많은"),
    CUNNING("교활한"),
    SIMPLE("단순한"),
    CANDID("솔직한"),
    CAPRICIOUS("변덕스런"),
    AUDACIOUS("뻔뻔한"),
    TALKATIVE("수다스런"),
    SHY("수줍어하는");


    private String name;

    AdjectiveName(String name) {
        this.name = name;
    }

    public static String getRandom() {
        return values()[(int) (Math.random() * values().length)].name;
    }

}
