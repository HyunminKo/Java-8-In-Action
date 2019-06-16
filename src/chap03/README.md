# 람다 표현식

- 익명 클래스로 다양한 동작을 구현할 수 있었지만 코드가 깔끔하지는 않았다
- 람다 표현식은 익명 클래스처럼 이름이 없는 함수면서 메서드를 인수로 전달할 수 있다
- 자바 8 API에 추가된 중요한 인터페이스와 형식 추론 등의 기능, 새로운 기능인 메서드 레퍼런스를 설명

## 람다란

- 람다(lambda)라는 용어는 람다 미적분학 학계에서 개발한 시스템에서 유래했다
- 코드가 간결하고 유연해진다

```java
Comparator<Apple> byWeight = new Comparator<Apple>() {
    public int compare(Apple a1, Apple a2) {
        return a1.getWeight().compareTo(a2.getWeight());
    }
};

// -->

Comparator<Apple> byWeight = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
```

### 함수형 인터페이스

- 오직 하나의 추상 메서드만 가지고 있는 인터페이스
- 람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있으므로 전체 표현식을 함수형 인터페이스의 인스턴스로 취급할 수 있다

```java
Runnable r1 = () -> System.out.println("Hello World 1"); // 람다 사용

Runnable r2 = new Runnable() { // 익명 클래스 사용
    public void run() {
        System.out.println("Hello World 2");
    }
}

public static void process(Runnable r) {
    r.run();
}

process(r1);
process(r2);
process(() -> System.out.println("Hello World 3")); // 직접 전달된 람다 표현식
```

### 함수 디스크립터

- 함수형 인터페이스의 추상 메서드 시그니처는 람다 표현식의 시그니처를 가리킨다
- 람다 표현식의 시그니처를 서술하는 메서드를 함수 디스크립터(function descripter)라고 부른다
- `()-> void` 라는 표현은 파라미터 리스트가 없고 void를 리턴하는 함수를 의미한다
- `(Apple, Apple) -> int` 는 두 개의 Apple을 인수로 받아 int를 리턴하는 함수

### 람다 활용

- 실행 어라운드 패턴(execute around pattern)

```java
// Original
public static String processFile() throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
        return br.readLine();
    }
}

// -->
public interface BufferedReaderProcessor{
    String process(BufferedReader b) throws IOException;
}
public static String processFile(BufferedReaderProcessor p) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
        return p.process(br);
    }
}
String oneLine = processFile((BufferedReader br) -> br.readLine());
String twoLine = processFile((BufferedReader br) -> br.readLine()+br.readLine());
```

## 함수형 인터페이스 사용

- java.util.function 패키지로 여러가지 함수형 인터페이스 제공

### Predicate

- java.util.function.Predicate<T> 인터페이스는 test라는 추상 메서드를 정의
- test는 제네릭 형식 T의 객체를 인수로 받고 boolean을 반환한다

```java
public static <T> List<T> filter(List<T> list, Predicate<T> p){
    List<T> results = new ArrayList<>();
    for(T s : list) {
        if(p.test(s)){
            results.add(s);
        }
    }
    return results;
}

Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
```

### Consumer

- java.util.function.Consumer<T> 인터페이스는 제네릭 형식 T를 인수로 받아 void를 리턴하는 accept라는 추상 메서드를 정의

```java
public static <T> void forEach(List<T> list, Consumer<T> c){
    for(T i : list){
        c.accept(i);
    }
}

forEach(Arrays.asList(1,2,3,4,5),
        (Integer i) -> System.out.println(i));
```

### Function

- java.util.function.Function<T, R> 인터페이스는 제네릭 형식 T을 인수로 받아 제네릭 형식 R 객체를 반환하는 apply라는 추상 메서드를 정의

```java
public static <T, R> List<R> map(List<T> list, Function<T, R> f){
    List<R> result = new ArrayList<>();
    for(T s: list){
        result.add(f.apply(s));
    }
    return result;
}

List<Integer> l = map(
    Arrays.asList("lambdas","in","action"),
    (String s) -> s.lenght()
);
```

### 기본형 특화

```java
List<Integer> list = new ArrayList<>();
for(int i = 300; i <400 ; i++){
    list.add(i);
}
```

- int가 Integer로 박싱됨
- 변환 과정에서 비용이 소모 된다. 박싱된 값은 기본형을 감싸는 래퍼로 힙에 저장되고 메모리를 더 소비하여 기본형을 가져올 때도 메모리를 탐색하는 과정이 필요하다

```java
IntPredicate evenNumbers = (int i) -> i % 2 == 0; // 박싱 없음
Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 1; // 박싱 있음
```
