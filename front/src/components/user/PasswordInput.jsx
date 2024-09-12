import React from 'react'

const PasswordInput = ({value, setValue}) => {
  return (
    <div class="row mb-3">
        <label for="password" class="col-sm-2 col-form-label">비밀번호</label>
        <div class="col-sm-10">
            <input 
                type="password" 
                class="form-control" 
                id="password3" 
                name='password' 
                placeholder='비밀번호를 입력하세요'
                value={value}
                onChange={(e) => setValue(e.target.value)}
            />
        </div>
    </div>
  )
}

export default PasswordInput
