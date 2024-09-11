import React from 'react'
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const StyledLink = styled(Link)`
    text-decoration: none;
    color: #333333;
    margin: 2em 0;
    padding: 1em;
    font-size: 1.1em;
`;

const NonUserDiv= () => {
  return (
    <div>
      <StyledLink to='/login'>로그인</StyledLink>
      <StyledLink to='/signup'>회원가입</StyledLink>
    </div>
  )
}

export default NonUserDiv
