package pl.datasets.widgets;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lukasz
 * @since 23.06.2016.
 */
public class Test {
    public static void main(String[] args) {
        new Test().test();
    }

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

    private void test() {
        List<String> data = new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("D");
        data.add("E");
        data.add("F");
        data.add("G");
        data.add("H");
        data.add("I");
        List<String> subList = data.subList(2, 5);
        for (String s : subList) System.out.println(s);
    }

}
