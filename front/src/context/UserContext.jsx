import React, { createContext, useState, useEffect } from 'react';

// UserContext 생성
export const UserContext = createContext();

export const UserProvider = ({ children }) => {
    const [loginUser, setLoginUser] = useState(null);

    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken');
        if (accessToken) {
            setLoginUser(accessToken);  // 토큰으로 로그인 상태를 설정
        }
    }, []);

    return (
        <UserContext.Provider value={{ loginUser, setLoginUser }}>
            {children}
        </UserContext.Provider>
    );
};
