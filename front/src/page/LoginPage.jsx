import React, { useContext, useState } from 'react'
import { Link, redirect, useNavigate } from 'react-router-dom';
import styled from 'styled-components'
import axios from '../axios/BasicAxios';
import EmailInput from '../components/user/EmailInput';
import PasswordInput from '../components/user/PasswordInput';
import { UserContext } from '../context/UserContext';

const StyledLoginDiv = styled.div`
    width: 30vw;
    margin: auto;
    & > form {
        margin-top: 3em;
        display: flex;
        justify-content: center;
        flex-direction: column;

        
    }

    & > h2 {
        text-align: center;
        margin: 1em;
    }

    & > span {
        display: block;
        text-align: center;
        font-size: 1.1em;
        margin-top: 1em;
    }
`;

const StyledLink = styled(Link)`
    color: #333333;
`;

const LoginPage = () => {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isFetching, setFetching] = useState(false);
    const {setLoginUser} = useContext(UserContext);
    const navigate = useNavigate();

    const login = (e) => {
        e.preventDefault();

        if(!email || !password) {
            alert('아이디와 비밀번호를 입력하세요');
            return;
        }

        if(isFetching) {
            alert('잠시만 기다려주세요');
            return; 
        }

        setFetching(true);
        axios.post('/users/login', {
            email: email,
            password: password
        })
        .then(res => {
            if(res.status !== 200) {
                throw res.message;
            }
            return res.data;
        })
        .then(({data}) => {
            //로그인 후 로직 저장
            console.log(data);
            setLoginUser(data.accessToken);
            localStorage.setItem('refreshToken', data.refreshToken);
            
            alert('로그인 성공');
            // 로그아웃 후 리다이렉트
            navigate('/', { replace: true });
        })
        .catch(err => {
            alert('아이디 또는 비밀번호가 틀렸습니다.');
        })
        .finally(
            setFetching(false)
        )
    }

  return (
    <StyledLoginDiv>
      <h2>로그인</h2>
      <form className='w-10' onSubmit={(e) => login(e)}>
        <EmailInput value={email} setValue={setEmail}/>
        <PasswordInput value={password} setValue={setPassword}/>
        <button type="submit" class="btn btn-primary">로그인</button>
      </form>
      <span>회원이 아니시라구요? <StyledLink to='/signup'>회원가입하기</StyledLink></span>
    </StyledLoginDiv>
  )
}

export default LoginPage
