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

- 위와 같이 람다 안에서 사용하는  외부 변수를 `람다가 포획한 변수` 라고 부름.  

- 자바와는 달리 람다에서 람다밖에 있는 파이널이 아닌 변수(var)에 접근할 수 있고 변경할 수 있음.
- 기본적으로 함수 안에 정의된 로컬 변수의 생명주기는 함수가 반환되면 끝나지만, 어떤 함수가 자신의 로컬 변수를 포획한 람다를 반환하거나 다른 변수에 저장한다면 로켤 변수와 함수의 생명주기가 달라질 수 있다.
- 파이널 변수(val)를 포획한 경우, 람다코드를 변수값과 함께 저장.
- 파이널이 아닌경우(var), 변수를 특별한 래퍼로 감싸서 나중에 변경하거나 읽을 수 있게 한 다음, 래퍼에 대한 참조를 람다코드와 함께 저장.

```kotlin
//변경 가능한 변수를 포획하는 방법을 보여주기 위한 예제
class Ref<T> (var value: T)		
val counter = Ref(0)
val inc = { counter.value++ }  //final 변수를 포획했지만 그 변수를 가리키는 객체의 값을 변경가능

//실제 코드에서는 래퍼를 만드는 대신, 변수를 직접 바꿈
var counter = 0
val inc = {counter++}
```

 

함정?



### 멤버참조

람다를 넘길 때 프로퍼티나 메소드를 단 하나만 호출하는 함수 값을 갖고있으면, 간단하게 이중콜론(::) 으로 사용할 수 있음.

```kotlin
		  // 클래스 :: 멤버
val getAge = Person::age 
->
val getAge = {person: Person -> person.age}
```



최상위 함수의 표현

```kotlin
fun salute() = println("Salute!")
run(::salute)  // 최상위 함수를 참조한다.
// 출력 : Salute!
```

- 최상위 함수란 class 나 다른 fun 안에 있는 것이 아닌 가장 밖에 있는 fun값을 의미



함수

```kotlin
val action = { person: Person, message: String ->
             sendEmail(person, message)
             }	// 이 람다는 sendEmail 함수에게 작업을 위임한다.
val nextAction = ::sendEmail	// 람다 대신 멤버 참조를 쓸 수 있다.
```



생성자

```kotlin
data class Person(val name: String, val age:Int)
>>> val createPerson = ::Person //"Person"의 인스턴스를 만드는 동작을 값으로 저장
>>> val p = createPerson("Alice", 29)
>>> println(p)
Person(name=Alice, age=29)

```



 

### 컬렉션 함수형 API

함수형 프로그래밍 스타일을 사용하면 컬렉션을 다룰때 편리



#### filter ,map

```kotlin
// filter
>>> val list = listOf(1,2,3,4)
>>> println(list.filter {it % 2 == 0) //각 원소별로 넘겨서 true로 반환하는 원소만 모음
[2, 4]         
                         
// map                         
>>> println(list.map {it * it})
[1, 4, 9, 16]                         
```



#### all, any, find, count

```kotlin
>>> val canBeInClub27 = { p: SamplePerson -> p.age <= 27}
>>> val people = listOf(SamplePerson("ncucu", 27), SamplePerson("ncucu.me", 20))

>>> println(people.all(canBeInClub27)) //모든 원소가 해당 조건에 일치하는지 확인
false

>>> println(people.any(canBeInClub27)) //원소 중 해당조건에 하나라도 일치하는게 있는지 확인
true

>>> println(people.find(canBeInClub27)) //조건을 만족하는 첫번째 원소를 반환.
person("ncucu", 27)

>>> println(people.find(canBeInClub27)) //조건을 만족하는 첫번째 원소를 반환.
person("ncucu", 27)

>>> println(people.count(canBeInClub27)) //조건에 만족하는 원소의 갯수.
1
```



#### count와 size의 차이

```kotlin
>>> println(people.count(canBeInClub27)) 		  //조건에 만족하는 원소의 갯수만 추적
>>> println(people.filter(canBeInClub27).size())  //조건을 만족하는 모든 원소가 포함된 컬렉션생성 
```

count는 원소의 갯수만 추적하므로 size보다 효율적



#### groupBy

컬렉션의 모든 원소를 특성에 따라 여러 그룹으로 나눌때 사용.

