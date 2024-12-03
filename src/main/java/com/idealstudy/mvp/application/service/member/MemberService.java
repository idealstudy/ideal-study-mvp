package com.idealstudy.mvp.application.service.member;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.member.*;
import com.idealstudy.mvp.enums.member.Gender;
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

    // Repository에 포함시키면 순환 참조 문제 발생하여 불가능. 인코딩은 어플리케이션 계층에서 처리하기로 결정
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public String addMember(String email, String token, Role role) throws IllegalArgumentException {
        String savedToken = redisRepository.getToken(email);
        if( savedToken == null || !savedToken.equals(token))
            throw new IllegalArgumentException("유효한 토큰이 아님");

        String password = UUID.randomUUID().toString().split("-")[0];
        addMember(email, role, password);
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

    @Deprecated
    public void createDummies() {

        //addMember("student@gmail.com", Role.ROLE_STUDENT, "1234");
        //addMember("teacher@gmail.com", Role.ROLE_TEACHER, "1234");
        //addMember("parents@gmail.com", Role.ROLE_PARENTS, "1234");
        //addMember("admin@gmail.com", Role.ROLE_ADMIN, "1234");
        // addMember("badteacher@gmail.com", Role.ROLE_TEACHER, "1234");
        addMember("otherparents@gmail.com", Role.ROLE_PARENTS, "1234");
        addMember("otherstudent@gmail.com", Role.ROLE_STUDENT, "1234");
    }

    public boolean testPassword(String raw, String encoded) {
        return passwordEncoder.matches(raw, encoded);
    }

    private void addMember(String email, Role role, String password) {

        String encodedPassword = passwordEncoder.encode(password);

        if(role == Role.ROLE_TEACHER)
            memberRepository.create((TeacherDto) TeacherDto.builder()
                    .userId(UUID.randomUUID().toString())
                    .password(encodedPassword)
                    .email(email)
                    .fromSocial(0)
                    .role(role)
                    .sex(Gender.MALE)
                    .build());
        if(role == Role.ROLE_STUDENT)
            memberRepository.create((StudentDto) StudentDto.builder()
                    .userId(UUID.randomUUID().toString())
                    .password(encodedPassword)
                    .email(email)
                    .fromSocial(0)
                    .role(role)
                    .sex(Gender.MALE)
                    .build());
        if(role == Role.ROLE_PARENTS)
            memberRepository.create((ParentsDto) ParentsDto.builder()
                    .userId(UUID.randomUUID().toString())
                    .password(encodedPassword)
                    .email(email)
                    .fromSocial(0)
                    .role(role)
                    .sex(Gender.MALE)
                    .build());
        /*
        if(role == Role.ROLE_ADMIN)
            memberRepository.create((AdminDto) AdminDto.builder()
                    .userId(UUID.randomUUID().toString())
                    .password(encodedPassword)
                    .email(email)
                    .fromSocial(0)
                    .role(role)
                    .sex(Gender.MALE)
                    .build());

         */
    }
}
