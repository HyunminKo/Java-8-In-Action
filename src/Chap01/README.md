## 자바 8을 눈여겨봐야 하는 이유

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
