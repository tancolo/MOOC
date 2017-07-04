package com.example;

import rx.Observable;

public class TestRxjava {
    public static void main(String ... args) {
        System.out.print("Hello World! \n");

        Observable.just(1,2,3,4)
                .map(Object::toString)
                .subscribe(System.out::print);
    }
}
