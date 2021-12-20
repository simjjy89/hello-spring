

# KAFKA 

## 카프카는 무엇인가

- Linked In에서 오픈소스로 개발된 분산 메시징 시스템.
- 대용량 실시간 로그처리에 특화되어있음.



## 카프카 특징

### Publish / Subscribe 구조

<img src="img\pub_sub.png" alt="pub_sub" style="zoom:67%;" />

- source Application과 Target Application 갯수가 적으면 data를 전송 하는 라인도 단순
- source Application과 Target Application 갯수가 많아질 수록 data를 전송하는 라인도 복잡해짐
  - 배포 및 유지보수에 어려움 
- 발신자(Publish)는 수신자가 누구든 상관없이 메세지를 보내기만 하고 수신자(Subscribe)는 발신자가 누군지 상관없이 필요한 메세지를 수신함.
- 카프카에서는 publish를 프로듀서(producer)라 하고, Subscribe를 컨슈머(Consumer)라 한다.
  - Kafka 내부에는 넘어온 데이터를 담는 Topic이라는 일종의 Queue가 존재
    - Topic에 데이터를 담는 역할은 Producer가, 데이터를 가져가는 역할은 Consumer가 함

- Point to Point 구조가 아닌 Pub/Sub 구조를 채택함으로써, 전송 라인이 단순해짐

  



### 멀티 프로듀서, 멀티 컨슈머 

<img src="img\multi_produce_consumer.png" alt="multi_produce_consumer" style="zoom:67%;" />

- 하나의 Topic에 여러 Producer가 메세지를 보낼 수 있음. 
- 하나의 Consumer가 여러 Topic을 구독할 수 있음.



### 고가용성

- 고가용성으로 서버에 갑자기 이슈가 생겼을 때, 데이터 손실없이 복구 할 수 있음.



### 낮은 지연, 높은 처리량

- 낮은 지연(Latency)과 높은 처리량(Throughput)을 통해서 데이터를 효과적으로 처리할 수 있음. 빅데이터 처리에 용이





## Topic 과 Partition

### Topic 이란

- 카프카에서 데이터를 주고 받는 공간.
- 토픽은 데이터베이스의 테이블이나 파일시스템의 폴더와 유사한 성질을 가지고 있음.

- 토픽은 목적에 따라 이름을 갖을 수 있음. 추후 유지보수에 용이.  ex) click_log, send_sms, location_log



### Partition 이란

### <img src="img\topic2.PNG" alt="topic2" style="zoom: 60%;" />

- 저장소(Topic) 에서 분리되어진 공간. 
- 파티션이 많을 수록 Consumer에게 데이터를 빨리 전달 할 수 있음.



### Topic 내부

### <img src="img\topic3.PNG" alt="topic3" style="zoom: 67%;" />

- 하나의 토픽은 여러개의 파티션으로 구성. 첫번째 파티션은 0번부터 생성

- 카프카 Consumer는 가장 오래된 데이터부터 가져감.  데이터를 가져가도 파티션 내부의 데이터는 삭제되지 않음.

- 새로운 컨슈머가 붙었을 경우 파티션의 0번 데이터 부터 다시 가져감.

  - 컨슈머 그룹이 달라야 함.
  - auto.offset.reset = earliest 여야 함.

  - 동일한 데이터에 대해 두번 처리 가능
    - 카프카를 사용하는 중요한 이유    

- 파티션이 1개 추가된 경우

  - 데이터를 보낼 때 key를 지정하여 어느 파티션에 넣을지 정할 수 있음.
  - key를 지정하지 않는 경우, round-robin으로 파티션이 지정됨.
  - key를 지정하는 경우, key값의 해시값을 구하고 특정 파티션에 할당되게 됨.

- 파티션을 늘리는건 가능하지만 줄일 수 없기때문에 신중해야함.
- 파티션 갯수를 늘리면 컨슈머 갯수를 늘려 데이터 처리를 분산시킬 수 있음.
- 옵션으로 지정한 최대 시간과 용량에 따라  파티션에 적재되어있는 데이터의 삭제 시기를 정할 수 있음.

 



### Partitional 이란

<img src="img\partitional1.PNG" alt="partitional1" style="zoom:50%;" />

