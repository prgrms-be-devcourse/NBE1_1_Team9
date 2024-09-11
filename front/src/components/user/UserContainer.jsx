import React, { useEffect, useState } from 'react'
import UserDiv from './UserDiv';
import NonUserDiv from './NonUserDiv';

const UserContainer = () => {
    const loginUser = localStorage.getItem('userId');

  return (
    <div>
      {loginUser ? <UserDiv/> : <NonUserDiv/>}
    </div>
  )
}

export default UserContainer