```kotlin
>>> val people = listOf(SamplePerson("A", 27), SamplePerson("B", 20), SamplePerson("C", 20))
>>> val groupBy = people.groupBy { it.age } //age를 key, SamplePerson list를 value로  
>>> println(groupBy)
{27=[SamplePerson(name=A, age=27)], 20=[SamplePerson(name=B, age=20), SamplePerson(name=C, age=20)]}
```

 

#### flatMap, flatten 

```kotlin
>>> val strings = listOf("abc", "def")
>>> println(strings.flatMap { it.toList() })
[a, b, c, d, e, f]
```

위 코드는 다음의 두 단계를 수행

1. it.toList()를 이용하여 해당 원소로 이루어진 리스트가 만들어진다. => list("abc"), list("def")
2. flatMap이  모든 원소로 이루어진 단일 리스트를 반환한다. => list("a", "b", "c", "d", "e", "f")



```kotlin
>>> val deepList = listOf(listOf(1), listOf(2, 3), listOf(4, 5, 6))
println(deepList.flatten()) //컬렉션의 모든 컬렉션에서 모든 요소를 단일목록으로 반환
[1, 2, 3, 4, 5, 6]
```

flatten() 함수를 사용하면 컬렉션안의 컬렉션 요소들을 풀어서 하나의 단일 목록으로 반환한다.



차이점을 좀더 찾아보기



### 지연 계산(lazy) 컬렉션 연산

```kotlin
people.map(Person::name).filter{ it.startsWith("A")} //map과 filter 각각 리스트를 반환
```

- filter와 map이 연쇄 호출 되면서 List를 각각 하나씩 만든다. 요소가 많아질수록 비효율적임



```kotlin
people.asSequence()					//원본 컬렉션을 시퀀스로 반환
	.map(Person::name)					// 중간연산
    .filter { it.startWith("A") }		// 중간연산
    .toList()						// 최종 연산
```

- 시퀀스 (Sequence) 를 사용하면 중간 임시 컬렉션을 사용하지 않고 컬렉션 연산을 연쇄실행함. 

   - Sequence 와 Collection 의 차이?

     



#### 시퀀스 연산 실행 - 중간 연산과 최종 연산

```kotlin
        [        중간 연산         ] [ 최종연산 ]
sequence.map { ... }.filter { ... }.toList()
```

- Collection 이라면 모든 원소에 대해 map을 한 후 filter를 수행하지만
- Sequence 의 경우 첫번째 원소에 대해 map, filter 까지 수행 한 후 다음 원소에 대한 처리를 시작함.(지연계산)

- map보다 filter를 먼저 실행시켜 전체 횟수를 줄이는게 좋음



#### 자바 함수형 인터페이스

#### 자바 메소드에 람다를 인자로 전달

#### SAM 생성자





### 수신 객체 지정 람다 : with, apply

코틀린은 자바에는 없는 **수신 객체 지정 람다**라는 것을 가지고 있음. 말그대로 람다 함수를 쓸 때 내가 자주 쓰고 싶은 객체를 미리 지정해서 사용하는 람다이다.



#### with

```kotlin
fun alphabet() : String {
    val stringBuilder = StringBuilder()
    return with(StringBuilder) { 	 
    	for (letter in 'A'..'Z'){	
            this.append(letter)
        }
        append("\nNow I know the alphabet!")
        this.toString()
    }
}

//위의 식을 리팩토링 하면 아래와 같음
fun alphabert() = with(StringBuilder()){
  
       	for (letter in 'A'..'Z'){	
            this.append(letter)
        }
        append("\nNow I know the alphabet!")
        this.toString() 
}

```

- 첫 번째 인자로 받은 객체(`StringBuilder`) 를 두번째 인자가 받는 수신 객체로 만듦. 두번째 객체로 받은 람다함수의 `this`가 곧 첫번째 인자로 수신받은 객체(`StringBuilder`) 이다.

-  with가 반환하는 값은 람다 코드의 실행 결과이며 람다식 본문의 마지막 값 (수신받은 객체를 반환 X)



#### apply

```kotlin
fun alphabert2() = StringBuilder().apply{

    for (letter in 'A'..'Z'){
         append(letter)
    }
    append("\nNow I know the alphabet!")
}
```

- with와 거의 비슷? 
- apply는 자신에게 수신된 객체를 반환 (이리저리 조작을 가한 객체 자체를 반환)

 