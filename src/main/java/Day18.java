import util.Util;

public class Day18 {

    public static void main(String[] args) {
        var input = Util.readString();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(String input) {
        return run(input, 400000);
    }

    private static int part1(String input) {
        return run(input, 40);
    }

    private static int run(String input, int rows) {
        int safe = 0;
        var str = input;
        for (int i = 0; i < rows; ++i) {
            var sb = new StringBuilder();
            for (int j = 0; j < str.length(); ++j) {
                safe += str.charAt(j) == '.' ? 1 : 0;
                sb.append(safe(str, j-1) == safe(str, j+1) ? '.' : '^');
            }
            str = sb.toString();
        }
        return safe;
    }

    private static boolean safe(String str, int i) {
        if (i < 0 || i >= str.length()) {
            return true;
        }
        return str.charAt(i) == '.';
    }
}
