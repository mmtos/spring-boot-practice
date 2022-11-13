# basic
* 튜토리얼 URL : [https://spring.io/guides/gs/spring-boot/](https://spring.io/guides/gs/spring-boot/)

## LOG
- [X] auto configuration 경험 (spring web,actuator)
- [X] CommandLineRunner 작성
- [X] test 작성
- [X] profile 설정

### CommandLineRunner Bean
    - 해당 빈은 spring boot 실행시 같이 실행된다.

### @SpringBootTest
    - whole application context to be created.
    - MockMvc로 Controller 테스트하기
        - MockMvc ? Mockup된 서블릿 컨테이너, a set of convenient builder classes, send HTTP requests into the DispatcherServlet and make assertions about the result.
        - @SpringBootTest와 @AutoConfigureMockMvc를 명시 후 Inject
    - 실제 서블릿 컨테이너로 Controller 테스트 하기
        - webEnvironment값 설정
            - @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
        - MockMvc와 달리 별도의 클라이언트 객체(TestRestTemplate)를 통해서 테스트 해야함.
    - 출처 : https://engkimbs.tistory.com/768

### @WebMvcTest
    - create only the web layers of the context (@SpringBootTest와 비교)

### application.yml 자동 완성
    - IntelliJ Plugin : Spring Assistance 설치
    - application.properties 에서 application.yml로 확장자 변경

### spring boot (> 2.4 ) profile 설정
    - https://backtony.github.io/spring/2021-08-21-spring-start-8/#2-%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-240-%EC%9D%B4%ED%9B%84%EC%9D%98-profile-%EC%84%A4%EC%A0%95
