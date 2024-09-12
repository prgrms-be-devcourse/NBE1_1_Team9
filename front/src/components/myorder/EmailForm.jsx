import React, { useState } from 'react'
import styled from 'styled-components';

const StyledDiv = styled.div`
    width: 60vw;
    display: flex;
    justify-content: center;
    align-items: center;

    & > * {
        margin: 1em;
    }
`;
const EmailForm = ({data, input, submit}) => {
  
  return (
    <div className='mb-3 d-flex justify-content-center'>
      <form onSubmit={(e) => submit(e)}>
        <StyledDiv>
            <h5>이메일로 주문 조회하기</h5>
            <input 
                type="email" 
                className="form-control w-50" 
                id="email" name='email'
                placeholder="이메일을 입력하세요" 
                value={data}
                onChange={(e) => input(e.target.value)}
            />
            <button type="submit" className="btn btn-dark">주문 검색</button>
        </StyledDiv>
      </form>
    </div>
  )
}

export default EmailForm
