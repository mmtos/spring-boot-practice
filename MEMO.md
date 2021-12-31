* 참고 URL https://spring.io/guides/gs/spring-boot/

* CommandLineRunner 빈
  - 해당 빈은 spring boot 실행시 같이 실행된다.
  - main은 boot application 실행하는 역할만 하고 기존의 main에 작성할 내용들은 여기에 작성하면 될 것같음. 

* @SpringBootTest
  - whole application context to be created.
  - @AutoConfigureMockMvc 를 같이 사용할 경우 Mockup된 서블릿 컨테이너(MockMvc)를 inject 받을 수 있음.
  - 실제 서블릿 컨테이너를 구동 후 테스트 하기 위해서는 webEnvironment값을 변경해 줘야 합니다.
    - @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    - 실제 서블릿 컨테이너는 MockMvc 객체를 통해서가 아닌 TestRestTemplate 객체와 같은 다른 방식으로 테스트를 해줘야 합니다.
    - 출처 : https://engkimbs.tistory.com/768

* MockMvc
  - a set of convenient builder classes, send HTTP requests into the DispatcherServlet and make assertions about the result.
  - @AutoConfigureMockMvc and @SpringBootTest가 적혀있어야 inject 됨
  - 실제 서블릿 컨테이너가 아닌 mock-up임

* @WebMvcTest
  - create only the web layers of the context (@SpringBootTest와 비교)

* application.yml 자동 완성
  - IntelliJ Plugin : Spring Assistance 설치 
  - application.properties 에서 application.yml로 확장자 변경

* spring boot (> 2.4 ) profile 설정
  - https://backtony.github.io/spring/2021-08-21-spring-start-8/#2-%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B6%80%ED%8A%B8-240-%EC%9D%B4%ED%9B%84%EC%9D%98-profile-%EC%84%A4%EC%A0%95