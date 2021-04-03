import util.Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static long part2(List<String> input) {
        List<Integer> num = new ArrayList<>();
        List<Integer> start = new ArrayList<>();
        mapInput(input, num, start);
        num.add(11);
        start.add(0);
        return run(num, start);
    }

    private static long part1(List<String> input) {
        List<Integer> num = new ArrayList<>();
        List<Integer> start = new ArrayList<>();
        mapInput(input, num, start);
        return run(num, start);
    }

    private static long run(List<Integer> num, List<Integer> start) {
        long n = num.get(0);
        long s = n-(start.get(0)+1);
        for (int i = 1; i < num.size(); ++i) {
            long n2 = num.get(i);
            long s2 = n2-(start.get(i)+i+1);
            while (s2 < 0) {
                s2 += n2;
            }
            s2 %= n2;
            int j = 0;
            for (; (j*n+s-s2)%n2 != 0; ++j);
            s += j*n;
            for (j = 1; (j*n+s-s2)%n2 != 0; ++j);
            n *= j;
        }
        return s;
    }

    private static void mapInput(List<String> input, List<Integer> num, List<Integer> start) {
        for (String row : input) {
            var split = row.split(" ");
            int n = Integer.parseInt(split[3]);
            num.add(n);
            int s = Integer.parseInt(split[11].substring(0, split[11].length()-1));
            start.add(s);
        }
    }
}
