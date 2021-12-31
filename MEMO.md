* 참고 URL https://spring.io/guides/gs/spring-boot/

* CommandLineRunner 빈
  - 해당 빈은 spring boot 실행시 같이 실행된다.
  - main은 boot application 실행하는 역할만 하고 기존의 main에 작성할 내용들은 여기에 작성하면 될 것같음. 

* @SpringBootTest
  - whole application context to be created.
  - @WebMvcTest를 대신 사용할 경우  create only the web layers of the context by using @WebMvcTest.
  - @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) : 랜덤 포트를 사용
  
* MockMvc
  - a set of convenient builder classes, send HTTP requests into the DispatcherServlet and make assertions about the result.
  - @AutoConfigureMockMvc and @SpringBootTest가 적혀있어야 inject 됨

* application.yml 자동 완성
  - IntelliJ Plugin : Spring Assistance 설치 
  - application.properties 에서 application.yml로 확장자 변경

