import React, { useState, useEffect } from 'react';
import Item from './Item';
import styled from 'styled-components';

const TotalPriceDiv = styled.div`
    font-size: 1.3em;
`;

const Order = ({ data }) => {
    const status = data.orderStatus === 'ORDERED' ? '주문 완료' : '배송 진행 중';
    const [showItems, setShowItems] = useState(false);
    const [totalPrice, setTotalPrice] = useState(0);

    useEffect(() => {
        // 총 가격 계산
        if (data?.orderDetails) {
            const newTotalPrice = data.orderDetails.reduce((sum, per) => sum + per.price * per.quantity, 0);
            setTotalPrice(newTotalPrice);
        }
    }, [data?.orderDetails]); // orderDetails가 변경될 때마다 총 가격 계산

    return (
        <div className='m-4'>
            <div>
                <h4>현재 상태: {status}</h4>
                <span><strong>이메일: </strong>{data?.email}</span><br/>
                <span><strong>주소: </strong>{data?.address}</span><br/>
                <span><strong>우편번호: </strong>{data?.postcode}</span>
            </div>
            <button type="button" className="btn btn-success" onClick={() => setShowItems(!showItems)}>
                {
                    showItems ? '아이템 목록 닫기' : '아이템 목록 자세히 보기'
                }
            </button>
            {
                showItems && data?.orderDetails.map((per, index) => (
                    <Item key={index} item={per} />
                ))
            }
            {showItems && <TotalPriceDiv><strong>합계: </strong>{totalPrice} 원</TotalPriceDiv>}
        </div>
    );
}

export default Order;
