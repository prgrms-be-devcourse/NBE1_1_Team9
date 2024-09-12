import React, { useContext } from 'react';
import UserDiv from './UserDiv';
import NonUserDiv from './NonUserDiv';
import { UserContext } from '../../context/UserContext'; // UserContext 임포트

const UserContainer = () => {
    const { user } = useContext(UserContext); // useContext로 로그인 정보 가져오기

    return (
        <div>
            {user ? <UserDiv /> : <NonUserDiv />}
        </div>
    );
};

export default UserContainer;
