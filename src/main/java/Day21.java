import util.Util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day21 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        //Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static String part2(List<String> input) {
        char[] data = "abcdefgh".toCharArray();
        char[] toUse = new char[data.length];
        Set<Character> seen = new HashSet<>();
        return calc(data, toUse, seen, 0, input);
    }

    private static String calc(char[] data, char[] toUse, Set<Character> seen, int curr, List<String> input) {
        if (curr == data.length) {
            var d = new char[toUse.length];
            System.arraycopy(toUse, 0, d, 0, d.length);
            var r = run(input, d);
            if (r.equals("fbgdceah")) {
                return new String(toUse);
            }
            return null;
        }
        for (char d : data) {
            if (!seen.contains(d)) {
                seen.add(d);
                toUse[curr] = d;
                var r = calc(data, toUse, seen, curr + 1, input);
                if (r != null) {
                    return r;
                }
                seen.remove(d);
            }
        }
        return null;
    }

    private static String part1(List<String> input) {
        char[] data = "abcdefgh".toCharArray();
        return run(input, data);
    }

    private static String run(List<String> input, char[] data) {
        for (String ins : input) {
            var split = ins.split(" ");
            if (ins.startsWith("swap position")) {
                int a = Integer.parseInt(split[2]);
                int b = Integer.parseInt(split[5]);
                swap(data, a, b);
            } else if (ins.startsWith("swap letter")) {
                char a = split[2].charAt(0);
                char b = split[5].charAt(0);
                swap(data, indexOf(data, a), indexOf(data, b));
            } else if (ins.startsWith("rotate left")) {
                int a = Integer.parseInt(split[2]);
                rotate(data, -a);
            } else if (ins.startsWith("rotate right")) {
                int a = Integer.parseInt(split[2]);
                rotate(data, a);
            } else if (ins.startsWith("rotate based")) {
                char a = split[6].charAt(0);
                int index = indexOf(data, a);
                rotate(data, index+1+(index >= 4 ? 1 : 0));
            } else if (ins.startsWith("reverse")) {
                int a = Integer.parseInt(split[2]);
                int b = Integer.parseInt(split[4]);
                for (int i = 0; i <= (b-a)/2; ++i) {
                    swap(data, a+i, b-i);
                }
            } else if (ins.startsWith("move")) {
                int a = Integer.parseInt(split[2]);
                int b = Integer.parseInt(split[5]);
                char c = data[a];
                if (a < b) {
                    for (int i = a; i < b; ++i) {
                        data[i] = data[i+1];
                    }
                    data[b] = c;
                } else {
                    for (int i = a; i > b; --i) {
                        data[i] = data[i-1];
                    }
                    data[b] = c;
                }
            } else {
                throw new RuntimeException("Instruction '" + ins + "' not understood");
            }
        }
        return new String(data);
    }

    private static void rotate(char[] data, int steps) {
        char[] d = new char[data.length];
        System.arraycopy(data, 0, d, 0, data.length);
        for (int i = 0; i < data.length; ++i) {
            data[(i+steps+data.length)%data.length] = d[i];
        }
    }

    private static int indexOf(char[] data, char c) {
        for (int i = 0; i < data.length; ++i) {
            if (data[i] == c) {
                return i;
            }
        }
        throw new RuntimeException(String.format("Character %s not found", c));
    }

    private static void swap(char[] data, int a, int b) {
        var c = data[a];
        data[a] = data[b];
        data[b] = c;
    }
}
