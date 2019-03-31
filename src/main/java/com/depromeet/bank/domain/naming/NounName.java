package com.depromeet.bank.domain.naming;

public enum NounName {
    DONKEY("당나귀"),
    TIGER("호랑이"),
    LION("사자"),
    CHEETAH("치타"),
    HYENA("하이에나"),
    PUPPY("강아지"),
    CROW("까마귀"),
    PHOENIX("불사조"),
    FLAMINGO("플라밍고"),
    PARROT("앵무새"),
    EAGLE("독수리"),
    CHICKEN("병아리"),
    SKYLARK("종달새"),
    CUCKOO("뻐꾸기"),
    SWAN("백조"),
    CRICKET("귀뚜라미"),
    GIRAFFE("기린"),
    ZEBRA("얼룩말"),
    RHINOCEROS("코뿔소"),
    HIPPOPOTAMUS("하마"),
    RABBIT("토끼"),
    SKUNK("스컹크"),
    SQUIRREL("다람쥐"),
    CAMEL("낙타"),
    CAT("고양이"),
    ELEPHANT("코끼리"),
    DEER("사슴"),
    MONKEY("원숭이"),
    HEDGEHOG("고슴도치"),
    WOLF("늑대"),
    MOLE("두더지");


    String name;

    NounName(String name) {
        this.name = name;
    }

    public static String getRandom() {
        return values()[(int) (Math.random() * values().length)].name;
    }
}
