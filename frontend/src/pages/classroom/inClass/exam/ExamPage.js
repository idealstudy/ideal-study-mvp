import React, { useState } from "react";
import ExamCreation from "../../../../components/classroom/inClass/exam/ExamCreation";
import ExamList from "../../../../components/classroom/inClass/exam/ExamList";

const ExamPage = () => {
  const [exams, setExams] = useState([]);

  const handleCreateExam = (exam) => {
    setExams([...exams, { id: Date.now(), ...exam }]);
  };

  const handleEditExam = (id) => {
    // Edit logic here
  };

  const handleDeleteExam = (id) => {
    setExams(exams.filter((exam) => exam.id !== id));
  };

  return (
    <div>
      <h2>시험 관리</h2>
      <ExamCreation onCreate={handleCreateExam} />
      <ExamList
        exams={exams}
        onEdit={handleEditExam}
        onDelete={handleDeleteExam}
      />
    </div>
  );
};

export default ExamPage;
