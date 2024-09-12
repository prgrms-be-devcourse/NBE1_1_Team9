import React, { createContext, useState, useEffect } from 'react';

// UserContext 생성
export const UserContext = createContext();

// UserProvider 컴포넌트
export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  // 컴포넌트가 처음 렌더링될 때 사용자 정보를 가져오는 함수
  useEffect(() => {
    const storedUser = localStorage.getItem("accessToken");
    setUser(storedUser);
  }, []);

  const login = (data) => {
    localStorage.setItem("accessToken", data.accessToken);
    localStorage.setItem("accessToken", data.refreshToken);
    setUser(data.accessToken);
  };

  const logout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    setUser(null);
  };

  return (
    <UserContext.Provider value={{ user, login, logout }}>
      {children}
    </UserContext.Provider>
  );
};
