# Spring & Spring Boot

<br>

## Spring 이란?
### Spring 핵심 철학
- 스프링은 JAVA 기반의 FrameWork
- 객체지향 프로그래밍이 제공하는 폭넓은 혜택을 누릴 수 있도록, 기본으로 돌아가 오브젝트에 관심을 갖는것을 목표

### Spring 사용 이유
- 단순화된 단위 테스팅
- 복잡한 코드의 감소
- 아키텍처의 유연성

<br>
 
## Spring 구조
### Spring 주요 모듈 
- 레이어 별로 크게 `Core Container`,`AOP`,`Web`,`Data Acess`, `Test` 로 나눌 수 있다.
![img.png](img/springRuntime.png)

#### Core Container
스프링 Core Container 에는 4가지 모듈이 있다.
1. Bean & Core
   - `IoC / DI` 기능의 지원을 담당
   - `Core`는 다른 스프링 모듈에서 필요로 하는 공통 기능을 갖고있는 핵심 모듈. 주요 어노테이션, 컨버터, 상수, 유틸리티 클래스등을 제공.
   - `Beans`는 스프링 DI 기능 기능의 핵심인 Bean Factory와  DI 기능을 제공 하는 모듈
2. Context
   - Beans의 기능을 구현받아 사용하는 Beans의 확장 버전. 해당 모듈을 가장 많이 쓰며, Spring 개발의 필수.
3. spEL
   - 객체 그래프를 조회하고 조작하는 기능을 제공
   > 표현식 <BR>
   > '#{"표현식"}' : "표현식"을 실행함 <BR> <BR>
   > 프로퍼티 참조 <BR>
   > ${"프로퍼티"} : "프로퍼티"를 참조할 때 사용.

   
#### AOP
1. AOP
   - AOP를 구현하기 위한 메소드 인터셉터 및 포인트 컷을 정의
2. Aspect
3. Instrument
4. Test

#### WEB
#### Data Access
#### Test

<br>
<br>

## Spring 핵심 3요소
### IoC(Inversion of Control)
#### IoC란?
- IoC란 기존 사용자가 모든 작업을 제어하던 것을 컨테이너에게 위임하여 객체의 생성부터 생명주기 등 모든 객체에 대한 제어권이 넘어간 것을 IoC, 제어의 역전이라고 함. 
이런한 제어권을 위임받은 컨테이너가 `IoC 컨테이너`


- 일반적인 의존성에 대한 제어권 : 개발자가 직접 의존성을 만듬
- 의존성이란 어떤 객체가 다른 객체를 사용하여 두 객체간의 연결을 의미함. 직접 new를 써서 객체를 생성하는 경우를 직접 의존성을 만든다고 함.
```java
public class OwnerController {
private OwnerRepository ownerRepository = new OwnerRepository();  //변수 선언과 객체 생성을 본인이 함
}
```
---
- 제어권 역전 : 직접 의존성을 만들지 않고 외부에서 의존성을 가져옴. 
- 즉, 밖에서 나에게 의존성을 주입해 주는 것을 DI(Dependency Injection)이라고 함.
```java
class OwnerController {

    private OwnerRepository repo;  //변수 선언만 함

    public OwnerController(OwnerRepository repo) {  //외부에서 객체를 주입받음
    this.repo = repo;
}

```

<br>

### AOP
#### AOP(Aspect-Oriented Programming) 란?
![img.png](img/AOP.png)
- 관점 지향 프로그래밍의 줄임말로, OOP로 독립적으로 분리하기 어려운 부가기능을 모듈화 하는 방식. 
분리한 부가기능을 `Aspect` 라는 모듈 형태로 만들어서 설계하고 개발하는 방법임. 
- 중복을 줄여서 적은 코드 수정으로 전체 변경을 할 수 있게 하자는 목적에서 출발

<br>

#### AOP  및 장점
- 중복되는 코드를 제거
- 효율적인 유지보수
- 변화 수용이 용이
- 재활용성 극대화

<br>

#### AOP 용어 정리
- 타겟(Target)
  - 핵심 기능을 담고 있는 모듈로 타겟은 부가기능을 부여할 대상(객체)이 된다.
- 어드바이스(Advice)
  - 어드바이스는 타겟에 제공할 부가기능을 담고 있는 모듈이다.
- 조인포인트(Join Point)
  - 어드바이스가 적용될 수 있는 위치를 말한다. 타겟 객체가 구현한 인터페이스의 모든 메서드는 조인 포인트가 된다.
- 포인트 컷(Pointcut)
  - 어드바이스를 적용할 타겟의 메서드를 선별하는 정규표현식이다. `JoinPoint`가 `Pointcut`에 일치할 때마다 해당 `PointCut`에 관련된 `Advice`가 실행됨.
- 애스펙트(Aspect)
  - 부가기능을 정의한 코드인 `Advice`와 Advice를 어디에 적용할지를 결정하는 `PointCut`을 합친 개념.
  - 여러객체에 공통적으로 적용되는 관심사항.
  - 싱글톤 형태의 객체로 존재한다. 
- 위빙(Weaving)
  - `Aspect`를 `Target` 객체에 연결시켜 관점지향 객체로 만드는 과정을 의미함. `Advice`를 비즈니스 로지기 코드에 삽입하는 것을 의미.
  
<br>

#### Spring AOP 동작 원리
- Spring의 AOP는 기본적으로 프록시(Proxy) 방식으로 동작하도록 되어있음.
- 스프링은 Aspect의 적용 대상(target)이 되는 객체에 대한 Proxy를 만들어 제공.
- 대상객체(Target)이 되는 객체를 사용하느 코드는 대상객체(Target)를 Proxy를 통해서 간접적으로 접근.
- Proxy는 공통기능(Advice)를 실행한 뒤 대상객체(Target)의 실제 메서드를 호출하거나 대상객체(Target)의 실제 메소드가 호출된 뒤 공통기능(Advice)을 실행


> ![img.png](img/proxy.png)
> Proxy Pattern?
> - 프록시 패턴이란 의미 그대로 실제 기능을 수행하는 주체(RealSubject)를 바로 호출하는 대신 대리자(Proxy)를 거쳐서 호출하는 것.
> - 클라이언트 -> 실제 기능을 담당하는 객체가 아닌, 클라이언트 -> 프록시 객체 -> 실제 기능을 담당하는 객체의 흐름으로 진행이 됨.
> - 실제 기능을 수행하기전에 전처리를 작업을 할 수 있음.
> - 실제 객체를 수정하지 않고 추가적인 기능을 삽입 할 수 있음.

<br>

#### 구현 방법

###### TimeTraceAop.java (@Around 적용)
```java
@Aspect //해당 클래스가 Aspect를 나타내는 클래스라는 것을 명시
@Component //@Component 을 사용하던가 SpringConfig에 @Bean으로 등록하던가 하면됨
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") // 부가기능을 적용할 Target의 패턴
    public Object excecute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START : "+joinPoint.toString());
        try{
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : "+joinPoint.toString()+" " + timeMs + "ms");

        }

    }
}
```
> ###### 로그
> ![img.png](img.png)
 
- @Around : 타겟 메서드를 감싸서 특정 Advice를 실행 한다는 의미. 
- @Before : 타겟 메서드가 실행되기 전에 Advice를 실행.
- @After : 타겟 메서드가 실행된 후에 Advice를 실행.
- @AfterReturning : 타겟 메서드가 정상적으로 끝났을 경우 Advice를 실행
- @AfterThrowing : 타겟 메서드에서 throwing이 발생했을때 Advice 실행
 