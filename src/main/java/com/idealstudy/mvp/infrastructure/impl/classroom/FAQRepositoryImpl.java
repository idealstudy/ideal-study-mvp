package com.idealstudy.mvp.infrastructure.impl.classroom;

import com.idealstudy.mvp.application.dto.PageRequestDto;
import com.idealstudy.mvp.application.dto.PageResultDto;
import com.idealstudy.mvp.application.dto.classroom.FAQDto;
import com.idealstudy.mvp.application.dto.classroom.FAQPageResultDto;
import com.idealstudy.mvp.infrastructure.FAQRepository;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.FAQEntity;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.ClassroomJpaRepository;
import com.idealstudy.mvp.infrastructure.jpa.repository.classroom.FAQJpaRepository;
import com.idealstudy.mvp.mapstruct.FAQMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.function.Function;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FAQRepositoryImpl implements FAQRepository {

    @Autowired
    private final FAQJpaRepository faqJpaRepository;

    @Autowired
    private final ClassroomJpaRepository classroomJpaRepository;

    @Autowired
    private final FAQMapper faqMapper;

    @Override
    public void create(FAQDto dto) {

        FAQEntity faqEntity = faqMapper.dtoTOEntity(dto);

        ClassroomEntity classroomEntity = classroomJpaRepository.findById(dto.getClassroomId()).orElseThrow();
        faqEntity.setClassroom(classroomEntity);

        log.info("생성된 entity: " + faqEntity);

        faqJpaRepository.save(faqEntity);
    }

    @Override
    public FAQDto findById(Long faqId) {

        FAQEntity entity = faqJpaRepository.findById(faqId).orElseThrow();

        return faqMapper.entityToDto(entity);
    }

    @Override
    public FAQPageResultDto findList(PageRequestDto dto, String classroomId) {

        Pageable pageable = dto.getPageable(Sort.by("regDate").ascending());

        Page<FAQEntity> results = faqJpaRepository.findByClassroom_classroomId(classroomId, pageable);

        Function<FAQEntity, FAQDto> fn = faqMapper::entityToDto;

        return faqMapper.toPageResultDto(new PageResultDto<FAQDto, FAQEntity>(results, fn));
    }

    @Override
    public FAQDto update(FAQDto dto) {

        FAQEntity entity = faqJpaRepository.findById(dto.getId()).orElseThrow();
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());

        FAQEntity result = faqJpaRepository.save(entity);
        return faqMapper.entityToDto(result);
    }

    @Override
    public void delete(Long faqId) {

        FAQEntity entity = faqJpaRepository.findById(faqId).orElseThrow();
        faqJpaRepository.delete(entity);
    }
}
