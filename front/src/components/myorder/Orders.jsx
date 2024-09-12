import React from 'react'
import Order from './Order'
import styled from 'styled-components'

const StyledOrders = styled.div`
  background-color: #999999;
`;

const Orders = ({ data }) => {
  return (
    <StyledOrders>
        {
            data.map(order => 
                <Order key={order?.id} data={order}/>
            )
        }
    </StyledOrders>
  )
}

export default Orders
