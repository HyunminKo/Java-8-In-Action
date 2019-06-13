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

- 자바 1.0에서는 스레드와 락, 메모리 모델을 ㅣㅈ원했지만 로우 레벨 기능을 온전히 이용하기엔 힘듦.

- 자바 5에서는 스레드 풀, 병렬 실행 컬렉션, 자바 7에서는 포크/조인 프레임워크를 제공했지만 여전히 개발자가 활용하기는 쉽지 않음.

### 3. 자바 8의 새로운 기능

- 스트림 API

- 메서드에 코드를 전달하는 기법

- 인터페이스의 디폴트 메서드

스트림을 이용하면 에러를 자주 일으키며 멀티코어 CPU를 이용하는 것보다 비용이 훨씬 비싼 키워드 synchronized를 사용하지 않아도 된다.
