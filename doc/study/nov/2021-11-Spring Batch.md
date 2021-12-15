# Spring Batch



## 배치 애플리케이션?

- 단발성으로 대용량의 데이터를 처리하는 애플리케이션.

- 스프링 진영에선 Spring Batch 가 있음

<br>

### 배치 애플리케이션의 조건

- 대용량 데이터 - 대용량 데이터 처리가 가능해야함.
- 자동화 - 사용자 개입없이 실행되어야함.
- 견고성 - 잘못된 데이터를 충돌/중단 없이 처리할 수 있어야 함.
- 신뢰성 - 무엇이 잘못되었는지 추적할 수 있어야 함. (로깅, 알림)
- 성능 - 지정한 시간안에 처리를 완료하고 다른 애플리케이션을 방해하지 않도록 수행

<br>

### Batch vs Quartz

Quartz는 스케줄러의 역할, 대용량 데이터 배치 처리에 대한 기능을 지원하지 않음. 

Batch는 반대로 스케줄 기능을 지원하지 않기때문에 둘을 조합해서 사용함.

<br>
<br>

## Spring Batch 예제
### MisApplication.kt
```kotlin
@EnableBatchProcessing //배치기능 활성화
@SpringBootApplication
class MisApplication

fun main(args: Array<String>) {
    runApplication<MisApplication>(*args)
}
```
메인 클래스에 `@EnableBatchProcessing` 어노테이션을 선언하여 스프링 배치 기능을 활성화 한다.
스프링 배치가 제공하는 어노테이션으로 스프링 배치에 필요한 대부분의 스프링 빈 정의를 제공함.

<br>

### SimpleJobConfiguration.kt
```kotlin
@Configuration
class SimpleJobConfiguration (
    var jobBuilderFactory: JobBuilderFactory,
    var stepBuilderFactory: StepBuilderFactory
){

    @Bean
    fun simpleJob(): Job {

        return jobBuilderFactory.get("simpleJob").start(simpleStep2()).build()
    }

    @Bean
    fun simpleStep1(): TaskletStep {
        return stepBuilderFactory.get("simpleStep1").tasklet { contribution, chunkContext ->
            System.out.println("STEP1")
            RepeatStatus.FINISHED
        }.build()
    }

    @Bean
    fun simpleStep2(): TaskletStep {
        return stepBuilderFactory.get("simpleStep2").tasklet { contribution, chunkContext ->
            System.out.println("STEP2")
            RepeatStatus.FINISHED
        }.build()
    }
}
```
