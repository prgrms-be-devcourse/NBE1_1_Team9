import React, { useEffect, useState } from 'react'
import { ProductList } from '../components/ProductList';
import { Summary } from '../components/Summary';
import axios from '../axios/BasicAxios';

const OrderPage = () => {
    const [products, setProducts] = useState([
        {productId: 'uuid-1', productName: '콜롬비아 커피 1', category: '커피빈', price: 5000},
        {productId: 'uuid-2', productName: '콜롬비아 커피 2', category: '커피빈', price: 5000},
        {productId: 'uuid-3', productName: '콜롬비아 커피 3', category: '커피빈', price: 5000},
      ]);
      const [items, setItems] = useState([]);
      const handleAddClicked = productId => {
        const product = products.find(v => v.productId === productId);
        const found = items.find(v => v.productId === productId);
        const updatedItems =
          found ? items.map(v => (v.productId === productId) ? {...v, count: v.count + 1} : v) : [...items, {
            ...product,
            count: 1
          }]
        setItems(updatedItems);
      }
      
      useEffect(() => {
        console.log(process.env.REACT_APP_SERVER_IP);
        axios.get('/products')
          .then(v => setProducts(v.data));
      }, []);
    
      const handleOrderSubmit = (order) => {
        if (items.length === 0) {
          alert("아이템을 추가해 주세요!");
        } else {
          axios.post('/orders', {
            email: order.email,
            address: order.address,
            postcode: order.postcode,
            orderItems: items.map(v => ({
              productId: v.productId,
              category: v.category,
              price: v.price,
              quantity: v.count
            }))
          }).then(
            v => alert("주문이 정상적으로 접수되었습니다."),
            e => {
              alert("서버 장애");
              console.error(e);
            })
        }
      }

  return (
    <>
    <div className="card">
      <div className="row">
        <div className="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
          <ProductList products={products} onAddClick={handleAddClicked}/>
        </div>
        <div className="col-md-4 summary p-4">
          <Summary items={items} onOrderSubmit={handleOrderSubmit}/>
        </div>
      </div>
    </div>
  </>
  )
}

export default OrderPage
