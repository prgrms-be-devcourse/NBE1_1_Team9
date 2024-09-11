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
const EmailForm = () => {
  const [email, setEmail] = useState('');
  
  const getOrdersLog = () => {

  }

  return (
    <div className='mb-3 d-flex justify-content-center'>
      <form onSubmit={(e) => getOrdersLog(e)}>
        <StyledDiv>
            <input 
                type="email" 
                className="form-control w-50" 
                id="email" name='email'
                placeholder="이메일을 입력하세요" 
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <button type="submit" className="btn btn-dark">주문 검색</button>
        </StyledDiv>
      </form>
    </div>
  )
}

export default EmailForm
