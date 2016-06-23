package pl.datasets.widgets;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * @author Lukasz
 * @since 23.06.2016.
 */
public class Test {
    public void fo2() {

    }

    public void foo() {
        Observable.range(1, 100).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return String.valueOf(integer)
                        .concat(integer % 15 == 0 ? "FizzBuzz"
                                : integer % 3 == 0 ? "Fizz"
                                : integer % 5 == 0 ? "Buzz"
                                : "");
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        });

    }

}
