package com.depromeet.bank.integration;

import com.depromeet.bank.git.CommitParser;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class GitCommitTest {

    private CommitParser commitParser;

    @Before
    public void setup() {
        commitParser = new CommitParser();
    }

    @Test
    public void 이상한_URL을_넘겨주었을경우_0을_반환() {
        Integer commit = commitParser.parse("https://github.com");
        assertThat(commit).isZero();
    }

}