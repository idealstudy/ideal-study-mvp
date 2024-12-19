import React, { useState } from "react";
import AssignmentCreation from "../../../../components/classroom/inClass/assignment/AssignmentCreation";
import AssignmentList from "../../../../components/classroom/inClass/assignment/AssignmentList";

const AssignmentPage = () => {
  const [assignments, setAssignments] = useState([]);

  const handleCreateAssignment = (assignment) => {
    setAssignments([...assignments, { id: Date.now(), ...assignment }]);
  };

  const handleEditAssignment = (id) => {
    // Edit logic here
  };

  const handleDeleteAssignment = (id) => {
    setAssignments(assignments.filter((assignment) => assignment.id !== id));
  };

  return (
    <div>
      <h2>과제 관리</h2>
      <AssignmentCreation onCreate={handleCreateAssignment} />
      <AssignmentList
        assignments={assignments}
        onEdit={handleEditAssignment}
        onDelete={handleDeleteAssignment}
      />
    </div>
  );
};

export default AssignmentPage;
