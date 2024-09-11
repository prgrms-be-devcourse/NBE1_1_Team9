import React from 'react'
import Item from './Item'

const Order = ({ data }) => {
  return (
    <div>
        <h6>{data?.email}</h6>
        <h6>{data?.address}</h6>
        <h6>{data?.postcode}</h6>
        <h6>{data?.orderStatus}</h6>
        {
            data?.orderDetails.map((per,index) => 
                <Item key={index} item={per}/>
            )
        }
    </div>
  )
}

export default Order
