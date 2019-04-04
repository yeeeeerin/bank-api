package com.depromeet.bank.repository;

import com.depromeet.bank.domain.attendance.DepromeetSessionType;
import com.depromeet.bank.domain.rule.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class AdjustmentRuleRepositoryTest {

    @Autowired
    private AdjustmentRuleRepository adjustmentRuleRepository;

    @Test
    public void test() {
        Condition condition = createCondition();
        Reward reward = createReward();
        AdjustmentRule adjustmentRule = AdjustmentRule.of(condition, reward);
        adjustmentRuleRepository.save(adjustmentRule);

        assertThat(adjustmentRuleRepository.findAll().size()).isEqualTo(1);
    }

    private Condition createCondition() {
        DataType dateType = DataType.NUMBER_OF_ATTENDEE;
        Long goal = 50L;
        Period period = Period.from(DepromeetSessionType.MARCH_TWENTY_THIRD);
        ComparisonType comparisonType = ComparisonType.GREATER_THAN_OR_EQUAL_TO;
        return Condition.of(dateType, goal, period, comparisonType);
    }

    private Reward createReward() {
        return Reward.getDefault();
    }
}