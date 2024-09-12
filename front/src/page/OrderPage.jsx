import React, { useEffect, useState } from 'react'
import { ProductList } from '../components/ProductList';
import { Summary } from '../components/Summary';
import axios from '../axios/BasicAxios';
import { replace, useNavigate } from 'react-router-dom';

const OrderPage = () => {

    const navigate = useNavigate();
    const [products, setProducts] = useState([
        {id: 'uuid-1', name: '콜롬비아 커피 1', category: '커피빈', price: 5000},
        {id: 'uuid-2', name: '콜롬비아 커피 2', category: '커피빈', price: 5000},
        {id: 'uuid-3', name: '콜롬비아 커피 3', category: '커피빈', price: 5000},
      ]);
      const [items, setItems] = useState([]);
      const [isFetching, setFetching] = useState(false);
      const [page, setPage] = useState(1);
      const handleAddClicked = productId => {
        const product = products.find(v => v.id === productId);
        const found = items.find(v => v.id === productId);
        const updatedItems =
          found ? items.map(v => (v.id === productId) ? {...v, count: v.count + 1} : v) : [...items, {
            ...product,
            count: 1
          }]
        setItems(updatedItems);
      }
      
      useEffect(() => {
        console.log(process.env.REACT_APP_SERVER_IP);
        axios.get(`/products?page=${page}`)
          .then(res => {
            if(res.status !== 200) {
                throw res.data;
            }
            return res.data;
        })
        .then(({data}) => {
            console.log(data.content);
            setProducts(data.content);
        })
          .catch(err => {
            alert('데이터 목록 조회 실패');
            console.log(err);
        });
      }, [page]);
    
      const handleOrderSubmit = (order) => {
        console.log(items);
        if (items.length === 0) {
          alert("아이템을 추가해 주세요!");
        } else {
            if(isFetching) {
                alert('잠시만 기다려주세요.');
                return;
            }
            setFetching(true);
          axios.post('/orders', {
            email: order.email,
            address: order.address,
            postcode: order.postcode,
            orderProductsQuantity: items.map(v => ({
              productId: v.id,
              quantity: v.count
            }))
          }).then(
            v => {
                alert("주문이 정상적으로 접수되었습니다.")
                window.location.reload();
            },
            e => {
              alert("서버 장애");
              console.error(e);
            })
            .finally( 
                setFetching(false)
            );
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
          <Summary items={items} onOrderSubmit={handleOrderSubmit} reset={() => setItems([])}/>
        </div>
      </div>
    </div>
  </>
  )
}

export default OrderPage
