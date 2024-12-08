package com.idealstudy.mvp.application.service;

import com.idealstudy.mvp.application.dto.OfficialProfileDto;
import com.idealstudy.mvp.infrastructure.repository.OfficialProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OfficialProfileService {

    @Autowired
    private final OfficialProfileRepository officialProfileRepository;

    public void create(String teacherId) {
        officialProfileRepository.create(teacherId);
    }

    public OfficialProfileDto selectOne(String teacherId) {

        return officialProfileRepository.findByTeacherId(teacherId);
    }

    public OfficialProfileDto update(OfficialProfileDto dto) {

        return officialProfileRepository.update(dto);
    }
}
