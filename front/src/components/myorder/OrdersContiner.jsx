import React from 'react'
import styled from 'styled-components'
import OrderList from './Orders';

const StyledOrdersContiner = styled.div`
    display: flex;
    justify-content: center;
    margin-top: 1em;
    background-color: gray;
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
