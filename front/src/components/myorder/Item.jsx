import React from 'react'

const Item = ({ item }) => {
  return (
    <div>
      <span><strong>유형:</strong>{item.category}</span><br/>
      <span><strong>가격:</strong>{item.price}원</span><br/>
      <span><strong>수량:</strong>{item.quantity}개</span><br/>
      <span><strong>소계:</strong>{item.quantity * item.price}원</span><br/>
    </div>
  )
}

export default Item
