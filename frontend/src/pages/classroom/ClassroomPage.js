import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import { readClassById } from "../../services/classroom/ClassroomService.mjs";
import LikeButton from "../../components/LikeButton";
import ClassInfo from "./ClassInfo";
import ClassEnrollment from "../../components/classroom/preClass/enrollment/Enrollment";
import ClassFAQ from "../../components/classroom/preClass/FAQ/FAQBoard";
import ClassInquiry from "./preClass/inquiry/InquiryBoardPage";
import ClassSchedule from "../../components/classroom/inClass/schedule/ScheduleBoard";

import "./ClassroomPage.css";
import PostBoard from "../../components/classroom/inClass/post/PostBoard";

const ClassroomPage = () => {
  const { classId } = useParams();
  const [classroomInfo, setClassroomInfo] = useState(null);

  useEffect(() => {
    // API 호출로 클래스 상세 정보 가져오기
    const fetchClass = async () => {
      const data = await readClassById(classId);
      setClassroomInfo(data);
    };
    fetchClass();
  }, [classId]);

  if (!classroomInfo) return <p>로딩 중...</p>;

  return (
    <div className="classroom-container">
      {/* 클래스 정보 섹션 */}
      <div className="section class-info-section">
        <ClassInfo classroom={classroomInfo} />
      </div>

      {/* 좋아요 버튼 */}
      <div className="section like-button">
        <LikeButton />
      </div>

      {/* 수업 신청 섹션 */}
      <div className="section enrollment-section">
        <ClassEnrollment classId={classId} />
      </div>

      {/* 질문 게시판 섹션 */}
      <div className="section inquiry-section">
        <ClassInquiry classId={classId} />
      </div>

      {/* FAQ 섹션 */}
      <div className="section faq-section">
        <ClassFAQ classId={classId} />
      </div>

      {/* 스케줄 섹션 */}
      <div className="section schedule-section">
        <ClassSchedule classId={classId} />
      </div>

      {/* 게시물 섹션 */}
      <div className="section post-section">
        <PostBoard classId={classId} />
      </div>
    </div>
  );
};

export default ClassroomPage;
