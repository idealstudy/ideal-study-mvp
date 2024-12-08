package com.idealstudy.mvp.application.service.classroom;

import com.idealstudy.mvp.infrastructure.repository.ClassroomRepository;
import com.idealstudy.mvp.infrastructure.repository.LikedRepository;
import com.idealstudy.mvp.presentation.dto.classroom.ClassroomRequestDto;
import com.idealstudy.mvp.application.dto.classroom.ClassroomResponseDto;

import com.idealstudy.mvp.infrastructure.jpa.entity.classroom.ClassroomEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    private final LikedRepository likedRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository,
                            @Qualifier("likedClassroomRepositoryImpl") LikedRepository likedRepository) {
        this.classroomRepository = classroomRepository;
        this.likedRepository = likedRepository;
    }

    public ClassroomResponseDto createClassroom(ClassroomRequestDto request) {
        ClassroomEntity savedClassroom = classroomRepository.save(request.toEntity());
        return ClassroomResponseDto.fromEntity(savedClassroom);
    }

    public List<ClassroomResponseDto> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(ClassroomResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public ClassroomResponseDto getClassroomById(String id) {
        return classroomRepository.findById(id)
                .map(ClassroomResponseDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
    }

    public ClassroomResponseDto updateClassroom(String id, ClassroomRequestDto request) {
        ClassroomEntity classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));
        request.updateEntity(classroom);
        ClassroomEntity updatedClassroom = classroomRepository.save(classroom);
        return ClassroomResponseDto.fromEntity(updatedClassroom);
    }

    public void deleteClassroom(String id) {
        if (!classroomRepository.existsById(id)) {
            throw new IllegalArgumentException("Classroom not found");
        }
        classroomRepository.deleteById(id);
    }

    public void updateLiked() {


    }

    public int countLiked(Long classroomId) {

        String collection = "classrooms";
        return likedRepository.countById(classroomId);
    }
}
