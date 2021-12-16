

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
  - Kafka 내부에는 넘어온 데이터를 담는 Topic이라는 일종의 Queue가 존재
    - Topic에 데이터를 담는 역할은 Producer가, 데이터를 가져가는 역할은 Consumer가 함



- 아주 유연한 Queue의 역할을 함
- 고가용성으로 서버에 갑자기 이슈가 생겼을 때, 데이터 손실없이 복구 할 수 있음.
- 낮은 지연(Latency)과 높은 처리량(Throughput)을 통해서 데이터를 효과적으로 처리할 수 있음. 빅데이터 처리에 용이



## 토픽이란?

- 토픽은 데이터베이스의 테이블이나 파일시스템의 폴더와 유사한 성질을 가지고 있음.

- 토픽은 목적에 따라 이름을 갖을 수 있음. 추후 유지보수에 용이.  ex) click_log, send_sms, location_log



### 토픽 내부

- 하나의 토픽은 여러개의 파티션으로 구성. 첫번째 파티션은 0번부터 생성
- 카프카 Consumer는 가장 오래된 데이터부터 가져감. 