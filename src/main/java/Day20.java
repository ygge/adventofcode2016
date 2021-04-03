import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Day20 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        List<Long> start = new ArrayList<>();
        List<Long> end = new ArrayList<>();
        parseInput(input, start, end);
        int count = 0;
        long s = 0;
        while (s <= 4294967295L) {
            boolean ok = true;
            for (int i = 0; i < start.size(); ++i) {
                if (start.get(i) <= s && end.get(i) >= s) {
                    ok = false;
                    s = end.get(i);
                    break;
                }
            }
            if (ok) {
                ++count;
            }
            ++s;
        }
        return count;
    }

    private static long part1(List<String> input) {
        List<Long> start = new ArrayList<>();
        List<Long> end = new ArrayList<>();
        parseInput(input, start, end);
        long s = 0;
        while (true) {
            boolean ok = true;
            for (int i = 0; i < start.size(); ++i) {
                if (start.get(i) <= s && end.get(i) >= s) {
                    ok = false;
                    s = end.get(i)+1;
                    break;
                }
            }
            if (ok) {
                break;
            }
        }
        return s;
    }

    private static void parseInput(List<String> input, List<Long> start, List<Long> end) {
        for (String row : input) {
            var split = row.split("-");
            start.add(Long.parseLong(split[0]));
            end.add(Long.parseLong(split[1]));
        }
    }
}
