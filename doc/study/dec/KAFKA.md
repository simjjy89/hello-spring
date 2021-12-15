

# KAFKA 

## 카프카는 무엇?



## 카프카를 통해 할 수 있는 일?



## 왜 카프카를 사용하는가?





- source Application과 Target Application 갯수가 적으면 data를 전송 하는 라인도 단순
- source Application과 Target Application 갯수가 많아질 수록 data를 전송하는 라인도 복잡해짐
  - 배포 및 유지보수에 어려움
- 그러한 복잡성을 해결하기위해 링크드인에서 만들었고 현재는 오픈소스로 제공
- Source Application과 Target Application의 결합도를 약하게 함
  - Source Application 에서 Kafka로 데이터를 전송하고 Target Application에서 Kafka의 데이터를 가져옴
  - Topic