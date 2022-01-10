## 람다로 프로그래밍



### 람다식란?

- 익명함수를 지칭하는 용어. 자바에는 8버전부터 도입이 되었음

- 고차함수에 인자로 전달되거나 고차함수가 돌려주는 결과값으로 쓰임



### 람다식의 장점

- 코드의 간결성 

- 함수를 만드는 과정이 없이 한번에 처리할 수 있어서 생산력이 높아짐
- 



### 람다식의 단점

- 모든 원소를 전부 순회하는 경우 일반적인 방법보다 느림
- 디버깅이 어려움



### 람다 식과 멤버 참조

이벤트가 발생했을 때 특정 핸들러를 실행하거나 데이터 구조의 모든 원소에 특정 연산을 적용해야 되는 경우가 생김

자바에서는 무명 클래스를 사용하였지만 번거로움

```java
/* JAVA에서 무명 내부 클래스로 리스너 구현 */
button.setOnClickListener(new OnClickListener(){
   
    @Override
    public void onClick(View view){
        
        /*클릭시 수행 동작*/
        
    }
    
});

```



람다를 사용 할 경우, 이를 더 간결하게 표현 할 수 있다.

``` kotlin
button.setOnClickListener{ /*클릭 시 수행 할 동작 */ }
```





### 람다와 컬렉션

예전 자바에는 컬렉션 라이브러리가 적어 개발자들이 필요한 컬렉션 기능을 직접 작성하고 하였음. 

``` kotlin
/*컬렉션을 직접 검색하기 */
fun findTheOldest(people:List<Person>){
	var maxAge = 0  //가장 많은 나이를 저장.
	var theOldest : Person? = null //가장 연장자인 사람을 저장.
	
	for (Person in people){  
		if (person.age > maxAge){   //최연장자를 발견하면 최댓값을 바꿈
			maxAge = person.age
			theOldest = person
		}
	}
    
    printLn(theOldest)
    
}

>>> val people = listOf(Person("Alice",29), Person("Bob",31))
>>> findTheOldest (people)
Person(name=Bob, age=31)
```



하지만 람다를 이용한 라이브러리 함수를 사용하면 간결하게 해결 할 수 있다.

```kotlin
>>> val people = listOf(Person("Alice",29), Person("Bob",31))
>>> println(people.maxBy { it.age }) //모든 컬렉션에 대해 maxBy함수를 실행, {}안에 비교에 사용될 값을 돌려줌 
Person(name=Bob, age=31)
```

 

### 람다식 문법

```kotlin
/* 람다 식을 변수에 저장하는 예제 */
>>> val sum = { x: Int, y: Int -> x + y }
>>> println(sum(1, 2))
3
```

- 코틀린 람다식은 항상 중괄호로 둘러싸여있음
-  -> 로 파라미터와 본문을 구분
- 람다식을 변수에 저장 할 수 있음



인자값과 타입을 모두 명시하면 어떤일이 벌어지는지 명확히 알수있지만 번잡해지므로

간결하게 생략 할 수 있음

```kotlin
people.maxBy ({ person: Person -> person.age}) 

people.maxBy ({ person -> age}) // 자료형 제거. 컴파일러가 추론

people.maxBy ({ it -> it.age}) // 파라미터가 한개인 경우 it으로 대체

people.maxBy ({it.age}) //it 파라미터 생략

people.maxBy {it.age} //괄호 제거. 람다식이 함수의 마지막 인자인 경우 괄호 밖으로 뺄 수 있음

```

 

인자가 여러개인 경우에도 람다가 마지막 인자일 경우 괄호에서 바깥으로 뺄 수 있다.

```kotlin
// 이름 붙인 인자를 사용해 람다 넘기기
val names = people.joinToString (separator = " ", transform = { p: Person -> p.name })
 
// 람다를 괄호 밖에 전달 하기
val names = people.joinToString (" ") { p: Person -> p.name } 
```



람다를 변수에 저장 할 때는 파라미터를 추론 할 문맥이 존재하지 않으므로 파라미터 타입을 명시해야함.

```kotlin
val getAge = {p.Person -> p.age}  // O

val getAge = {it -> it.age } //  X 타입 추론 불가능함
```





### 현재 영역에 있는 변수에 접근

람다를 함수안에 정의하면 함수의 파라미터뿐만 아니라 람다정의의 앞에 선언된 로컬 변수까지 람다에서 모두 사용 할 수 있음

```kotlin
//함수 파라미터를 람다 안에서 사용하기

fun printMessageWithPrefix(messages: Collection<String>, prefix: String){
    messages.foreEach{ //각 원소에 대해 수행 할 작업을 람다로 받음
        
        printLn("$prefix $it") //람다 안에서 함수의 "prefix" 파라미터를 사용 할 수 있음
        
    }
}
```



파라미터를 사용할 수 있을 뿐만 아니라 람다 바깥에 있는 로컬 변수를 람다안에서 변경 할 수 있다

```kotlin

fun printProblemCounts (response: Collection<String>){
    var clientErrors = 0
    var serverErrors = 0
    responses.forEach {
        if (it.startWidth("4")){
            clientErros++	//람다 안에서 람다 밖의 변수를 변경함			
        } else if (it.startWidth("5")){
            serverErrors++
        }
    }
}
```

위와 같이 람다 안에서 사용하는  외부 변수를 `람다가 포획한 변수` 라고 부름.

