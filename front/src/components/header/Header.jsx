import { Link, NavLink } from "react-router-dom"
import "../../App.css"
import React from 'react'
import styled from "styled-components"
import UserContainer from "../user/UserContainer";


const StyledHeader = styled.div`
    margin-top: 3em;

    & > div {
        margin-top: 2em;
        margin-bottom: 2em;
    }
`; 

const StyledNavLink = styled(NavLink)`
    text-decoration: none;
    color: #333333;
    margin: 2em 0;
    padding: 1em;
    font-size: 1.1em;

    &:hover {
        color: #111111;
    }

    &.active {
        color: #ffffff; /* 활성화된 링크의 색상 */
        background-color: #111111;
        padding: 0.3em;
    }
`;

//로그인 관련 컨테이너 추가
const Header = () => {


  return (
    <StyledHeader className="row justify-content-center m-4">
      <h1 className="text-center d-inline">Grids & Circle</h1>
      <div className="w-75 d-flex justify-content-between">
        <div>
            <StyledNavLink 
                to='/'
                className={({ isActive }) => (isActive ? 'active' : '')}
            >
                상품 주문하러 이동하기
            </StyledNavLink>
            <StyledNavLink 
                to='/myOrder'
                className={({ isActive }) => (isActive ? 'active' : '')}
            >
                내 주문 조회하기
            </StyledNavLink>
        </div>
        <UserContainer/>
      </div>
    </StyledHeader>
  )
}

export default Header
