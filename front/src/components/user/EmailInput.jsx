import React from 'react'

const EmailInput = ({value, setValue}) => {
  return (
    <div class="row mb-3">
        <label for="email" class="col-sm-2 col-form-label">아이디</label>
        <div class="col-sm-10">
            <input 
                type="email" 
                class="form-control" 
                id="email" 
                name='email'
                placeholder='아이디(이메일)을 입력하세요.'
                value={value}
                onChange={(e) => setValue(e.target.value)}
            />
        </div>
    </div>
  )
}

export default EmailInput
