package com.idealstudy.mvp.application.service.classroom.inclass;

import com.idealstudy.mvp.application.component.ClassroomComponent;
import com.idealstudy.mvp.application.component.EnrollmentComponent;
import com.idealstudy.mvp.application.dto.classroom.inclass.AttendanceDto;
import com.idealstudy.mvp.application.repository.inclass.AttendanceRepository;
import com.idealstudy.mvp.domain.Attendance;
import com.idealstudy.mvp.enums.error.DBErrorMsg;
import com.idealstudy.mvp.enums.error.SecurityErrorMsg;
import com.idealstudy.mvp.util.TryCatchServiceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final EnrollmentComponent enrollmentComponent;

    private final ClassroomComponent classroomComponent;

    private final Attendance attendance;

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository, EnrollmentComponent enrollmentComponent,
                             ClassroomComponent classroomComponent) {
        this.attendanceRepository = attendanceRepository;
        this.enrollmentComponent = enrollmentComponent;
        this.classroomComponent = classroomComponent;

        attendance = new Attendance();
    }

    public AttendanceDto checkIn(String classroomId, String studentId) {

        return TryCatchServiceTemplate.execute(() -> {

            if(attendanceRepository.todayVisitInfo(LocalDateTime.now(ZoneId.of("Asia/Seoul")), studentId)
                    .isPresent())
                throw new IllegalStateException("이미 존재하는 출석 정보입니다.");

            return attendanceRepository.checkIn(classroomId);
        },
        () -> enrollmentComponent.checkAffiliated(classroomId, studentId), DBErrorMsg.CREATE_ERROR);
    }

    public AttendanceDto checkOut(Long id, String studentId) {

        return TryCatchServiceTemplate.execute(() -> {

            AttendanceDto dto = attendanceRepository.findById(id);

            if( !dto.getCreatedBy().equals(studentId))
                throw new SecurityException(SecurityErrorMsg.NOT_YOURS.toString());

            return attendanceRepository.checkOut(id, studentId, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        },
        null, DBErrorMsg.UPDATE_ERROR);
    }

    public List<AttendanceDto> getIndividualAttendance(String studentId, LocalDateTime time) {

        return TryCatchServiceTemplate.execute(() -> {
            Map<String, LocalDate> dateMap = attendance.getDate(time);

            return attendanceRepository.getIndividualAttendance(studentId,
                    dateMap.get("startDate"), dateMap.get("endDate"));
        }, null, DBErrorMsg.SELECT_ERROR);
    }

    public List<AttendanceDto> getTodayClassroomAttendance(String classroomId, LocalDate date, String teacherId) {

        return TryCatchServiceTemplate.execute(() ->
                        attendanceRepository.getTodayClassroomAttendance(classroomId, date),
                () -> classroomComponent.checkMyClassroom(teacherId, classroomId), DBErrorMsg.SELECT_ERROR);
    }
}
