import util.Util;

import java.util.List;

public class Day9 {

    public static void main(String[] args) {
        var input = Util.readString();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input, 0, input.length()));
    }

    private static long part2(String input, int start, int len) {
        long count = 0;
        for (int i = start; i < len; ++i) {
            var c = input.charAt(i);
            if (c == '(') {
                var end = input.indexOf(')', i);
                var split = input.substring(i+1, end).split("x");
                int chars = Integer.parseInt(split[0]);
                int num = Integer.parseInt(split[1]);
                count += num*part2(input, end+1, end+chars+1);
                i = end+chars;
            } else {
                ++count;
            }
        }
        return count;
    }

    private static int part1(String input) {
        var sb = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            var c = input.charAt(i);
            if (c == '(') {
                var end = input.indexOf(')', i);
                var split = input.substring(i+1, end).split("x");
                int chars = Integer.parseInt(split[0]);
                int num = Integer.parseInt(split[1]);
                var str = input.substring(end+1, end+chars+1);
                for (int j = 0; j < num; ++j) {
                    sb.append(str);
                }
                i = end+chars;
            } else {
                sb.append(c);
            }
        }
        return sb.length();
    }
}
