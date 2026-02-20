package com.example.study_first.dto;

import com.example.study_first.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberRequestDto {
    private String nickname;
    private Long attackPower;      // 공격력
    private Double damage;         // 데미지
    private Double damageAmp;      // 최종뎀
    private Double stDamage;       // 스탯 대미지
    private Double attackSpeed;    // 공속
    private Double minVal;         // 최소댐
    private Double maxVal;         // 최대댐
    private Double normalMonDamage; // 일몹뎀
    private Double bossDamage;     // 보공
    private Double critRate;       // 크리 확률
    private Double critDamage;     // 크리 데미지


    public Member toEntity() {
        return Member.builder()
                .nickname(this.nickname)
                .attackPower(this.attackPower)
                .damage(this.damage)
                .damageAmp(this.damageAmp)
                .stDamage(this.stDamage)
                .attackSpeed(this.attackSpeed)
                .minVal(this.minVal)
                .maxVal(this.maxVal)
                .normalMonDamage(this.normalMonDamage)
                .bossDamage(this.bossDamage)
                .critRate(this.critRate)
                .critDamage(this.critDamage)
                .build();
    }
}
