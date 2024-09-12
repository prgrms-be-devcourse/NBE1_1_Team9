import React from 'react'
import { Route, Routes } from 'react-router-dom'
import OrderPage from './OrderPage'
import MyOrderPage from './MyOrderPage'
import Header from '../components/header/Header'

const ClientPage = () => {
  return (
    <div className="container-fluid m-auto">
        <Header/>
        <Routes>
            <Route path='/' element={<OrderPage/>}/>
            <Route path='/myOrder' element={<MyOrderPage />}/>
        </Routes>
    </div>
    
  )
}

export default ClientPage
