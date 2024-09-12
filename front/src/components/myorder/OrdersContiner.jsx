import React from 'react'
import styled from 'styled-components'
import OrderList from './Orders';

const StyledOrdersContiner = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-top: 1em;

    & > h3 {
        text-align: center;
        margin-bottom: 1em;
    }
`;

const OrdersContiner = ({ orders }) => {

  return (
    <StyledOrdersContiner>
        <h3>주문 내역</h3>
        <OrderList data={orders}/>
    </StyledOrdersContiner>
  )
}

export default OrdersContiner
