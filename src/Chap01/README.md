# 자바 8을 눈여겨봐야 하는 이유

## Introduction

### 1. 간결성

```java
//기존 무게에 따라 정렬
Collections.sort(inventory, new Comparator<Apple>() {
    public int compare(Apple a1, Apple a2){
        return a1.getWeight().compareTo(a2.getWeight());
    }
});
```

```java
//java 8, 간단
inventory.sort(comparing(Apple::getWeight));
```

### 2. 자바 8에서는 병렬 실행을 새롭고 단순한 방식으로 접근

- 멀티코어 CPU 대중화와 같은 변화가 자바 8에도 많은 영향을 미침

- 자바 1.0에서는 스레드와 락, 메모리 모델을 ㅣㅈ원했지만 로우 레벨 기능을 온전히 이용하기엔 힘듦

- 자바 5에서는 스레드 풀, 병렬 실행 컬렉션, 자바 7에서는 포크/조인 프레임워크를 제공했지만 여전히 개발자가 활용하기는 쉽지 않음

### 3. 자바 8의 새로운 기능

- 스트림 API

- 메서드에 코드를 전달하는 기법

- 인터페이스의 디폴트 메서드

스트림을 이용하면 에러를 자주 일으키며 멀티코어 CPU를 이용하는 것보다 비용이 훨씬 비싼 키워드 synchronized를 사용하지 않아도 된다

### 4. 스트림 처리

- 스트림이란 한 번에 한 개씩 만들어지는 연속적인 데이터 항목들의 모임이다

- 스트림 API의 핵심은 기존에는 한 번에 한 항목을 처리했지만 작업들을 고수준으로 추상화해서 일련의 스트림으로 만들어 처리할 수 있다는 것

- 또한 파이프라인을 이용해서 입력 부분을 멀티 CPU 코어에 쉽게 할당 할 수 있다는 점

### 5. 동작 파라미터화(Be havior parameterization)로 메서드에 코드 전달하기

- sort에 파라미터를 제공해서 역순 정렬이나 다양한 기준에 의해서 정렬하고 싶을 때

- 2013UK0001, 2014US0002 등의 형식을 갖는다고 하고 처음 네 개의 숫자는 년도를, 다음 두 글자는 국가 코드를, 마지막 네 개의 숫자는 ID로 의미하고 순서대로 정렬한다고 한다. 자바 8 이전에는 메서드를 다른 메서드로 전달할 방법이 없었다

- 위에서 Comparator 객체를 만들어서 sort 방법을 넘겨줄수 있지만 복잡하고 기존 동작을 재활용하지 못한다

### 6. 병렬성과 공유 가변 데이터(shared mutable data)

- 병렬성을 얻기 위하여 스트림 메서드로 전달하는 코드의 동작 방식을 조금 바꿔야 한다

- 안전하게 실행되도록 하려면 공유된 가변 데이터에 접근하지 않아야 하고 이를 pure, side-effect-free, stateless 함수라고 한다

- 공유되지 않은 가변 데이터, 메서드와 함수 코드를 다른 메서드로 전달하는 두 가지 기능은 함수형 프로그래밍의 핵심적인 사항이다

## 자바 함수

- Method Reference(메서드 레퍼런스) 새로운 자바 8의 기능

디렉토리에서 모든 숨겨진 파일을 필터링 한다고 하자

```java
//기존
File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
    public boolean accept(File file) {
        return file.isHidden();
    }
})
```

File 클래스에는 이미 isHidden 메서드가 있는데 왜 또 FileFilter로 isHidden을 감싸 인스턴스화 해야할까?

```java
//java 8 version
File[] hiddenFiles = new File(".").listFiles(File::isHidden);
```

### 코드 넘겨주기 : 예제

- 사과 리스트를 받아 녹색 사과만 골라 리스트를 리턴하는 함수를 구현해보자

```java
public static List<Apple> filterGreenApples(List<Apple> inventory){
    List<Apple> result = new ArrayList<>();
    for(Apple apple : inventory){
        if("green".equals(apple.getColor())){
            result.add(apple);
        }
    }
    return result;
}
```

