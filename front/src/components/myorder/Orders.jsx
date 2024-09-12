import React from 'react'
import Order from './Order'

const Orders = ({ data }) => {
  return (
    <div>
        {
            data.map(order => 
                <Order key={order?.id} data={order}/>
            )
        }
    </div>
  )
}

export default Orders
