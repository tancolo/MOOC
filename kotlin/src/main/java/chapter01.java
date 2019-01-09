import java.util.Arrays;
import java.util.List;

public class chapter01 {
    public static void main(String ...args) {
        System.out.println("hello world java");

        int[] test = new int[]{1,2,3,4};
        List list = Arrays.asList(test);

        System.out.println("list.size = " + list.size());
        System.out.println("test: " + test.length);
    }
}
