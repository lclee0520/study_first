package com.example.study_first.controller;

import com.example.study_first.domain.Member;
import com.example.study_first.dto.ItemRequestDto;
import com.example.study_first.dto.MemberRequestDto;
import com.example.study_first.service.CalculatorService;
import com.example.study_first.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.study_first.repository.MemberRepository;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final CalculatorService calculatorService;
    private final MemberRepository memberRepository; //자바에 new 근대 스프링에서는 이렇게
    private final MemberService memberService;

    @PostMapping
    public Long createMember(@RequestBody MemberRequestDto dto) {
        Member member = dto.toEntity();
        return memberRepository.save(member).getId(); //저장하고 아이디 출력
    }

    // 사냥 전투력
    @GetMapping("/{id}/hunting-cp")
    public double getHuntingCP(@PathVariable Long id) {
        return calculatorService.getCurrentCP(id,true);
    }

    // 보스 전투력
    @GetMapping("/{id}/boss-cp")
    public double getBossCP(@PathVariable Long id) {
        return calculatorService.getCurrentCP(id,false);
    }

    // 사냥 효율 비교
    @PostMapping("/{id}/compareHunt")
    public String compareItemsHunt(@PathVariable Long id, @RequestBody List<ItemRequestDto> items) {
        if (items.size() < 2) return "비교를 위해 최소 2개의 아이템 정보가 필요합니다.";
        return calculatorService.compareItems(id, items.get(0), items.get(1), true);
    }

    // 보스 효율 비교
    @PostMapping("/{id}/compareBoss")
    public String compareItemsBoss(@PathVariable Long id, @RequestBody List<ItemRequestDto> items) {
        if (items.size() < 2) return "비교를 위해 최소 2개의 아이템 정보가 필요합니다.";
        return calculatorService.compareItems(id, items.get(0), items.get(1), false);
    }

    // 3. 정밀 시뮬레이션
    @PostMapping("/{id}/simulate")
    public String simulateItem(@PathVariable Long id, @RequestBody ItemRequestDto item) {
        double huntEff = calculatorService.calculateEfficiency(id, item, true);  // 사냥
        double bossEff = calculatorService.calculateEfficiency(id, item, false); // 보스

        StringBuilder result = new StringBuilder("--- 정밀 시뮬레이션 ---\n");
        double threshold = 1e-7;

        // 사냥 결과
        if (Math.abs(huntEff) > threshold) {
            String dir = (huntEff > 0) ? "상승" : "감소";
            result.append(String.format("[사냥] 전투력 %.6f%% %s\n", Math.abs(huntEff), dir));
        } else {
            result.append("[사냥] 전투력 변동이 거의 없습니다 (0.000000% 미만)\n");
        }

        // 보스 결과
        if (Math.abs(bossEff) > threshold) {
            String dir = (bossEff > 0) ? "상승" : "감소";
            result.append(String.format("[보스] 전투력 %.6f%% %s\n", Math.abs(bossEff), dir));
        } else {
            result.append("[보스] 전투력 변동이 거의 없습니다 (0.000000% 미만)\n");
        }

        // 종합 분석
        if (Math.abs(huntEff) > threshold || Math.abs(bossEff) > threshold) {
            result.append("\n[종합 분석]\n");
            if (huntEff > 0 && bossEff > 0) {
                result.append("▶ 축하합니다! 모든 상황에서 강해지는 아이템입니다.");
            } else if (huntEff > 0) {
                result.append("▶ 이 아이템은 '필드 사냥' 전용입니다. 보스전엔 비추천해요.");
            } else if (bossEff > 0) {
                result.append("▶ 이 아이템은 '보스 레이드' 전용입니다. 사냥터에선 효율이 낮아요.");
            } else {
                result.append("▶ 전체적으로 성능이 하락합니다. 장착하지 않는 것을 권장합니다.");
            }
        }

        return result.toString();
    }

    //스텟 업데이트
    @PatchMapping("/{id}/update")
    public String updateMember(@PathVariable Long id, @RequestBody MemberRequestDto requestDto){
        memberService.updateMember(id, requestDto);
        return "스텟 수정 완료";
    }

    //id 삭제
    @DeleteMapping("/{id}/delete")
    public String deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return "회원 삭제 완료 (ID: " + id + ")";
    }
}