# NBE1_1_Team9

### 기술 스택

- Java, Spring Boot
- JavaScript, React, BootStrap, Styled Components
- H2, Spring Data JPA, QueryDsl, Redis
- Spring REST Docs, Junit5, Thymeleaf
- Spring Security, JWT
- Jmeter

### 코딩 컨벤션

패키지 구조

```
api 
 ㄴcontroller
  ㄴorder
   ㄴrequest
    ㄴOrderCreateRequest.java
   ㄴOrderController.java
  ㄴproduct
 ㄴservice
  ㄴorder
   ㄴrequest
    ㄴOrderCreateServiceReqeuest.java
   ㄴresponse
	ㄴOrderResponse.java
   ㄴOrderServiceImpl.java
   ㄴOrderService.java
  ㄴproduct
config
domain
 ㄴorder
  ㄴOrder.java
  ㄴOrderRepository.java
 ㄴproduct
global
```

API 패키지

- Service와 Controller에 관한 부분을 작성한다.
    - Controller 패키지
        - ReqeustDto를 정의하여 사용한다. OOORequest.java
    - Service 패키지
        - Interface를 이용해 기능을 설계하고 구현체에서 사용한다.
        - Controller의 request를 그대로 사용하지 않도록 한다. OOOServiceRequest.java로 재정의 하여 사용한다. (패키지간 의존성을 덜어주기 위함)
        - ResponseDto를 정의하여 사용한다. OOOresponse.java

domain 패키지

- Entity와 Repository에 관한 부분을 작성한다.
    - 각 도메인에서 사용되는 Enum이나 일급컬렉션은 사용되는 엔티티패키지 하위에 작성한다.

config 패키지

- 설정 파일을 모두 따로 빼서 관리하도록 한다.
- config 파일은 모두 네이밍을 OOOCofing.java로 하도록 한다.

global 패키지

- 전역적으로 사용할 객체들에 대해서는 global 패키지 하위에서 관리한다.

코드 컨벤션

```
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
    
9. 이중적인 의미를 가지는 단어는 지양한다.
    - event, design ...
    
10. 의도가 드러난다면 되도록 짧은 이름을 선택한다.
    - retreiveUser() (X) / getUser() (O)
    - 단, 축약형을 선택하는 경우는 개발자의 의도가 명백히 전달되는 경우이다. 명백히 전달이 안된다면 축약형보다 서술형이 더 좋다.
    
11. LocalDateTime -> xxxAt, LocalDate -> xxxDt로 네이밍

12. 객체를 조회하는 함수는 JPA Repository에서 findXxx 형식의 네이밍 쿼리메소드를 사용하므로 개발자가 작성하는 Service단에서는 되도록이면 getXxx를 사용하자.

13. 생성자를 지양하고 Builder패턴을 이용하여 생성한다.
```

### ERD

