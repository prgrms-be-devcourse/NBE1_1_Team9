import React from 'react'
import { Route, Routes } from 'react-router-dom'
import OrderPage from './OrderPage'
import MyOrderPage from './MyOrderPage'
import Header from '../components/header/Header'
import LoginPage from './LoginPage'
import SignupPage from './SignupPage'

const ClientPage = () => {
  return (
    <div className="container-fluid m-auto">
        <Header/>
        <Routes>
            <Route path='/' element={<OrderPage/>}/>
            <Route path='/myOrder' element={<MyOrderPage />}/>
            <Route path='/login' element={<LoginPage />}/>
            <Route path='/signup' element={<SignupPage />}/>
        </Routes>
    </div>
    
  )
}

export default ClientPage
