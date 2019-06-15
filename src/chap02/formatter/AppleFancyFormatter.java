package chap02.formatter;

import chap02.Apple;

public class AppleFancyFormatter implements AppleFormatter {
    @Override
    public String accept(Apple a) {
        String characteristic = a.getWeight() > 150 ? "heavy" : "light";
        return String.format("A %s %s apple",characteristic,a.getColor());
    }
}
