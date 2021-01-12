import util.Util;

import java.util.List;

public class Day2 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static String part2(List<String> input) {
        char num = '5';
        var sb = new StringBuilder();
        for (String s : input) {
            for (int i = 0; i < s.length(); ++i) {
                num = next(num, s.charAt(i));
            }
            sb.append(num);
        }
        return sb.toString();
    }

    private static String part1(List<String> input) {
        int num = 5;
        var sb = new StringBuilder();
        for (String s : input) {
            for (int i = 0; i < s.length(); ++i) {
                num = next(num, s.charAt(i));
            }
            sb.append(num);
        }
        return sb.toString();
    }

    private static char next(char num, char dir) {
        switch (dir) {
            case 'U':
                return switch (num) {
                    case '3' -> '1';
                    case '6' -> '2';
                    case '7' -> '3';
                    case '8' -> '4';
                    case 'A' -> '6';
                    case 'B' -> '7';
                    case 'C' -> '8';
                    case 'D' -> 'B';
                    default -> num;
                };
            case 'R':
                return switch (num) {
                    case '2' -> '3';
                    case '3' -> '4';
                    case '5' -> '6';
                    case '6' -> '7';
                    case '7' -> '8';
                    case '8' -> '9';
                    case 'A' -> 'B';
                    case 'B' -> 'C';
                    default -> num;
                };
            case 'D':
                return switch (num) {
                    case '1' -> '3';
                    case '2' -> '6';
                    case '3' -> '7';
                    case '4' -> '8';
                    case '6' -> 'A';
                    case '7' -> 'B';
                    case '8' -> 'C';
                    case 'B' -> 'D';
                    default -> num;
                };
            case 'L':
                return switch (num) {
                    case '3' -> '2';
                    case '4' -> '3';
                    case '6' -> '5';
                    case '7' -> '6';
                    case '8' -> '7';
                    case '9' -> '8';
                    case 'B' -> 'A';
                    case 'C' -> 'B';
                    default -> num;
                };
        }
        throw new RuntimeException("dir " + dir + " not understood");
    }

    private static int next(int num, char dir) {
        switch (dir) {
            case 'U':
                return num <= 3 ? num : num-3;
            case 'R':
                return num%3 == 0 ? num : num+1;
            case 'D':
                return num >= 7 ? num : num+3;
            case 'L':
                return num%3 == 1 ? num : num-1;
        }
        throw new RuntimeException("dir " + dir + " not understood");
    }
}
