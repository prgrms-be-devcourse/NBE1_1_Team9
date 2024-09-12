import React, { useContext, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import axios from '../../axios/BasicAxios';
import { UserContext } from '../../context/UserContext';

const StyledLink = styled(Link)`
    text-decoration: none;
    color: #333333;
    margin: 2em 0;
    padding: 1em;
    font-size: 1.1em;
`;

const UserDiv = () => {
  const [isFetching, setFetching] = useState(false);
  const userId = localStorage.getItem('userId');
  const navigate = useNavigate();
  const {setLoginUser} = useContext(UserContext);

  const logout = () => {
    if(isFetching) {
      alert('잠시만 기다려주세요.');
      return;
    }

    setFetching(true);
    axios.post(`/users/logout`)
    .then(res => {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      alert('로그아웃 완료');
      setLoginUser(null);
      //리다이렉트
      navigate('/', { redirect: true });
    })
    .finally(
      setFetching(false)
    )
  }
  return (
    <div>
      <StyledLink onClick={() => logout()}>로그아웃</StyledLink>
    </div>
  )
}

export default UserDiv
