# cache

## TODO
- [x] 캐시 엔트리별로 ttl 적용하기
- [X] 캐시 엔트리별로 ttl 적용하기 테스트 작성 
- [ ] redis로 여러 서버에서 공유할 수 있는 캐시 만들기

## 학습
### Spring AOP 기반 캐시 도입하기
#### 기본적인 적용방법 
- 설정파일에 @EnableCaching을 붙인다. 필요하다면 Cache Manager Bean을 추가한다. 
- 캐시하고자 하는 메서드에 @Cacheable을 붙인다.
- 특정 메서드 실행시점에 캐시를 만료시켜야한다면 해당 메서드에 @CacheEvict를 붙인다.
- 필요하다면 spring expression으로 cache key를 지정해준다. 만일 지정하지 않으면 메서드의 모든 파라미터를 key로 본다.

#### 주의사항 
- 하나의 클래스 내에서 캐시가 적용된 메서드를 호출하는경우 캐시가 적용되지 않는다. 
  - 스프링에서 제공하는 Proxy객체를 경유하지 않기 때문이다.
- private, protected 메서드에는 캐시가 적용되지 않는다.
- non-static public methods 만 advice 적용 가능하다.
- Spring Bean에만 적용가능하다.

#### 대안
- AspectJ를 사용해서 combination with load-time/compile-time weaving 을 도입해보자. 
- https://www.baeldung.com/aspectj

#### 참고링크
  - https://spring.io/guides/gs/caching/
  - https://www.baeldung.com/spring-cache-tutorial
  - https://mangchhe.github.io/springboot/2021/09/15/SpringBootCache/
  - https://medium.com/upday-devs/3-common-mistakes-when-implementing-spring-cache-abstraction-a7ac2ee247ba
  - https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-introduction-proxies
  - https://www.baeldung.com/java-dynamic-proxies
  - https://stackoverflow.com/questions/55964758/how-to-advise-static-methods-using-spring-aop



### SpringExtension 중 SpyBean 사용법
- 객체의 행동을 그대로 유지하면서 mock의 역할도 수행한다. MockBean은 객체의 행동도 지정해주어야 한다.
- https://www.concretepage.com/spring-5/spybean-example-spring-test
