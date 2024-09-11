import React from 'react'
import Item from './Item'

const Order = ({ data }) => {
    const status = data.orderStatus === 'ORDERED' ? '주문 완료' : '배송 진행 중';
  return (
    <div>
        <h6>{data?.email}</h6>
        <h6>{data?.address}</h6>
        <h6>{data?.postcode}</h6>
        <h6>{status}</h6>
        <h6>아이템 목록</h6>
        {
            data?.orderDetails.map((per, index) => 
                <Item key={index} item={per}/>
            )
        }
    </div>
  )
}

export default Order
