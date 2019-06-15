package chap02;

import chap02.formatter.AppleFancyFormatter;
import chap02.formatter.AppleFormatter;
import chap02.formatter.AppleSimpleFormatter;
import chap02.predicate.AppleGreenColorPredicate;
import chap02.predicate.AppleHeavyWeightPredicate;
import chap02.predicate.ApplePredicate;
import chap02.predicate.AppleRedAndHeavyPredicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteringApples {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80,"green"),
                new Apple(155,"green"),
                new Apple(120,"red"));

        List<Apple> heavyApples = filterApples(inventory, new AppleHeavyWeightPredicate());
        System.out.println(heavyApples);

        List<Apple> greenApples = filterApples(inventory, new AppleGreenColorPredicate());
        System.out.println(greenApples);

        List<Apple> redAndHeavyApples = filterApples(inventory, new AppleRedAndHeavyPredicate());
        System.out.println(redAndHeavyApples);

        prettyPrintApple(inventory,new AppleFancyFormatter());
        prettyPrintApple(inventory,new AppleSimpleFormatter());
    }
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p){
        List<Apple> result = new ArrayList<>();
        for(Apple apple: inventory){
            if(p.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }
    public static void prettyPrintApple(List<Apple> inventory, AppleFormatter formatter){
        for(Apple apple: inventory){
            System.out.println(formatter.accept(apple));
        }
    }
}
