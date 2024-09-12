import "./App.css"
import 'bootstrap/dist/css/bootstrap.css';
import React, {useEffect, useState} from 'react';
import ClientPage from './page/ClientPage';
import { BrowserRouter } from "react-router-dom";
import { UserProvider } from "./context/UserContext";

function App() {

  return (
    <UserProvider>
      <BrowserRouter >
        <ClientPage />
      </BrowserRouter>
    </UserProvider>
    
  );
}

export default App;
