package com.idealstudy.mvp.infrastructure.repository;


import com.idealstudy.mvp.application.dto.OfficialProfileDto;

public interface OfficialProfileRepository {

    void create(String teacherId);

    OfficialProfileDto findByTeacherId(String teacherId);

    OfficialProfileDto update(OfficialProfileDto dto);
}