- 어떤 사람들은 무게로 필터링을 하고 싶을 것이다

```java
public static List<Apple> filterGreenApples(List<Apple> inventory){
    List<Apple> result = new ArrayList<>();
    for(Apple apple : inventory){
        if(apple.getWeight() > 150){
            result.add(apple);
        }
    }
    return result;
}
```

- 자바 8에서는 중복되지 않고 코드를 인수로 넘겨줄 수 있다

```java
import java.util.function.*;

public static boolean isGreenApple(Apple apple){
    return "green".equals(apple.getColor());
}
public static boolean isHeavyApple(Apple apple){
    return apple.getWeight() > 150;
}

static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple:inventory){
        if(p.test(apple)){
            result.add(apple);
        }
    }
    return result;
}

//이런식으로 호출 가능!
filterApples(inventory, Apple::isGreenApple);
filterApples(inventory, Apple::isHeavyApple);
```

### 메서드 전달에서 람다로

- 이렇게 한 두번만 사용할 때 메서드를 정의하는 것은 귀찮은 일이다

```java
filterApples(inventory, (Apple a) -> "green".equals(a.getColor()));

filterApples(inventory, (Apple a) -> a.getWeight() > 150);

// 이런식으로도 가능!
filterApples(inventory, (Apple a) -> a.getWeight() < 80 || "green".equals(a.getColor()));
```

- 더욱 간단하지만 람다식이 길어지면 익명 람다보다는 코드가 수행하는 일을 잘 설명한 이름을 가진 메서드로 정의하고 메서드 레퍼런스를 활용하는 것이 바람직

- 멀티코어 CPU가 아니었다면 자바 8 설계자들은 여기까지만 했을테다. 그렇지만 이것을 라이브러리화 했다

```java
filterApples(inventory, (Apple a) -> a.getWeight() > 150);

// -->>
static <T> Collection<T> filter(Collection<T> c, Predicate<T> p);

filter(inventory, (Apple a) -> a.getWeight() > 150);
```

- 하지만 병렬성이라는 중요성 때문에 이와 같은 설계를 포기했고 filter와 비슷한 동작을 수행하는 연산집합을 포함하는 새로운 스트림API를 제공한다.

### 스트림

- 리스트에서 고가의 트랜잭션만 필터링한 다음에 통화로 결과를 그룹화해야 한다고 가정

```java
Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>();

for(Transaction transaction : transactions) {
    if(transaction.getPrice() > 1000){
        Currency currency = transaction.getCurrency();
        List<Transaction> transactionForCurrency = transactionsByCurrencies.get(currency);
        if(transcationsForCurrency == null){
            transactionsForCurrency = new ArrayList<>();
            transactionByCurrencies.put(currency, transactionsForCurrency);
        }
        transactionsForCurrency.add(transaction);
    }
}
```

- 스트림 API로 간단하게 짤수 있다

```java
import static java.util.stream.Collectors.toList;

Map<Currency, List<Transaction>> transactionsByCurrencies =
    transaction.stream()
               .filter((Transaction t) -> t.getPrice() > 1000) // 고가의 트랜잭션 필터링
               .collect(groupingBy(Transaction::getCurrency)); // 통화로 그룹화

```

- 기존 코드에서는 for-each와 같이 수행하는 것을 외부 반복(external iteration)이라고 하고 스트림 API를 이용하면 루프를 신경 쓸 필요가 없으며 라이브러리 내부에서 처리 되므로 이를 내부 반복(internal iteration)이라고 한다.

```java
import static java.util.stream.Collectors.toList;

//순차 방식
List<Apple> heavyApples =
    inventory.stream()
             .filter((Apple a) -> a.getWeight() > 150)
             .collect(toList());

//병렬 처리 방식
List<Apple> heavyApples =
    inventory.parallelStream()
             .filter((Apple a) -> a.getWeight() > 150)
             .collect(toList());
```

### 함수형 프로그램에서 가져온 다른 유용한 아이디어

- NullPointer 예외를 피할 수 있도록 도와주는 Optional<T> 클래스를 제공

- 패턴 매칭
