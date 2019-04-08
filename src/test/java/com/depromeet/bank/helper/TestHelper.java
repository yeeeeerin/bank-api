package com.depromeet.bank.helper;


import com.depromeet.bank.domain.Account;
import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.domain.TransactionClassify;
import com.depromeet.bank.domain.attendance.Attendance;
import com.depromeet.bank.dto.InstrumentRequest;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.vo.SocialMemberVo;
import com.depromeet.bank.vo.TransactionValue;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TestHelper {
    // noninstantiable utility class
    private TestHelper() {
    }

    public static SocialMemberVo createSocialMemberVo(Long id, String nickname, String imageUrl) {
        SocialMemberVo socialMemberVo = new SocialMemberVo();
        ReflectionTestUtils.setField(socialMemberVo, "id", id);
        Map<String, String> propertyMap = new HashMap<>();
        propertyMap.put("nickname", nickname);
        propertyMap.put("profile_image", imageUrl);
        ReflectionTestUtils.setField(socialMemberVo, "properties", propertyMap);
        return socialMemberVo;
    }

    public static Member createMember(Long socialId, String name, String profileHref) {
        Member member = new Member();
        member.setSocialId(socialId);
        member.setName(name);
        member.setProfileHref(profileHref);
        return member;
    }

    public static Account createAccount(String name, Long balance, Double rate, Member member) {

        Account account = new Account();
        account.setName(name);
        account.setBalance(balance);
        account.setRate(rate);
        account.setMember(member);

        return account;

    }

    public static InstrumentRequest createInstrumentRequest(String name, String description, LocalDateTime expiredAt) {
        InstrumentRequest instrumentRequest = new InstrumentRequest();
        ReflectionTestUtils.setField(instrumentRequest, "name", name);
        ReflectionTestUtils.setField(instrumentRequest, "description", description);
        ReflectionTestUtils.setField(instrumentRequest, "expiredAt", expiredAt);
        return instrumentRequest;
    }

    public static TransactionRequest createTransactionRequest(Long fromAccountId, Long toAccountId, Long amount) {
        TransactionRequest transactionRequest = new TransactionRequest();
        ReflectionTestUtils.setField(transactionRequest, "fromAccountId", fromAccountId);
        ReflectionTestUtils.setField(transactionRequest, "toAccountId", toAccountId);
        ReflectionTestUtils.setField(transactionRequest, "amount", amount);
        return transactionRequest;
    }

    private static TransactionValue transactionValue(Long amount, TransactionClassify transactionClassify, Account account) {
        return TransactionValue.of(amount, transactionClassify, account, UUID.randomUUID().toString());
    }

    public static Transaction createTransaction(Long amount, TransactionClassify transactionClassify, Account account) {
        TransactionValue transactionValue = transactionValue(amount, transactionClassify, account);
        return Transaction.from(transactionValue);
    }

    public static Attendance createAttendance(Long id,
                                              Integer numberOfAttendee,
                                              Integer numberOfAbsentee,
                                              String sessionName,
                                              LocalDateTime createdAt)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Attendance> constructor = Attendance.class.getDeclaredConstructor(
                Long.class, Integer.class, Integer.class, String.class, LocalDateTime.class
        );
        constructor.setAccessible(true);
        return constructor.newInstance(id, numberOfAttendee, numberOfAbsentee, sessionName, createdAt);
    }
}
