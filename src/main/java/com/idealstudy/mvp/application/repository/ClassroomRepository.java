package com.idealstudy.mvp.application.repository;

import com.idealstudy.mvp.application.dto.classroom.ClassroomPageResultDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;
import com.idealstudy.mvp.enums.classroom.ClassroomStatus;


public interface ClassroomRepository {

    ClassroomResponseDto create(String title, String description, Integer capacity, String thumbnail, String teacherId);

    ClassroomResponseDto findById(String id); // ID로 수업 찾기

    ClassroomPageResultDto findAll(int page, ClassroomStatus status); // 모든 수업 찾기

    ClassroomResponseDto update(String id, String title, String description, Integer capacity, String thumbnail);

    void deleteById(String id); // 수업 삭제
}