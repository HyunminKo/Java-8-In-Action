# 동작 파라미터화 코드 전달하기

- 동작 파라미터화(behavior parameterization)을 이용하면 자주 바뀌는 요구사항에 효과적으로 대응할 수 있다

- 동작 파라미터화란 아직은 어떻게 실행할 것인지 결정하지 않은 코드 블록을 의미

### 변화하는 요구사항에 대응하기

1. 첫 번째 시도: 녹색 사과 필터링

```java
public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
        if("grren".equals(apple.getColor())){ //녹색 사과만 선택
            result.add(apple);
        }
    }
    return result;
}
```

그런데 농부가 변심하여 빨간 사과도 필터링 하고 싶어졌다.

2. 두 번째 시도: 색을 파라미터화

```java
public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
        if(apple.getColor().equals(color)){
            result.add(apple);
        }
    }
    return result;
}
```
