import React from 'react'
import styled from 'styled-components';

const StyledItemDiv = styled.div`

    & > span:nth-child(4) {
        

        &  * {
            font-size: 1.3em;
        }
    }
`;

const Item = ({ item }) => {
  return (
    <StyledItemDiv>
      <span><strong>유형:</strong>{item.category} </span>
      <span><strong>가격:</strong>{item.price}원 </span>
      <span><strong>수량:</strong>{item.quantity}개 </span><br/>
      <span><strong>소계:</strong>{item.quantity * item.price}원</span>
    </StyledItemDiv>
  )
}

export default Item
