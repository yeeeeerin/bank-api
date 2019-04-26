package com.depromeet.bank.helper;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class DepromeetMembers {

    private Map<String, Integer> memberMap;

    public Optional<Integer> getNumberByName(String name) {
        if (name == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(memberMap.get(name));
    }

    @PostConstruct
    public void initialize() {
        memberMap = new HashMap<>();
        memberMap.put("박수지", 5);
        memberMap.put("김한나", 4);
        memberMap.put("유현식", 3);
        memberMap.put("이윤지", 5);
        memberMap.put("권재원", 4);
        memberMap.put("김성진", 5);
        memberMap.put("이미영", 3);
        memberMap.put("오기환", 4);
        memberMap.put("최가람", 4);
        memberMap.put("조서완", 4);
        memberMap.put("이호용", 4);
        memberMap.put("전해성", 1);
        memberMap.put("정진용", 5);
        memberMap.put("김태성", 4);
        memberMap.put("정소연", 4);
        memberMap.put("최형석", 3);
        memberMap.put("최준영", 4);
        memberMap.put("이동석", 2);
        memberMap.put("김지운", 5);
        memberMap.put("조민지", 5);
        memberMap.put("김주현", 4);
        memberMap.put("여정승", 3);
        memberMap.put("이동건", 6);
        memberMap.put("윤찬명", 6);
        memberMap.put("최예진", 6);
        memberMap.put("최희재", 6);
        memberMap.put("최지훈", 6);
        memberMap.put("신종민", 6);
        memberMap.put("김유리", 6);
        memberMap.put("정현석", 6);
        memberMap.put("현지혜", 6);
        memberMap.put("이상은", 6);
        memberMap.put("박은비", 5);
        memberMap.put("공병국", 6);
        memberMap.put("김지원", 6);
        memberMap.put("김재연", 6);
        memberMap.put("유은비", 6);
        memberMap.put("정서경", 6);
        memberMap.put("마규석", 6);
        memberMap.put("곽규원", 6);
        memberMap.put("김지은", 6);
        memberMap.put("이예린", 6);
        memberMap.put("정승진", 6);
        memberMap.put("전용성", 6);
        memberMap.put("김신제", 6);
        memberMap.put("이유리", 6);
        memberMap.put("유승아", 6);
        memberMap.put("김승주", 6);
        memberMap.put("강인한", 6);
        memberMap.put("이지원", 6);
        memberMap.put("심문성", 6);
        memberMap.put("오유경", 6);
        memberMap.put("강진호", 6);
        memberMap.put("이종효", 6);
        memberMap.put("김동영", 3);
        memberMap.put("손혜진", 5);
        memberMap.put("백승화", 5);
        memberMap.put("은유진", 5);
        memberMap.put("마현지", 2);
        memberMap.put("노경래", 5);
        memberMap.put("이슬", 4);
        memberMap.put("최해영", 6);
        memberMap.put("배재현", 6);
        memberMap.put("이효진", 6);
        memberMap.put("이윤이", 6);
        memberMap.put("한지윤", 6);
        memberMap.put("이준혁", 6);
        memberMap.put("손지아", 6);
        memberMap.put("이상준", 6);
        memberMap.put("박진영", 6);
        memberMap.put("황희", 6);
        memberMap.put("유예지", 6);
        memberMap.put("이태송", 6);
        memberMap.put("박은지", 6);
        memberMap.put("김민경", 6);
        memberMap.put("서수민", 6);
        memberMap.put("김시내", 6);
        memberMap.put("김혜리", 6);
        memberMap.put("문승훈", 6);
        memberMap.put("허유림", 6);
        memberMap.put("김지영", 6);
    }
}
