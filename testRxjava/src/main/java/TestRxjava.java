import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestRxjava {

    public static void main(String[] args) {

        List<String> string1 = Arrays.asList("a1", "b1", "c1", "d1", "e1");
        //List<String> string2 = Arrays.asList("a2", "b2", "c2", "d2", "e2");

        // print the list string1
        for (String string : string1) {
            System.out.println(string);
        }
        System.out.println("\n\n\n");

        // create Flowable<String> with fromIterable
        Flowable.fromIterable(string1)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !s.contains("c1");
                    }
                })
                .firstOrError()
                .doOnSuccess(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                })
                .subscribe();
    }

}
