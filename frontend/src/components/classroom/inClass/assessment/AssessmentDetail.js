import React from "react";

const AssessmentDetail = ({ assignment }) => {
  return (
    <div>
      <h3>과제 상세</h3>
      <h4>{assignment.title}</h4>
      <p>{assignment.description}</p>
    </div>
  );
};

export default AssessmentDetail;
