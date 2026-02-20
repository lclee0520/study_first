package com.example.study_first.service;

import com.example.study_first.domain.Member;
import com.example.study_first.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CalculatorServiceTest {

    @Autowired CalculatorService calculatorService;
    @Autowired MemberRepository memberRepository;

    @Test
    @DisplayName("전투력 계산 공식이 정확하게 돌아가야 한다")
    void combatPowerTest() {
        // 1. 가상 유저 생성 및 스탯 설정
        Member member = new Member();
        member.setNickname("테스터");
        member.setAttackPower(100);    // 공격력 100
        member.setDamageAmp(1.2);      // 증폭 1.2
        member.setSkillCoef(2.0);      // 계수 2.0
        member.setMinVal(10);          // 최소 10
        member.setMaxVal(20);          // 최대 20 (평균 15)
        member.setTotalDamage(0.5);    // 합뎀 50%
        member.setNormalMonDamage(0.5); // 일몹뎀 50%

        Member savedMember = memberRepository.save(member);

        // 2. 계산 수행 (직접 계산기로 두드린 값과 비교)
        // 기본데미지 = 100 * 1.2 * 2.0 * 15 = 3600
        // 공속적용 = 3600 * 1.5 = 5400
        // 사냥전투력 = 5400 * (1 + 0.5 + 0.5) = 10800
        long huntingCP = calculatorService.getCurrentHuntingCP(savedMember.getId());

        // 3. 검증
        System.out.println("계산된 사냥 전투력: " + huntingCP);
        assertThat(huntingCP).isEqualTo(10800L);
    }
}