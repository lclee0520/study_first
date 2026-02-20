package com.example.study_first.service;

import com.example.study_first.domain.Member;
import com.example.study_first.dto.MemberRequestDto;
import com.example.study_first.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public void updateMember(Long id, MemberRequestDto requestDto){
        Member member = findMember(id);
        member.update(requestDto);
    }

    public void deleteMember(Long id){
        Member member = findMember(id);
        memberRepository.delete(member);
    }

    private Member findMember(Long id){
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다. ID: " + id));
    }
}