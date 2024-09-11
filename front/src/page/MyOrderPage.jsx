import React, { useEffect, useState } from 'react'
import EmailForm from '../components/myorder/EmailForm'
import OrdersContiner from '../components/myorder/OrdersContiner'
import styled from 'styled-components'
import axios from '../axios/BasicAxios';

const StyledMyOrderPage = styled.div`
    flex-direction: column;
`;

const MyOrderPage = () => {

    const [orders, setOrders] = useState([]);
    const [email, setEmail] = useState('');
    const getOrderLog = (e) => {
        e.preventDefault();
        axios.get('')
        .then(res => {
            console.log(res);
        })
        .catch(err => {
            console.log(err);
        });
    }

  return (
    <StyledMyOrderPage className='m-auto d-flex justify-content-center w-75'>
      <EmailForm data={email} input={setEmail} submit={getOrderLog}/>
      <OrdersContiner orders={orders}/>
    </StyledMyOrderPage>
  )
}

export default MyOrderPage
