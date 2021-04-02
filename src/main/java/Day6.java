import util.Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static String part2(List<String> input) {
        List<Map<Character, Integer>> seen = new ArrayList<>();
        for (int i = 0; i < input.get(0).length(); ++i) {
            seen.add(new HashMap<>());
        }
        for (String row : input) {
            for (int i = 0; i < row.length(); ++i) {
                Integer c = seen.get(i).get(row.charAt(i));
                seen.get(i).put(row.charAt(i), c == null ? 1 : c+1);
            }
        }
        var sb = new StringBuilder();
        for (Map<Character, Integer> map : seen) {
            var c = map.entrySet().stream()
                    .min((e1, e2) -> e1.getValue() - e2.getValue())
                    .map(Map.Entry::getKey)
                    .orElseThrow();
            sb.append(c);
        }
        return sb.toString();
    }

    private static String part1(List<String> input) {
        List<Map<Character, Integer>> seen = new ArrayList<>();
        for (int i = 0; i < input.get(0).length(); ++i) {
            seen.add(new HashMap<>());
        }
        for (String row : input) {
            for (int i = 0; i < row.length(); ++i) {
                Integer c = seen.get(i).get(row.charAt(i));
                seen.get(i).put(row.charAt(i), c == null ? 1 : c+1);
            }
        }
        var sb = new StringBuilder();
        for (Map<Character, Integer> map : seen) {
            var c = map.entrySet().stream()
                    .max((e1, e2) -> e1.getValue() - e2.getValue())
                    .map(Map.Entry::getKey)
                    .orElseThrow();
            sb.append(c);
        }
        return sb.toString();
    }
}
