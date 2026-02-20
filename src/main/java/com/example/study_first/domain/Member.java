package com.example.study_first.domain;

import com.example.study_first.dto.MemberRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    // --- 전투력 계산을 위한 기본 스탯 ---
    private long attackPower;      // 공격력
    private double damage;         // 데미지
    private double damageAmp;      // 데미지 증폭
    private double stDamage;       // 스탯 대미지(실 스텟)
    private double attackSpeed;    // 공속
    private double minVal;         // 최소댐
    private double maxVal;         // 최대댐
    private double normalMonDamage; // 일몹뎀
    private double bossDamage;     // 보공
    private double critRate;       // 크리 확률
    private double critDamage;     // 크리 데미지


    //크리 데미지 계산
    public double getCritExpectation() {
        return 1.0 + (Math.min(1.0, critRate / 100.0) * (critDamage / 100.0));
    }

    //기본 데미지 계산
    public double calculateHitDamage() {
        System.out.println("공격력: " + attackPower + ", 데미지: " + damage);
        return attackPower
                * (1.0 + (damage / 100.0))
                * (1.0 + (stDamage / 100.0))
                * (1.0 + (damageAmp / 100.0))
                * ((minVal + maxVal) / 200.0)
                * getCritExpectation();
    }

    // 2. 공속 적용 데미지 공속이 1.5를 넘어가면 1.5로 고정, 그 미만이면 입력값 그대로 사용
    public double calculateFinalDps() {
        double rawAs = Math.min(1.5, (attackSpeed / 100.0));
        double finalAs = Math.floor(rawAs * 1000.0) / 1000.0;
        return calculateHitDamage() * finalAs;
    }

    // 3. 일반몹 전투력 계산
    public long calculateHuntingCP() {
        return (long) (calculateFinalDps() * (1 + ((normalMonDamage) / 100.0)));
    }

    // 4. 보스 전투력 계산
    public long calculateBossCP() {
        return (long) (calculateFinalDps() * (1 + (bossDamage / 100.0)));
    }

    public void update(MemberRequestDto dto){
        this.attackPower = dto.getAttackPower();
        this.damage = dto.getDamage();
        this.damageAmp = dto.getDamageAmp();
        this.stDamage = dto.getStDamage();
        this.attackSpeed = dto.getAttackSpeed();
        this.minVal = dto.getMinVal();
        this.maxVal = dto.getMaxVal();
        this.normalMonDamage = dto.getNormalMonDamage();
        this.bossDamage = dto.getBossDamage();
        this.critRate = dto.getCritRate();
        this.critDamage = dto.getCritDamage();
    }
}
