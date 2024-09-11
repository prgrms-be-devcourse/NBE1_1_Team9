## 필요기능

- 주문 하기 (상)
- 주문 발송 처리 (상) - 스케줄러 + bulkUpdate
- 상품 등록 (중)
- 상품 수정 (중)
- 상품 단건 조회 (중)
- 상품 삭제 (중)
- 개인의 주문 단건 조회
- 내 주문 조회 → email 식별
- 개인의 주문 수정

## 네이밍 규칙

1. 기본적으로 네이밍은 **누구나 알 수 있는 쉬운 단어**를 선택한다.
    - 우리는 외국인이 아니다. 쓸데없이 어려운 고급 어휘를 피한다.
2. 변수는 CamelCase를 기본으로 한다.
    - private String secondName = "William"
3. URL, 파일명 등은 kebab-case를 사용한다.
    - /user-email-page ...
4. 패키지명은 단어가 달라지더라도 무조건 소문자를 사용한다.
    - frontend, useremail ...
5. ENUM이나 상수는 대문자로 네이밍한다.
    - public String NAME = "June" ...
6. 함수명은 소문자로 시작하고 **동사**로 네이밍한다.
    - getUserId(), isNormal() ...
7. 클래스명은 **명사**로 작성하고 UpperCamelCase를 사용한다.
    - UserEmail, Address ...
8. 객체 이름을 함수 이름에 중복해서 넣지 않는다. (= 상위 이름을 하위 이름에 중복시키지 않는다.)
    - line.getLength() (O) / line.getLineLength() (X)
9. 컬렉션은 복수형을 사용하거나 컬렉션을 명시해준다.
    - List ids, Map<User, Int> userToIdMap ...
10. 이중적인 의미를 가지는 단어는 지양한다.
    - event, design ...
11. 의도가 드러난다면 되도록 짧은 이름을 선택한다.
    - retreiveUser() (X) / getUser() (O)
    - 단, 축약형을 선택하는 경우는 개발자의 의도가 명백히 전달되는 경우이다. 명백히 전달이 안된다면 축약형보다 서술형이 더 좋다.
12. 함수의 부수효과를 설명한다.
    - 함수는 한가지 동작만 수행하는 것이 좋지만, 때에 따라 부수 효과를 일으킬 수도 있다.
        
        ```kotlin
        fun getOrder() {
         if (order == null) {
             order = Order()
         }
        return order
        }
        ```
        
    - 위 함수는 단순히 order만 가져오는 것이 아니라, 없으면 생성해서 리턴한다.
    - 그러므로 getOrder() (X) / getOrCreateOrder() (O)
13. LocalDateTime -> xxxAt, LocalDate -> xxxDt로 네이밍
14. 객체를 조회하는 함수는 JPA Repository에서 findXxx 형식의 네이밍 쿼리메소드를 사용하므로 개발자가 작성하는 Service단에서는 되도록이면 getXxx를 사용하자.

## 추가 기능

- 재고 관리 (동시성)
***
![스크린샷 2024-09-06 오전 11 39 51](https://github.com/user-attachments/assets/f0f53edf-a462-4cca-b71e-b3475c312857)


