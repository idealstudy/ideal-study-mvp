package com.idealstudy.mvp.application.service;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.MemberPageResultDto;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.infrastructure.MemberRepository;
import com.idealstudy.mvp.infrastructure.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final RedisRepository redisRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addMember(String email, String token, Role role) throws IllegalArgumentException {
        String savedToken = redisRepository.getToken(email);
        if( savedToken == null || !savedToken.equals(token))
            throw new IllegalArgumentException("유효한 토큰이 아님");

        String password = UUID.randomUUID().toString().split("-")[0];
        memberRepository.create(MemberDto.builder()
                .userId(email)
                .password(passwordEncoder.encode(password))
                .email(email)
                .fromSocial(0)
                .role(role)
                .build());
        redisRepository.deleteToken(email);

        return password;
    }

    public MemberDto findById(String userId) {
        return memberRepository.findById(userId);
    }

    public MemberDto findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public MemberPageResultDto findMembers() {

        PageRequestDto requestDto = new PageRequestDto(1, 9999);
        return memberRepository.findMembers(requestDto);
    }

    public MemberDto updateMember(MemberDto dto) {
        return memberRepository.update(dto);
    }

    public boolean deleteMember(String userId) {
        memberRepository.deleteById(userId);
        if(memberRepository.findById(userId) == null)
            return false;

        return true;
    }
}
