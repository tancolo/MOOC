import java.util.Arrays;
import java.util.List;

public class chapter01 {
    public static void main(String ...args) {
//        System.out.println("hello world java");
//
//        int[] test = new int[]{1,2,3,4};
//        List list = Arrays.asList(test);
//
//        System.out.println("list.size = " + list.size());
//        System.out.println("test: " + test.length);

        String document = "content://com.android.externalstorage.documents/tree/9016-4EF8%3A";

        // step 1. split 9016-4EF8 from uri
        String[] splits = document.toString().split("/");
        // debug splits
        System.out.println("AXEL===> length = " + splits.length);
        for (String split : splits) {
            System.out.println("AXEL===> split = " + split);
        }

        String endSplitString = splits[splits.length - 1];
        String splitUsb = endSplitString.substring(0, endSplitString.length() - 3);
        System.out.println("AXEL===> split = " + splitUsb);

    }
}
