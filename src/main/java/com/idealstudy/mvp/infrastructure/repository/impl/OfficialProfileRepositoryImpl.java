package com.idealstudy.mvp.infrastructure.repository.impl;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.infrastructure.repository.OfficialProfileRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.OfficialProfileEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.member.TeacherEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.OfficialProfileJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.member.TeacherJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OfficialProfileRepositoryImpl implements OfficialProfileRepository {

    @Autowired
    private final OfficialProfileJpaRepository officialProfileJpaRepository;

    @Autowired
    private final TeacherJpaRepository teacherJpaRepository;

    @Override
    public void create(String teacherId) {

        String initContent = "<p>최초 프로필 생성됨</p>";

        TeacherEntity teacherEntity = teacherJpaRepository.findById(teacherId).orElseThrow();

        OfficialProfileEntity entity = OfficialProfileEntity.builder()
                .teacher(teacherEntity)
                .id(teacherId)
                .content(initContent)
                .build();

        officialProfileJpaRepository.save(entity);
    }

    @Override
    public OfficialProfileDto findByTeacherId(String teacherId) {

        OfficialProfileEntity entity = officialProfileJpaRepository.findById(teacherId).orElseThrow();

        return entity.toDto();
    }

    @Override
    public OfficialProfileDto update(OfficialProfileDto dto) {

        OfficialProfileEntity entity = officialProfileJpaRepository.findById(dto.getTeacherId()).orElseThrow();
        entity.setContent(dto.getContent());
        return officialProfileJpaRepository.save(entity).toDto();
    }
}
