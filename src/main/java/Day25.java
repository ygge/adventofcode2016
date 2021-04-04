import util.Util;

import java.util.List;

public class Day25 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(0);
    }

    private static int part1(List<String> input) {
        for (int i = 1; ; ++i) {
            int[] reg = new int[4];
            reg[0] = i;
            if (run(input, reg)) {
                return i;
            }
        }
    }

    private static boolean run(List<String> input, int[] reg) {
        boolean first = true;
        int produced = 0;
        int p = 0;
        while (p >= 0 && p < input.size()) {
            var split = input.get(p).split(" ");
            switch (split[0]) {
                case "cpy":
                    if (split[2].charAt(0) >= 'a' && split[2].charAt(0) <= 'd') {
                        setValue(reg, split[2], getValue(reg, split[1]));
                    }
                    break;
                case "inc":
                    if (split[1].charAt(0) >= 'a' && split[1].charAt(0) <= 'd') {
                        setValue(reg, split[1], getValue(reg, split[1]) + 1);
                    }
                    break;
                case "dec":
                    if (split[1].charAt(0) >= 'a' && split[1].charAt(0) <= 'd') {
                        setValue(reg, split[1], getValue(reg, split[1]) - 1);
                    }
                    break;
                case "jnz":
                    if (getValue(reg, split[1]) != 0) {
                        p += getValue(reg, split[2])-1;
                    }
                    break;
                case "out":
                    int o = getValue(reg, split[1]);
                    if ((first && o != 0) || (!first && o != 1)) {
                        return false;
                    }
                    first = !first;
                    if (++produced == 100) {
                        return true;
                    }
                    break;
                case "tgl":
                    var v = getValue(reg, split[1]);
                    if (p + v >= 0 && p + v < input.size()) {
                        var s = input.get(p+v).split(" ");
                        String args = input.get(p + v).substring(3);
                        switch (s[0]) {
                            case "inc" -> input.set(p + v, "dec" + args);
                            case "dec", "tgl", "out" -> input.set(p + v, "inc" + args);
                            case "jnz" -> input.set(p + v, "cpy" + args);
                            default -> input.set(p + v, "jnz" + args);
                        }
                    }
            }
            ++p;
        }
        return false;
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