# 스트림 소개

- 컬렉션은 자바에서 가장 많이 사용하는 기능 중 하나이고 거의 모든 애플리케이션에서 만들고 처리하는 과정을 포함한다
- 예를 들어 채식주의자용으로 그룹화 한다든지 가장 비싼 요리를 찾는 등의 연산이 포함된다

```sql
SELECT name FROM dishes WHERE calore < 400
```

- SQL 질의에서는 어떻게 필터링할 것인지는 구현할 필요가 없다. 컬렉션으로도 이와 비슷한 기능을 만들 수 있지 않을까?
- 많은 요소를 포함하는 커다란 컬렉션에서는 어떻게 해야할까? 성능을 높이려면 멀티코어를 활용해 병렬로 컬렉션을 처리한다

## 스트림이란 무엇인가?

- 스트림은 자바 API에 새로 추가된 기능이며 선언형으로 컬렉션을 처리할 수 있다.

```java
//기존 자바 7
List<Dish> lowCaloricDishes = new ArrayList<>();
for(Dish d: menu) {
    if(d.getCalories() <400){
        lowCaloricDishes.add(d);
    }
}

Collections.sort(lowCaloricDishes, new Comparator<Dish>(){
    public int compare(Dish d1, Dish d2){
        return Integer.compare(d1.getCalories(), d2.getCalories());
    }
});

List<String> lowCaloricDishesName = new ArrayList<>();
for(Dish d: lowCaloricDishes) {
    lowCaloricDishesName.add(d.getName());
}
```

- 위 코드에서 lowCaloricDishes라는 `가비지 변수`가 사용되었다. 컨테이너 역할만 하는 중간 변수이다

```java
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
List<String> lowCaloricDishesName = menu.stream()
                                        .filter(d -> d.getCalories() <400)
                                        .sorted(comparing(Dish::getCalories))
                                        .map(Dish::getName)
                                        .collect(toList());
//stream() -> parallelStream()
```

- 스트림 API 등장으로 데이터 처리 과정을 병렬화하면서 스레드와 락을 걱정할 필요가 없다

## 스트림 시작하기

- 스트림이란 무엇인가? 데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소

  - 연속된 요소 : 특정 요소 형식으로 이루어진 연속된 값 집합의 인터페이스를 제공. 컬렉션에서는 ArrayList, LinkedList를 사용할 것인지에 대한 시간과 공간, 저장 및 접근 연산이 주를 이루지만 스트림은 filter, sorted, map처럼 표현 계산식이 주를 이룬다.

  - 소스 : 스트림은 컬렉션, 배열, I/O 자원 등의 소스로부터 데이터를 소비한다

  - 데이터 처리 연산: 데이터베이스와 비슷한 연산을 지원. filter, map, reduce, find, match, sort 등으로 데이터를 조작할수 있고 순차적 또는 병렬적으로 실행할 수 있다

## 스트림과 컬렉션

- 데이터를 `언제` 계산하느냐가 컬렉션과 스트림의 가장 큰 차이. 컬렉션은 어떤 연산을 하기전에 항상 메모리에 모든 값이 있어야한다. 스트림은 이론적으로 요청할 때만 요소를 계산하는 고정된 자료구조이다

- 반복자와 마찬가지로 스트림도 한 번만 탐색할 수 있고 탐색된 스트림의 요소는 소비된다.

```java
List<String> title = Arrays.asList("Java8","In","Action");
Stream<String> s = title.stream();
s.forEach(System.out::println); //title의 각 단어를 출력
s.forEach(System.out::println); //java.lang.IllegalStateException: 스트림이 이미 소비 되었거나 닫힘
```

- 컬렉션을 사용하면 사용자가 직접 요소를 반복해야 한다.

```java
//for-each loop
List<String> names = new ArrayList<>();
for(Dish d: menu) {
    names.add(d.getName());
}
// iterator
List<String> names = new ArrayList<>();
Iterator<String> it = menu.iterator();
while(it.hasNext())) {
    Dish d = it.next();
    names.add(d.getName());
}

// stream
List<String> names = menu.stream()
                         .map(Dish::getName)
                         .collect(toList());
```
