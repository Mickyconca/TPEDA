package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QGram {

    private static int N = 2;

    public QGram(int n) {
        QGram.N = n;
    }

//    public void printTokens(final String str) {
//        Map<String, Integer> map = getTokens(str);
//        map.forEach((k, v) -> System.out.printf("%s    %d%n", k, v));
//    }

    private static Map<String, Integer> getTokens(String str) {
        HashMap<String, Integer> map = new HashMap<>();
        StringBuilder string = new StringBuilder();
        appends(string);
        string.append(str);
        appends(string);
        //ya cree el string con los # que le falta
        for (int i = 0; i < string.length() - (N - 1); i++) {
            String key = makeKey(string, i);
            if (!map.containsKey(key)) {
                map.put(key, 1);
            } else {
                map.put(key, map.get(key) + 1);
            }
        }
        return map;
    }

    private static String makeKey(StringBuilder str, int index) {
        StringBuilder string = new StringBuilder();
        for (int i = index; i < index + N; i++) {
            string.append(str.charAt(i));
        }
        return string.toString();
    }

    private static void appends(StringBuilder str) {
        for (int i = 0; i < N - 1; i++) {
            str.append("#");
        }
    }

    public static double similarity(String s1, String s2) {
        Map<String, Integer> map1 = getTokens(s1);
        Map<String, Integer> map2 = getTokens(s2);
        Set<String> keys1 = map1.keySet();
        Set<String> keys2 = map2.keySet();
        int size1 = 0, size2 = 0, counter = 0;

        for (String key : keys2) {
            size2 += map2.get(key);
        }
        for (String k1 : keys1) {
            size1 += map1.get(k1);
            for (String k2 : keys2) {
                if (k1.equals(k2) ) {
                   counter+=Math.min(map1.get(k1), map2.get(k2));

                }
            }

        }
        return (double) (size1 + size2 - (size1-counter+size2-counter)) / (size1 + size2);
    }
}
