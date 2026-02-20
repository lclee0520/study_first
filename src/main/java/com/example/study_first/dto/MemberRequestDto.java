package com.example.study_first.dto;

import com.example.study_first.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberRequestDto {
    private String nickname;
    private long attackPower;      // 공격력
    private double damage;         // 데미지
    private double damageAmp;      // 최종뎀
    private double stDamage;       // 스탯 대미지
    private double attackSpeed;    // 공속
    private double minVal;         // 최소댐
    private double maxVal;         // 최대댐
    private double normalMonDamage; // 일몹뎀
    private double bossDamage;     // 보공
    private double critRate;       // 크리 확률
    private double critDamage;     // 크리 데미지


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