- 프로듀서가 데이터를 보낼 때 무조건 파티셔너를 통해서 전송하게 됨. 파티셔너는 데이터를 토픽의 어떤 파티션에 넣을지 정하는 역할을 함.

- 레코드에 포함된 메시지 키 또는 값에 따라 파티션의 위치가 달라짐.
  - 동일한 메시지 키를 가진 레코드는 동일한 해쉬값을 만들어내기때문에 항상 동일한 파티션으로 들어갈 수 있음.
    - 순서를 지켜서 데이터를 처리하게 할 수 있는 장점이 있음. ex) 서울의 온도를 측정한 데이터
  - 메시지 키가 없는 데이터들은 round-robin 방식으로 적절하게 분배됨.
- Partitional 인터페이스를 사용하여 커스텀 파티셔너를 만들 수 있음.
  - 메세지키, 메세지값, 또는 토픽 이름에 따라 어느 파티션에 데이터를 보낼지 정할 수 있음.
  - 특정 데이터의 처리량을 조절하고 싶은 경우 사용   ex) vip 고객의 데이터



 



## 카프카 핵심요소 3가지

### Broker

- 카프카가 설치되어있는 서버 단위
- 보통 3개이상 브로커 설치하여 사용



### Replication

- partition의 복제
- partition이 1인 경우
  - replication이 1일 경우 : 원본 partition 1개만 존재
  - replication이 2일 경우 : 원본 partition 1개, 복제본 replication 1개 존재
  - replication이 3일 경우 : 원본 partition 1개, 복제본 replication 2개 존재
- replication의 갯수는 broker 갯수보다 커질 수 없음!

- 원본 partiton을 Leader partition, 복제본 partition을 Follower partition이라고 함. 
  - Leader partition과 Follower partition을 묶어 ISR(In Sync Replica) 라고 함.
  - 

- partition의 고가용성을 위해 사용됨.
  - 원본 partition(Leader partition)이 훼손되더라도 Follower partition이 존재하므로 복제본으로 복구가 가능함. 그리고 Leader partition의 역할을 승계함

- producer가 topic의 partition에 데이터를 전달 할 때, 데이터를 전달받는 주체가 Leader partition임
- producer에는 ack라는 상세 옵션이 있음.
  - partition의 replication과 관련이 있음
  - ack = 0 일경우 : Leader partition에 데이터를 전달하고 응답값은 받지않음
    - 데이터가 잘 전송되었는지, 나머지 replica에도 잘 전송되었는지 보장할 수 없음.
    - 속도는 빠르지만 데이터 유실 가능성이 있음!
  - ack = 1 일 : Leader partition에 데이터를 전달하고 응답값을 받음
    - Leader partition에 데이터 전달 유무는 알 수 있지만, 나머지 replica에 잘 전송되었는지 보장이 안됨.
    - 데이터 유실 가능성이 있음
  - ack = all :  Leader partition에 데이터를 전달하고, 나머지 partition에도 데이터가 잘 전송되었는지 확인 절차를 거침
    - 데이터 유실의 가능성은 없음.
    - 확인절차가 많기때문에 속도가 현저히 느림.

- replication을 무턱대고 만들 수 없는 이유
  - replication이 많아질수록 resource도 많이 잡아먹음.
    - retention date (저장시간)을 잘 생각해서 replication 갯수를 정하는 것이 좋음
  - 3개이상의 broker를 사용 할 때, replication은 3으로 설정하는 것이 좋음.



### ISR(In Sync Replica)

- 





- - 



## 카프카 Lag

- 카프카 운영에 있어 중요한 모니터링 지표 중 하나



### Consumer lag

- 프로듀서가 데이터를 넣는 속도가 컨슈머가 데이터를 읽는 속도보다 클 경우, 두 offset 간의 차이가 발생. 이를 Consumer lag이라고 함. 
- consumer lag을 통해 pipeline으로 연결된 프로듀서와 컨슈머 간의 상태를 짐작 할 수 있음.
- 파티션이 여러개 있으면 1세트의 producer와 consumer에서도 Lag이 여러개 존재 할 수 있음.
  - 그중 높은 숫자의 lag을 records-lag-max 라고 부름.

 

 

 