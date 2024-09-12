
## 파일 구조
```
src
├─ App.css
├─ App.jsx
├─ App.test.js
├─ axios
│  └─ BasicAxios.js
├─ components
│  ├─ Product.jsx
│  ├─ ProductList.jsx
│  ├─ Summary.jsx
│  ├─ SummaryItem.jsx
│  ├─ header
│  │  └─ Header.jsx
│  ├─ myorder
│  │  ├─ EmailForm.jsx
│  │  ├─ Item.jsx
│  │  ├─ Order.jsx
│  │  ├─ Orders.jsx
│  │  └─ OrdersContiner.jsx
│  └─ user
│     ├─ EmailInput.jsx
│     ├─ NameInput.jsx
│     ├─ NonUserDiv.jsx
│     ├─ PasswordInput.jsx
│     ├─ UserContainer.jsx
│     └─ UserDiv.jsx
├─ context
│  └─ UserContext.jsx
├─ index.css
├─ index.js
├─ logo.svg
├─ page
│  ├─ ClientPage.jsx
│  ├─ LoginPage.jsx
│  ├─ MyOrderPage.jsx
│  ├─ OrderPage.jsx
│  └─ SignupPage.jsx
├─ reportWebVitals.js
└─ setupTests.js

```

## 사용한 라이브러리
- react router dom
- styled components


## 특징
1. 페이지 컴포넌트와 페이지를 구성하는 태그 컴포넌트 분리
2. env 파일을 통한 서버 경로 노출 방지