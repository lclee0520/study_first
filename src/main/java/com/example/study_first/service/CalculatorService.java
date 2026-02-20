package com.example.study_first.service;

import com.example.study_first.domain.Member;
import com.example.study_first.dto.ItemRequestDto;
import com.example.study_first.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CalculatorService {

    private final MemberRepository memberRepository;

    // 현재 전투력 조회
    public double getCurrentCP(Long id, boolean isHunting) {
        Member member = findMember(id);
        return isHunting ? member.calculateHuntingCP() : member.calculateBossCP();
    }

    // 이이템 비교
    public String compareItems(Long id, ItemRequestDto itemA, ItemRequestDto itemB, boolean isHunting) {
        Member member = findMember(id);
        double effA = runSimulation(member, itemA, isHunting);
        double effB = runSimulation(member, itemB, isHunting);

        String bestItem = (effA >= effB) ? "아이템 A" : "아이템 B";
        String mode = isHunting ? "사냥" : "보스";

        return String.format("[%s 기준] %s가 더 효율적입니다! (A: %.6f%%, B: %.6f%% 상승)",
                mode, bestItem, effA, effB);
    }

    //전투력 계산
    public double calculateEfficiency(Long id, ItemRequestDto item, boolean isHunting) {
        Member member = findMember(id);
        return runSimulation(member, item, isHunting);
    }

    // 유저 찾기
    private Member findMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다. ID: " + id));
    }

    //전투력 변화
    private double runSimulation(Member member, ItemRequestDto item, boolean isHunting) {
        //기준 전투력 측정
        double beforeCP = isHunting ? member.calculateHuntingCP() : member.calculateBossCP();
        if (beforeCP == 0) return 0;

        //세 전투력 측정
        Member simMember = item.applyTo(member);
        double afterCP = isHunting ? simMember.calculateHuntingCP() : simMember.calculateBossCP();

        return ((afterCP - beforeCP) / beforeCP) * 100.0;
    }
}