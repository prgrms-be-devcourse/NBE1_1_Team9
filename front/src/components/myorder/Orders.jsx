import React from 'react'
import Order from './Order'
import styled from 'styled-components'

const StyledOrders = styled.div`
  background-color: #cccccc;
`;

const Orders = ({ data }) => {

  if(data.length > 0) {
    return (
    
      <StyledOrders className='shadow p-3 mb-5 bg-body-tertiary rounded'>
          {
              data.map(order => 
                  <Order key={order?.id} data={order}/>
              )
          }
      </StyledOrders>
    )
  } else return null;
}

export default Orders
