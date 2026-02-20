package com.example.study_first.dto;

import com.example.study_first.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemRequestDto {
    private long addAtk;           // 추가 공격력
    private double addDmg;         // 추가 데미지%
    private double addStDmg;       // 추가 스탯대미지%
    private double addAmp;         // 추가 증폭
    private double addMin;         // 추가 최소뎀
    private double addMax;         // 추가 최대뎀
    private double addNormalDmg;   // 추가 일몹뎀
    private double addBossDmg;     // 추가 보공
    private double addCritRate;    // 추가 크확
    private double addCritDmg;     // 추가 크뎀
    private double addAs;          // 추가 공속

    public Member applyTo(Member member) {
        return member.toBuilder()
                .attackPower(member.getAttackPower() + this.addAtk)
                .damage(member.getDamage() + this.addDmg)
                .damageAmp(member.getDamageAmp() + this.addAmp)
                .stDamage(member.getStDamage() + this.addStDmg)
                .attackSpeed(member.getAttackSpeed() + this.addAs)
                .minVal(member.getMinVal() + this.addMin)
                .maxVal(member.getMaxVal() + this.addMax)
                .normalMonDamage(member.getNormalMonDamage() + this.addNormalDmg)
                .bossDamage(member.getBossDamage() + this.addBossDmg)
                .critRate(member.getCritRate() + this.addCritRate)
                .critDamage(member.getCritDamage() + this.addCritDmg)
                .build();
    }
}