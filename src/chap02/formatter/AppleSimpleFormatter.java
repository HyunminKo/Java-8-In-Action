package chap02.formatter;

import chap02.Apple;

public class AppleSimpleFormatter implements AppleFormatter{

    @Override
    public String accept(Apple a) {
        return String.format("An apple of %sg",a.getWeight());
    }
}
