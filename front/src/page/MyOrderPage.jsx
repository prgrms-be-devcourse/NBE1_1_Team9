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
    const [isFetching, setFetching] = useState(false);

    const getOrderLog = (e) => {
        e.preventDefault();
        console.log(email);

        if(!email) {
            alert('아메일을 입력하세요.')
            return;
        }

        if(isFetching) {
            alert('잠시만 기다려주세요.')
            return;
        }
        
        setFetching(true);
        axios.get(`/orders?email=${email}`)
        .then(res => {
            if(res.status !== 200) {
                throw res.message;
            }
            return res.data;
        })
        .then(({ data }) => {
            setOrders(data);
        })
        .catch(err => {
            console.log(err);
        })
        .finally( 
            setFetching(false)
        );
    }

  return (
    <StyledMyOrderPage className='m-auto d-flex justify-content-center w-75'>
      <EmailForm data={email} input={setEmail} submit={getOrderLog}/>
      <OrdersContiner orders={orders}/>
    </StyledMyOrderPage>
  )
}

export default MyOrderPage
