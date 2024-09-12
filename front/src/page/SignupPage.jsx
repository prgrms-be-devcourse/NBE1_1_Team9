import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components'
import axios from '../axios/BasicAxios';
import EmailInput from '../components/user/EmailInput';
import PasswordInput from '../components/user/PasswordInput';
import NameInput from '../components/user/NameInput';

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

const SignupPage = () => {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [isFetching, setFetching] = useState(false);
    const navigate = useNavigate();
    const join = (e) => {
        e.preventDefault();

        if(!email || !password || !name) {
            alert('아이디와 비밀번호를 입력하세요');
            return;
        }

        if(isFetching) {
            alert('잠시만 기다려주세요');
            return; 
        }

        setFetching(true);
        axios.post('/users/join', {
            email: email,
            password: password,
            name: name
        })
        .then(res => {
            console.log(res);
            if(res.status !== 200) {
                throw res.message;
            }
            return res.data;
        })
        .then(({data}) => {
            //로그인 후 로직 저장
            console.log(data);
            alert('회원가입 완료되었습니다!');
            localStorage.setItem("userId", data.userId);
            navigate('/login');
        })
        .catch(err => {
            alert('회원가입 실패');
        })
        .finally(
            setFetching(false)
        )
    }


  return (
    <StyledLoginDiv>
        <h2>회원가입</h2>
        <form className='w-10' onSubmit={(e) => join(e)}>
            <EmailInput value={email} setValue={setEmail}/>
            <PasswordInput value={password} setValue={setPassword}/>
            <NameInput value={name} setValue={setName}/>
            <button type="submit" class="btn btn-primary">로그인</button>
        </form>
    </StyledLoginDiv>
  )
}

export default SignupPage