![스크린샷 2024-09-12 오전 9.47.19.png](https://github.com/user-attachments/assets/e606fb53-c4f5-4a94-a6df-36946216949b)

Order-OrderProduct 양방향 관계 설정

- 주문은 주문 목록과 부모 자식 관계를 맺는다고 생각하여 양방향 관계로 지정하였다.

Product-OrderProduct 단방향 관계 설정

- 상품과 주문상품에 대해서는 주문 상품은 상품을 알아야 하지만 상품은 주문 상품에 대해 알 필요가 없다고 여겨져서 단방향 관계로 설정하였다.

--- 

### Git 브랜치 전략

![image.png](https://github.com/user-attachments/assets/79a8f45f-8edc-425c-a691-aa78bb342344)

## 공통 타입

- `feat` : 새로운 기능 구현
- `mod` : 코드 및 내부 파일 수정
- `add` : feat 이외의 부수적인 코드, 파일, 라이브러리 추가
- `del` : 불필요한 코드나 파일 삭제
- `fix` : 버그 및 오류 해결
- `ui` :  UI 관련 작업
- `chore` : 버전 코드, 패키지 구조, 함수 및 변수명 변경 등의 작은 작업
- `hotfix` : 배포된 버전에 이슈 발생 시, 긴급하게 수정 작업
- `rename` : 파일이나 폴더명 수정
- `docs` : README나 Wiki 등의 문서 작업
- `refactor` : 코드 리팩토링
- `merge` : 서로 다른 브랜치 간의 병합
- `comment` : 필요한 주석 추가 및 변경

## Issue

```kotlin
[type] 화면이름 / 작업내용

ex)
[feat] home / 규칙 뷰 구현
```

### 템플릿

```markdown
## 💡 ISSUE
<!-- 어떤 이슈인지 간략하게 설명해주세요. -->

## 📌 TO DO
<!-- 상세하게 task를 나눠서 작성해주세요. -->
- [ ] task1
- [ ] task2
- [ ] task3
```

- Assignees : 자기 자신
- Labels : 작업 유형, 자기 자신
- Projects : 프로젝트 선택 후 상태 설정
- Milestone : 해당 milestone 선택

- feat : 새로운 기능 구현
- mod : 코드 및 내부 파일 수정
- add : feat 이외의 부수적인 코드, 파일, 라이브러리 추가
- del : 불필요한 코드나 파일 삭제
- fix : 버그 및 오류 해결
- ui : UI 관련 작업
- chore : 버전 코드, 패키지 구조, 함수 및 변수명 변경 등의 작은 작업
- hotfix : 배포된 버전에 이슈 발생 시, 긴급하게 수정 작업
- rename : 파일이나 폴더명 수정
- docs : README나 Wiki 등의 문서 작업
- refactor : 코드 리팩토링
- merge : 서로 다른 브랜치 간의 병합
- comment : 필요한 주석 추가 및 변경

## Branch

```
feature/{type}-{작업 내용}

ex)
feature/feat-main-view
feature/add-font-res
```

## Commit Message

```
[type] 제목(작업 내용)  

본문 (한줄로 설명 가능한 경우 본문은 생략) 
	
ex) 
[feat] ~~~한 기능 구현 
[feat] 로그인 서버 연동 
[mod] 회원가입 로직 변경 
[del] 불필요한 import 제거 
[chore] MainActivity 코드 정렬 적용 
[fix] 회원가입 버튼 활성화 로직 버그 수정 
```

## PR

```kotlin
[type] 화면이름 / 작업내용

ex)
[feat] home / 규칙 뷰 구현
```

### 템플릿

```markdown

## 개요
<!---- 자신이 완료한 이슈를 닫아주세요 -->
- closed #이슈
<!---- 변경 사항 및 관련 이슈에 대해 간단하게 작성해주세요. 어떻게보다 무엇을 왜 수정했는지 설명해주세요. -->

<!---- Resolves: #(Isuue Number) -->

## PR 유형
어떤 변경 사항이 있나요?

- [ ] 새로운 기능 추가
- [ ] 버그 수정
- [ ] CSS 등 사용자 UI 디자인 변경
- [ ] 코드에 영향을 주지 않는 변경사항(오타 수정, 탭 사이즈 변경, 변수명 변경)
- [ ] 코드 리팩토링
- [ ] 주석 추가 및 수정
- [ ] 문서 수정
- [ ] 테스트 추가, 테스트 리팩토링
- [ ] 빌드 부분 혹은 패키지 매니저 수정
- [ ] 파일 혹은 폴더명 수정
- [ ] 파일 혹은 폴더 삭제

## PR Checklist
PR이 다음 요구 사항을 충족하는지 확인하세요.

- [ ] 커밋 메시지 컨벤션에 맞게 작성했습니다.
- [ ] 변경 사항에 대한 테스트를 했습니다.(버그 수정/기능에 대한 테스트).

📣 **To Reviewers**
---
<!-- 전달사항 -->
```

- Reviewers : AnTaeho, rinklove, Anyeon00, jmd5314, Na Minhyeok
- Labels : 작업 유형, 자기 자신
- Projects : 프로젝트 선택 후 상태 설정
- Milestone : 해당 milestone 선택
- Development : 해당 이슈 연결 → closed 뒤에 #{이슈 번호} 붙이면 PR 머지할 때 이슈가 자동으로 닫힘.

### Related Issue

### 개요

- 변경 사항 및 관련 이슈에 대해 간단하게 작성해주세요.
- 어떻게 보다 무엇을 왜 수정했는지 설명해주세요.

### PR 유형

- 자신이 올린 PR이 무슨 내용의 코드인지 다른이들이 확인하기 쉽게 체크리스트를 통해 전달합니다.

### PR Checklist

- 커밋 컨벤션을 지켰는지 확인합니다.

### To Reviewers

- 리뷰 시 중점적으로 봐야하는 부분

---
## 트러블 슈팅

### Spring Security 적용시 Controller 테스트 실행 문제 해결

  [문제 해결 커밋](https://github.com/prgrms-be-devcourse/NBE1_1_Team9/pull/8/commits/695be94fab762f681256c5ecefcfa800676dd7fd)

  1. 테스트시 MockBean UserService, JwtService 생성

  2. security Test 의존성 추가

  3. `@WithMockUser` 를 통해 security를 통과할 수 있는 가짜 유저 생성

  4. mockMvc에 csrf문제를 해결 할 수 있도록 with(csrf()) 추가

### REST Docs 도입

  [REST-Docs를-시작으로-개발해보자](https://velog.io/@naminhyeok/REST-Docs를-시작으로-개발해보자)

### 테스트에서 PK 값을 테스트하는 방법

  [테스트에서-Transactional을-이용한-롤백](https://velog.io/@naminhyeok/테스트에서-Transactional을-이용한-롤백)

  autoincrement id 값에 대한 테스트는 getter를 이용해 꺼내서 확인하자

### 공통응답 vs ResponseEntity

  [공통응답 활용법](https://velog.io/@naminhyeok/공통응답-객체-vs-ResponseEntity)

### 연관관계 편의 메서드 vs Cascade

  [연관관계-편의-메서드-그리고-Cascade](https://velog.io/@naminhyeok/연관관계-편의-메서드-그리고-Cascade)

  Cascade를 최대한 지양하자 (정말 완벽한 부모-자식 구조라고 생각이 든다면 사용 OK)

  연관관계 편의 메서드를 이용하자(사용하지 않으면 자식에게는 DB에 저장이 안된다)

### count(*) from 테이블 vs 커버링 인덱스

  [어떤 쿼리가 성능이 더 좋을까?](https://velog.io/@naminhyeok/count-from-table-vs-커버링-인덱스)

  어떤 코드가 더 성능이 좋은지 확인

    ```java
       	@Override
        public Page<Product> findAllUsingQueryDsl(Pageable pageable) {
            List<Product> products = queryFactory
                .selectFrom(product)
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    
            long count = queryFactory
                .select(product.id)
                .from(product)
                .fetch().size();
    
            return new PageImpl<>(products, pageable, count);
        }
    
        @Override
        public Page<Product> findAllUsingQueryDsl2(Pageable pageable) {
            List<Product> products = queryFactory
                .selectFrom(product)
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    
            JPAQuery<Long> count = queryFactory.select(product.count())
                .from(product);
    
            return PageableExecutionUtils.getPage(products, pageable, count::fetchOne);
        }
    ```

  **결론**

  전자는 컬렉션에서 size를 통해 데이터의 갯수를 세기 위해 컬렉션에 담기 때문에 더 느리고 작업을 많이 수행하지 못한다. 후자로 코딩할 수 있도록 하자

### RefreshToken 저장 장소를 RDBMS에서 Redis로 이동
    - refreshToken이 User 테이블의 컬럼으로 존재
    - accessToken이 1시간마다 만료되기 때문에 최악의 경우 각 유저는 1시간 마다 조회와 업데이트 쿼리 발생
    - refreshToken은 자주 변경되기 때문에 RDBMS의 특징과 어울리지 않는다

  ### 해결 과정

    - RefreshToken를 MySQL에서 Redis에 저장하기로 결정
    - email : refreshToken 형식으로 저장
        - PK값을 저장해 둘 수도 있지만, 토큰 생성에 email을 담기 때문에 email 값을 저장하기로 선택

  ### 결과

    - Redis로 refreshToken의 저장 위치를 바꿔서 기존 MySQL의 부화 감소
    - key-value 방식으로 저장되기 때문에 O(1)의 시간복잡도를 가져 성능 향상
    - refreshToken의 경우 저장후 바로 사용되지 않기 때문에 이벤트를 활용해 비동기적으로 처리
### fetch join으로 N+1 문제 해결

  ### 문제 상황

  OrderProduct에서 Order에 대해 지연로딩이 적용되어있을 경우
  Order에서 OrderProduct를 가져와야 하는 로직에서
  Order에서 OrderProduct를 가져올때마다 select 쿼리가 나가게 된다 -> **N+1 문제** 발생

  → 패치 조인으로 일일히 쿼리가 나가지 않고 한꺼번에 Join해서 가져오도록 한다.

  패치 조인 적용 전 이메일로 조회 쿼리

    ```java
    Hibernate: 
        select
            o1_0.order_id,
            o1_0.address,
            o1_0.postcode,
            o1_0.created_at,
            o1_0.email,
            o1_0.order_status,
            o1_0.updated_at 
        from
            orders o1_0 
        where
            o1_0.email=?
    Hibernate: 
        select
            op1_0.order_id,
            op1_0.seq,
            op1_0.created_at,
            op1_0.product_id,
            op1_0.quantity,
            op1_0.updated_at 
        from
            order_items op1_0 
        where
            op1_0.order_id=?
    Hibernate: 
        select
            p1_0.product_id,
            p1_0.category,
            p1_0.created_at,
            p1_0.description,
            p1_0.product_name,
            p1_0.price,
            p1_0.updated_at 
        from
            products p1_0 
        where
            p1_0.product_id=?
    Hibernate: 
        select
            p1_0.product_id,
            p1_0.category,
            p1_0.created_at,
            p1_0.description,
            p1_0.product_name,
            p1_0.price,
            p1_0.updated_at 
        from
            products p1_0 
        where
            p1_0.product_id=?
    ```

  → fetch join 적용

  ![화면 캡처 2024-09-12 103818.png](https://github.com/user-attachments/assets/001c3bc4-6e4e-4a06-8075-c8558f5e8204)

  적용 후 이메일로 조회 쿼리

    ```java
    select
            o1_0.order_id,
            o1_0.address,
            o1_0.postcode,
            o1_0.created_at,
            o1_0.email,
            op1_0.order_id,
            op1_0.seq,
            op1_0.created_at,
            op1_0.product_id,
            p1_0.product_id,
            p1_0.category,
            p1_0.created_at,
            p1_0.description,
            p1_0.product_name,
            p1_0.price,
            p1_0.updated_at,
            op1_0.quantity,
            op1_0.updated_at,
            o1_0.order_status,
            o1_0.updated_at 
        from
            orders o1_0 
        join
            order_items op1_0 
                on o1_0.order_id=op1_0.order_id 
        join
            products p1_0 
                on p1_0.product_id=op1_0.product_id 
        where
            o1_0.email=?
    ```

### 컨트롤러의 파라미터에서 전달받은 데이터를 DTO객체로 바인딩 시 발생하는 에러 해결

  ### 문제상황

  > form으로 상품등록 API 요청시, 상품등록DTO의 필드 값이 null이라는 검증 에러 메시지가 응답됨
  >

  ### 원인

  > 컨트롤러에서 @ModelAttribute를 통해 데이터를 객체로 바인딩할 때,  객체의 setter가 사용됨
  >

  ### 해결

  > DTO클래스에 @Setter 추가
>
### 타임리프 form 태그로 PUT, DELETE Api요청시 발생하는 에러 해결

  ### 문제상황

  > HTML Form태그에서 PUT과 DELETE 메서드로 상품 등록과 상품 삭제 API 요청시, 콘솔 창에 허용 되지 않은 메서드라는 에러 메시지가 출력됨
  >

  ### 원인

  > HTML Form에서 API요청시, GET과 POST 메서드만 사용 가능
  >

  [REST - HTML Form에서 GET/POST만 지원하는 이유](https://haah.kr/2017/05/23/rest-http-method-in-html-form/)

  ### 해결

  > 1.  Hidden Input 태그를 통해 PUT과 DELETE 메서드 사용
    2. **HiddenHttpMethodFilter 설정**
  >

    ```html
    <!-- Thymeleafe 사용시, 다음 코드를 통해 hidden input 태그를 자동 추가 -->
    <form action="#" th:action="/board/api" method="#" th:method="delete">
      ...
    </form>
    ```

    ```html
    # application.properties
    spring.mvc.hiddenmethod.filter.enabled=true
    ```

  [html form 태그에서 PUT, DELETE 사용](https://velog.io/@krafftdj/html-form-태그에서-PUT-DELETE-사용)

---

## 개인 작업 목록

[민혁 PR](https://github.com/prgrms-be-devcourse/NBE1_1_Team9/pulls?q=is:pr+is:closed+label:민혁)

- QueryDsl 적용
- Pagination 적용

[준호 PR](https://github.com/prgrms-be-devcourse/NBE1_1_Team9/pulls?q=is:pr+is:closed+label:준호)

- React를 통한 프론트엔드 개발

[태호 PR](https://github.com/prgrms-be-devcourse/NBE1_1_Team9/pulls?q=is:pr+is:closed+label:태호)

- 스프링 시큐리티 적용
- JWT 토큰 발급 및 검증

[형석 PR](https://github.com/prgrms-be-devcourse/NBE1_1_Team9/pulls?q=is:pr+is:closed+label:형석)

- 타임리프를 이용한 관리자 페이지

[믿음 PR](https://github.com/prgrms-be-devcourse/NBE1_1_Team9/pulls?q=is:pr+is:closed+label:믿음)

- QueryDsl을 이용한 N+1 문제 해결

---

## API 명세서

```bash
// build process
./gradlew clean build

// start jar
cd build/libs/
java -jar cafe-0.0.1-SNAPSHOT.jar

// 브라우저를 통해 확인 할 수 있다.
http://localhost:8080/docs/index.html
```

![스크린샷 2024-09-12 오전 10.24.52.png](https://github.com/user-attachments/assets/1acd06b8-8a6f-4b85-96b2-0619b98be042)

---

## 테스트

총 테스트 항목 82개

![스크린샷 2024-09-12 오전 10.16.23.png](https://github.com/user-attachments/assets/8befce0d-c723-4fca-9480-247b50c21bb1)



### 테스트 커버리지

- 도메인 91% 라인 커버
    - Order 100% 커버
    - Product 100% 커버
    - OrderProduct 100% 커버
- 서비스 83% 라인 커버
- 컨트롤러 50% 라인 커버

![스크린샷 2024-09-12 오전 10.18.14.png](https://github.com/user-attachments/assets/99b7eeaf-e64f-4416-ab1d-09d7466735ec)
