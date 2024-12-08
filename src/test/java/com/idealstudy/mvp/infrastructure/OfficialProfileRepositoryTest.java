package com.idealstudy.mvp.infrastructure;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.application.dto.member.MemberDto;
import com.idealstudy.mvp.application.dto.member.TeacherDto;
import com.idealstudy.mvp.enums.member.Gender;
import com.idealstudy.mvp.enums.member.Role;
import com.idealstudy.mvp.enums.member.SchoolRegister;
import com.idealstudy.mvp.infrastructure.repository.MemberRepository;
import com.idealstudy.mvp.infrastructure.repository.OfficialProfileRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
public class OfficialProfileRepositoryTest {

    private final MemberRepository memberRepository;
    private final OfficialProfileRepository officialProfileRepository;

    private static final String USER_ID = UUID.randomUUID().toString();

    @Autowired
    public OfficialProfileRepositoryTest(MemberRepository memberRepository, OfficialProfileRepository repository) {
        this.memberRepository = memberRepository;
        this.officialProfileRepository = repository;
    }

    @BeforeEach
    public void createDummyTeacher() {
        String univ = "한국대학교";
        SchoolRegister schoolRegister = SchoolRegister.GRADUATION;
        String subject = "수학";

        TeacherDto dto = TeacherDto.builder()
                .userId(USER_ID)
                .password("abcd1234")
                .phoneAddress("010-1234-1234")
                .email("testteacher@gmail.com")
                .role(Role.ROLE_TEACHER)
                .sex(Gender.MALE)
                .referralId(UUID.randomUUID().toString())
                .fromSocial(0)
                .univ(univ)
                .status(schoolRegister)
                .subject(subject)
                .build();

        memberRepository.create(dto);
    }

    @Test
    public void save() {

        MemberDto memberDto = memberRepository.findById(USER_ID);
        Assertions.assertThat(memberDto).isNotNull();
        Assertions.assertThat(memberDto.getUserId()).isEqualTo(USER_ID);

        String univ = "한국대학교";
        TeacherDto teacherDto = memberRepository.findTeacherById(USER_ID);
        Assertions.assertThat(teacherDto).isNotNull();
        Assertions.assertThat(teacherDto.getUserId()).isEqualTo(USER_ID);
        Assertions.assertThat(teacherDto.getUniv()).isEqualTo(univ);

        String initMsg = "<p>최초 프로필 생성됨</p>";

        officialProfileRepository.create(USER_ID);

        OfficialProfileDto dto = officialProfileRepository.findByTeacherId(USER_ID);
        Assertions.assertThat(dto).isNotNull();
        Assertions.assertThat(dto.getContent()).isEqualTo(initMsg);
    }

    @Test
    public void update() {

        officialProfileRepository.create(USER_ID);

        String html = "<h1>아무개 공식 프로필</h1>\n" +
                "<p>저는 수학 정말 잘합니다. 믿어주세요.</p>";

        OfficialProfileDto officialProfileDto = OfficialProfileDto.builder()
                .teacherId(USER_ID)
                .content(html)
                .build();

        officialProfileRepository.update(officialProfileDto);

        OfficialProfileDto resultDto = officialProfileRepository.findByTeacherId(USER_ID);

        Assertions.assertThat(resultDto).isNotNull();
        Assertions.assertThat(resultDto.getTeacherId()).isEqualTo(USER_ID);
        Assertions.assertThat(resultDto.getContent().isEmpty()).isFalse();
        Assertions.assertThat(resultDto.getContent()).isEqualTo(html);
    }
}
