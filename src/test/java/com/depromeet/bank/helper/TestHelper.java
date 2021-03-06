package com.depromeet.bank.helper;

import com.depromeet.bank.domain.Member;
import com.depromeet.bank.domain.Transaction;
import com.depromeet.bank.domain.TransactionClassify;
import com.depromeet.bank.domain.account.Account;
import com.depromeet.bank.domain.account.AccountType;
import com.depromeet.bank.domain.data.attendance.Attendance;
import com.depromeet.bank.domain.rule.ComparisonType;
import com.depromeet.bank.domain.rule.DataType;
import com.depromeet.bank.domain.rule.NotType;
import com.depromeet.bank.dto.AdjustmentRuleRequest;
import com.depromeet.bank.dto.InstrumentRequest;
import com.depromeet.bank.dto.TransactionRequest;
import com.depromeet.bank.repository.AccountRepository;
import com.depromeet.bank.repository.MemberRepository;
import com.depromeet.bank.vo.SocialMemberVo;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.when;

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

    public static Account createAccount(String name, Long balance, Double rate, AccountType accountType, Member member) {

        Account account = new Account();
        account.setName(name);
        account.setBalance(balance);
        account.setRate(rate);
        account.setAccountType(accountType);
        account.setMember(member);

        return account;
    }

    public static void createSystemMember(MemberRepository memberRepository,
                                          AccountRepository accountRepository) {
        // 시스템 계정 생성
        Member systemMember = new Member();
        systemMember.setId(Member.SYSTEM_MEMBER_ID);
        systemMember.setName("SYSTEM");
        systemMember.setSocialId(0L);
        systemMember.setProfileHref("");
        when(memberRepository.findById(Member.SYSTEM_MEMBER_ID)).thenReturn(Optional.of(systemMember));

        // 시스템 계좌 생성
        Account systemAccount = new Account();
        systemAccount.setId(Account.SYSTEM_ACCOUNT_ID);
        systemAccount.setName("SYSTEM_ACCOUNT");
        systemAccount.setBalance(10000000000000L);
        systemAccount.setRate(0.0);
        systemAccount.setMember(systemMember);
        when(accountRepository.findById(Member.SYSTEM_MEMBER_ID)).thenReturn(Optional.of(systemAccount));
        when(accountRepository.findByMemberIdAndAccountType(Member.SYSTEM_MEMBER_ID, AccountType.SYSTEM))
                .thenReturn(Collections.singletonList(systemAccount));
    }

    public static InstrumentRequest createInstrumentRequest(
            String name,
            String description,
            LocalDateTime expiredAt,
            LocalDateTime toBeSettledAt,
            List<AdjustmentRuleRequest> rules) {
        InstrumentRequest instrumentRequest = new InstrumentRequest();
        ReflectionTestUtils.setField(instrumentRequest, "name", name);
        ReflectionTestUtils.setField(instrumentRequest, "description", description);
        ReflectionTestUtils.setField(instrumentRequest, "expiredAt", expiredAt);
        ReflectionTestUtils.setField(instrumentRequest, "toBeSettledAt", toBeSettledAt);
        ReflectionTestUtils.setField(instrumentRequest, "rules", rules);
        return instrumentRequest;
    }

    public static TransactionRequest createTransactionRequest(Long fromAccountId,
                                                              Long toAccountId,
                                                              Long amount) throws Exception {

        Constructor<TransactionRequest> constructor = TransactionRequest.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        TransactionRequest transactionRequest = constructor.newInstance();

        ReflectionTestUtils.setField(transactionRequest, "fromAccountId", fromAccountId);
        ReflectionTestUtils.setField(transactionRequest, "toAccountId", toAccountId);
        ReflectionTestUtils.setField(transactionRequest, "amount", amount);
        return transactionRequest;
    }

    public static Transaction createTransaction(Long amount,
                                                TransactionClassify transactionClassify,
                                                Account account, Long balance) throws Exception {
        Constructor<Transaction> constructor = Transaction.class.getDeclaredConstructor(
                Long.class,
                TransactionClassify.class,
                Account.class,
                String.class,
                Long.class);
        constructor.setAccessible(true);
        return constructor.newInstance(amount, transactionClassify, account, "guid", balance);
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

    public static AdjustmentRuleRequest createAdjustmentRuleRequest(
            DataType dataType,
            ComparisonType comparisonType,
            NotType notType,
            Long goal,
            Long criterion,
            LocalDateTime fromAt,
            LocalDateTime toAt,
            Double rate) {
        AdjustmentRuleRequest adjustmentRuleRequest = new AdjustmentRuleRequest();
        ReflectionTestUtils.setField(adjustmentRuleRequest, "dataType", dataType);
        ReflectionTestUtils.setField(adjustmentRuleRequest, "comparisonType", comparisonType);
        ReflectionTestUtils.setField(adjustmentRuleRequest, "notType", notType);
        ReflectionTestUtils.setField(adjustmentRuleRequest, "goal", goal);
        ReflectionTestUtils.setField(adjustmentRuleRequest, "criterion", criterion);
        ReflectionTestUtils.setField(adjustmentRuleRequest, "fromAt", fromAt);
        ReflectionTestUtils.setField(adjustmentRuleRequest, "toAt", toAt);
        ReflectionTestUtils.setField(adjustmentRuleRequest, "rate", rate);
        return adjustmentRuleRequest;
    }
}
