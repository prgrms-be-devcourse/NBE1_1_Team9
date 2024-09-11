import "./App.css"
import 'bootstrap/dist/css/bootstrap.css';
import React, {useEffect, useState} from 'react';
import ClientPage from './page/ClientPage';
import { BrowserRouter } from "react-router-dom";

function App() {

  return (
    <BrowserRouter >
      <ClientPage />
    </BrowserRouter>
  );
}

export default App;
