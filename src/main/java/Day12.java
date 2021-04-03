import util.Util;

import java.util.List;

public class Day12 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        int[] reg = new int[4];
        reg[2] = 1;
        return run(input, reg);
    }

    private static int part1(List<String> input) {
        int[] reg = new int[4];
        return run(input, reg);
    }

    private static int run(List<String> input, int[] reg) {
        int p = 0;
        while (p >= 0 && p < input.size()) {
            var split = input.get(p).split(" ");
            switch (split[0]) {
                case "cpy":
                    setValue(reg, split[2], getValue(reg, split[1]));
                    break;
                case "inc":
                    setValue(reg, split[1], getValue(reg, split[1]) + 1);
                    break;
                case "dec":
                    setValue(reg, split[1], getValue(reg, split[1]) - 1);
                    break;
                case "jnz":
                    if (getValue(reg, split[1]) != 0) {
                        p += getValue(reg, split[2])-1;
                    }
            }
            ++p;
        }
        return reg[0];
    }

    private static void setValue(int[] reg, String r, int value) {
        char c = r.charAt(0);
        reg[c-'a'] = value;
    }

    private static int getValue(int[] reg, String ins) {
        if (ins.length() == 1) {
            char c = ins.charAt(0);
            if (c >= 'a' && c <= 'd') {
                return reg[c-'a'];
            }
        }
        return Integer.parseInt(ins);
    }
}
