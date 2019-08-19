import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 给定一段英文，找出每个单词使用的频率, 按照String字典顺序排序(忽略大小写)，
 * 并打印出所有单词及其频率的排序列表。
 * 如: “My name is …, I come from …, I am … years old!”
 */
public class Words {
    public static void main(String... args) {
        String wordsTemp = "My name is ..., I come from ..., I am ... years old!";

        for (Map.Entry<String, Integer> entry : wordFreq(wordsTemp).entrySet()) {
            System.out.println(entry.getKey() + "   " + entry.getValue());
        }
    }

    private static Map<String, Integer> wordFreq(String wordsString) {

        TreeMap<String, Integer> wordMap = new TreeMap<>();
        Matcher matcher = Pattern.compile("\\w+").matcher(wordsString);

        while (matcher.find()) {
            String word = matcher.group().toLowerCase();
            if (wordMap.get(word) == null) {
                wordMap.put(word, 1);
            }else {
                wordMap.put(word, wordMap.get(word) + 1);
            }
        }

        return wordMap;
    }

}
