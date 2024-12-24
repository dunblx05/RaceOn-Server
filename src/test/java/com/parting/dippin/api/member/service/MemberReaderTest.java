package com.parting.dippin.api.member.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.parting.dippin.common.DatabaseTest;
import com.parting.dippin.domain.member.service.MemberReader;
import com.parting.dippin.entity.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DatabaseTest
class MemberReaderTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberReader memberReader;

    @DisplayName("멤버코드 조회")
    @Test
    void getMemberCode() {
        // given
        int memberId = 1;
        String memberCode = "A12345";

        // when
        String result = memberReader.getMemberCode(memberId);

        // then
        assertThat(memberCode).isEqualTo(result);
    }
}
