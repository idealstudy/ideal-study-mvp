// 자기소개
import React, { useState } from "react";
import Button from "../Button";

const UserInfoSpace = ({
  user,
  isAuthenticated,
  isEditing,
  setIsEditing,
  onSave,
}) => {
  const [updatedUser, setUpdatedUser] = useState({
    name: user.name || "이름 안들어옴", // 변경된 이름
    password: user.password || "", // 변경된 비밀번호
    phone: user.phoneAddress || "전화번호 안들어옴", // 변경된 전화번호
    email: user.email || "이메일 안들어옴", // 변경된 이메일
    school: user.school || "", // 변경된 학교
    gender: user.sex || "성별 안들어옴", // 변경된 성별
    level: user.level || "레벨 안들어옴", // 기존 값 유지
  }); // 수정될 유저정보

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUpdatedUser({ ...updatedUser, [name]: value }); // 입력값 상태 업데이트
  };

  return (
    <div>
      <h3>김대민</h3>
      <div className="profile-header">
        <h2>
          {isEditing ? (
            <input
              type="text"
              name="name"
              value={updatedUser.name}
              onChange={handleChange}
            />
          ) : (
            updatedUser.name
          )}
        </h2>
      </div>
      <div className="profile-details">
        <p>
          <strong>이메일:</strong>{" "}
          {isEditing ? (
            <input
              type="email"
              name="email"
              value={updatedUser.email}
              onChange={handleChange}
            />
          ) : (
            updatedUser.email
          )}
        </p>
        <p>
          <strong>연락처:</strong>{" "}
          {isEditing ? (
            <input
              type="text"
              name="phone"
              value={updatedUser.phone}
              onChange={handleChange}
            />
          ) : (
            updatedUser.phone
          )}
        </p>
        <p>
          <strong>학교:</strong>{" "}
          {isEditing ? (
            <input
              type="text"
              name="school"
              value={updatedUser.school}
              onChange={handleChange}
            />
          ) : (
            updatedUser.school
          )}
        </p>
        <p>
          <strong>성별:</strong>{" "}
          {isEditing ? (
            <input
              type="text"
              name="gender"
              value={updatedUser.gender}
              onChange={handleChange}
            />
          ) : (
            updatedUser.gender
          )}
        </p>
        <p>
          <strong>등급:</strong>{" "}
          {isEditing ? (
            <input
              type="text"
              name="grade"
              value={updatedUser.grade}
              onChange={handleChange}
            />
          ) : (
            updatedUser.grade
          )}
        </p>
        <p>
          <strong>레벨:</strong>{" "}
          {isEditing ? (
            <input
              type="text"
              name="level"
              value={updatedUser.level}
              onChange={handleChange}
            />
          ) : (
            updatedUser.level
          )}
        </p>
      </div>
      {isAuthenticated && (
        <>
          {isEditing ? (
            <Button onClick={() => onSave(updatedUser)}>저장</Button> // 저장 버튼
          ) : (
            <Button onClick={() => setIsEditing(true)}>수정</Button> // 수정 버튼
          )}
        </>
      )}
    </div>
  );
};

export default UserInfoSpace;
