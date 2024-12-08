package com.idealstudy.mvp.infrastructure.repository.preclass;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.FAQPageResultDto;

public interface FAQRepository {

    void create(FAQDto dto);

    FAQDto findById(Long faqId);

    FAQPageResultDto findList(PageRequestDto dto, String classroomId);

    FAQDto update(FAQDto dto);

    void delete(Long faqId);
}
