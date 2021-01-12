import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {

    public static void main(String[] args) {
        List<String> input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        int count = 0;
        List<List<Integer>> data = new ArrayList<>();
        for (String s : input) {
            List<Integer> num = Arrays.stream(s.split(" "))
                    .filter(n -> n.length() > 0)
                    .map(n -> Integer.parseInt(n.trim()))
                    .collect(Collectors.toList());
            data.add(num);
        }
        for (int i = 0; i < data.size(); i += 3) {
            for (int j = 0; j < 3; ++j) {
                List<Integer> num = Arrays.asList(data.get(i).get(j), data.get(i+1).get(j), data.get(i+2).get(j));
                Collections.sort(num);
                if (num.get(0) + num.get(1) > num.get(2)) {
                    ++count;
                }
            }
        }
        return count;
    }

    private static int part1(List<String> input) {
        int count = 0;
        for (String s : input) {
            List<Integer> num = Arrays.stream(s.split(" "))
                    .filter(n -> n.length() > 0)
                    .map(n -> Integer.parseInt(n.trim()))
                    .sorted()
                    .collect(Collectors.toList());
            if (num.get(0) + num.get(1) > num.get(2)) {
                ++count;
            }
        }
        return count;
    }
}
