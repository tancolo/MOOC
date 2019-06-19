import java.util.*;

public class Algorithms {
    public static void main(String... args) {
        List<String> words = Arrays.asList("com", "android", "external",
                "storage", "documents", "tree",
                "split", "axel", "can", "express",
                "download", "trash");

        //sortWords01(words);
        //sortWords02(words);
        sortWordsKaiXue(words);
    }

    /**
     * 有一个英文小写单词列表 List\，要求将其按首字母分组（key 为 ‘a’ - ‘z’），
     * 并且每个分组内的单词列表都是按升序排序，得到一个 Map\>。请尝试用 10 行以内 Java 6.0 代码完成。
     * !!!此方法无法完成 10行代码搞定!!!
     * @param words
     */
    public static void sortWords01(List<String> words) {

        // classify the words with 'a' ~ 'z'

        TreeMap<String, List<String>> wordMap = new TreeMap<>();
        for(Iterator<String> iterator = words.iterator(); iterator.hasNext();) {
            String word = iterator.next();
            String firstChar = word.substring(0, 1);

            List<String> stringList = new ArrayList<>();

            if (wordMap.get(firstChar) == null) {
                stringList.add(word);
                wordMap.put(firstChar, stringList);
            } else  {
                List<String> foundList = wordMap.get(firstChar);
                foundList.add(word);
                wordMap.put(firstChar, foundList);
            }
        }

        // sort the ArrayList in TreeMap
        Iterator<Map.Entry<String, List<String>>> iterator = wordMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            Collections.sort(entry.getValue());
        }

        // print the TreeMap
        printMap(wordMap);
    }

    /**
     * 有一个英文小写单词列表 List\，要求将其按首字母分组（key 为 ‘a’ - ‘z’），
     * 并且每个分组内的单词列表都是按升序排序，得到一个 Map\>。请尝试用 10 行以内 Java 6.0 代码完成。
     * !!!此方法无法完成 10行代码搞定!!!
     * 思路:
     * 1. Iterate the list and classify the words to 'a' ~ 'z' TreeMap
     * 2. Sort every List in value of TreeMap
     * 改进版本
     * @param words
     */
    public static void sortWords02(List<String> words) {

        // classify the words with 'a' ~ 'z'
        TreeMap<Character, List<String>> wordsMap = new TreeMap<>();
        for (String word : words) {
            char firstChar = word.charAt(0);
            if (!wordsMap.containsKey(firstChar)) {
                wordsMap.put(firstChar, new ArrayList<>());
            }
            wordsMap.get(firstChar).add(word);
        }

        // sort the ArrayList in TreeMap
        for (List<String> wordList : wordsMap.values()) {
            Collections.sort(wordList);
        }

        printMap(wordsMap);

//        // print the TreeMap
//        Iterator<Map.Entry<Character, List<String>>> printIterator = wordsMap.entrySet().iterator();
//        while (printIterator.hasNext()) {
//            Map.Entry<Character, List<String>> entry = printIterator.next();
//            System.out.println("key: " + entry.getKey());
//
//            for (Iterator<String> wordIterator = entry.getValue().iterator(); wordIterator.hasNext();) {
//                String word = wordIterator.next();
//                System.out.println("        value: " + word);
//            }
//        }
    }


    /**
     * 原始方法并没有对key排序， TreeMap是会自动排序的
     * @param keywords
     */
    public static void sortWordsKaiXue(List<String> keywords) {
        Map<Character, List<String>> result = new HashMap<>();
        for (String k: keywords) {
            char firstChar = k.charAt(0);
            if (!result.containsKey(firstChar)) {
                result.put(firstChar, new ArrayList<String>());
            }
            result.get(firstChar).add(k);
        }
        for (List<String> list: result.values()) {
            Collections.sort(list);
        }

        // print the Map
        printMap(result);
    }


    public static <K, V> void printMap(Map<K, List<V>> map) {
        // print the Map
        Iterator<Map.Entry<K, List<V>>> printIterator = map.entrySet().iterator();
        while (printIterator.hasNext()) {
            Map.Entry<K, List<V>> entry = printIterator.next();
            System.out.println("key: " + entry.getKey());

            for (Iterator<V> wordIterator = entry.getValue().iterator(); wordIterator.hasNext();) {
                V word = wordIterator.next();
                System.out.println("        value: " + word.toString());
            }
        }
    }

}
