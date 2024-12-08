package com.idealstudy.mvp.application.service.classroom.preclass;

import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryDto;
import com.idealstudy.mvp.application.dto.classroom.preclass.ClassInquiryPageResultDto;
import com.idealstudy.mvp.enums.DBErrorMsg;
import com.idealstudy.mvp.enums.SecurityErrorMsg;
import com.idealstudy.mvp.enums.classroom.Visibility;
import com.idealstudy.mvp.infrastructure.repository.preclass.ClassInquiryRepository;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ClassInquiryService {

    @Autowired
    private final ClassInquiryRepository classInquiryRepository;

    public void create(String title, String content, String classroomId, String writer, Visibility visibility) {

        TryCatchServiceTemplate.execute(() -> {
            classInquiryRepository.create(title, content, classroomId, writer, visibility);
            return null;
        }, null, DBErrorMsg.CREATE_ERROR);
    }

    public ClassInquiryDto findById(Long classInquiryId, String userId) {

        ClassInquiryDto dto = classInquiryRepository.findById(classInquiryId);
        if(dto.getVisibility().equals(Visibility.PRIVATE)) {
            if( userId != null)
                checkMine(classInquiryId, userId);
            // 회원 정보 없으면 무조건 거부
            else {
                log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
                throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            }
        }
        return dto;
    }

    public ClassInquiryPageResultDto findListByClassId(String classId, int page) {

        return TryCatchServiceTemplate.execute(() -> classInquiryRepository.findListByClassId(classId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }

    public ClassInquiryPageResultDto findListByMemberId(String userId, int page){

        return TryCatchServiceTemplate.execute(() -> classInquiryRepository.findListByMemberId(userId, page),
                null, DBErrorMsg.SELECT_ERROR);
    }


    public ClassInquiryDto update(Long classInquiryId, String title, String content, String classroomId, String writer,
                                  Visibility visibility) {

        return TryCatchServiceTemplate.execute(() -> classInquiryRepository.update(classInquiryId, title, content,
                classroomId, writer, visibility), ()-> checkMine(classInquiryId, writer), DBErrorMsg.UPDATE_ERROR);
    }

    public void delete(Long inquiryId, String deleter) {

        TryCatchServiceTemplate.execute(() -> classInquiryRepository.delete(inquiryId),
                ()-> checkMine(inquiryId, deleter), DBErrorMsg.DELETE_ERROR);
    }

    private void checkMine(Long classInquiryId, String userId) {

        ClassInquiryDto dto = classInquiryRepository.findById(classInquiryId);

        if(dto.getCreatedBy().equals(userId)) {
            log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
        }
    }
}
