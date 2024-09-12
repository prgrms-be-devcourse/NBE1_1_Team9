import React from 'react'

const NameInput = ({value, setValue}) => {
  return (
    <div class="row mb-3">
        <label for="email" class="col-sm-2 col-form-label">이름</label>
        <div class="col-sm-10">
            <input 
                type="text" 
                class="form-control" 
                id="name" 
                name='name'
                placeholder='이름을 입력하세요.'
                value={value}
                onChange={(e) => setValue(e.target.value)}
            />
        </div>
    </div>
  )
}

export default NameInput
