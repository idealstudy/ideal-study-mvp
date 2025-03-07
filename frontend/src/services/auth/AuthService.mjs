import apiClient from "../apiClient.mjs";

/**
 * 로그인
 */
export const loginUser = async (username, password) => {
  console.log("로그인 API 시도:", username, password);
  try {
    const response = await apiClient.post("/auth/login", {
      username,
      password,
    });
    console.log("로그인 API 성공:", response);
    // 서버에서 받은 JWT 토큰과 유저 정보 반환

    const { token } = response.data; // response.data = { username: , token: }
    localStorage.setItem("jwtToken", token);

    const firstLoginResponse = await apiClient.get("/api/check-first-login");
    console.log("최초 로그인 검사 결과:", firstLoginResponse);

    return { response, firstLoginResponse }; // 토큰과 유저 정보를 객체로 반환
  } catch (error) {
    console.log("로그인 API 실패", error);

    // 로그인 실패 시 임시 더미 데이터 반환
    // const dummyData = {
    //   token: "dummy-token", // 더미 토큰
    //   user: { id: "1", name: "아기 한석원", role: "teacher", level: "3" }, // 더미 유저 정보
    // };

    return null;
  }
};

/**
 * 로그아웃
 */
export const logoutUser = async (username, password) => {
  console.log("로그아웃 API 시도:", username, password);
  try {
    const response = await apiClient.get("/auth/logout/{userId}");
    console.log("로그아웃 API 성공:", response);
    return response.data;
  } catch (err) {
    console.log("로그아웃 API 실패:", err);
  }
};
