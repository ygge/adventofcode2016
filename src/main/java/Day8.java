import util.Util;

import java.util.List;

public class Day8 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static String part2(List<String> input) {
        boolean[][] screen = performInstructions(input);
        for (boolean[] booleans : screen) {
            var sb = new StringBuilder();
            for (boolean aBoolean : booleans) {
                sb.append(aBoolean ? 'X' : ' ');
            }
            System.out.println(sb.toString());
        }
        return "RURUCEOEIL";
    }

    private static int part1(List<String> input) {
        boolean[][] screen = performInstructions(input);
        int count = 0;
        for (boolean[] booleans : screen) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    ++count;
                }
            }
        }
        return count;
    }

    private static boolean[][] performInstructions(List<String> input) {
        boolean[][] screen = new boolean[6][50];
        for (String ins : input) {
            if (ins.startsWith("rect")) {
                var split = ins.substring(5).split("x");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                for (int i = 0; i < y; ++i) {
                    for (int j = 0; j < x; ++j) {
                        screen[i][j] = true;
                    }
                }
            } else if (ins.startsWith("rotate row")) {
                var split = ins.substring(13).split(" by ");
                int a = Integer.parseInt(split[0]);
                int b = Integer.parseInt(split[1]);
                boolean[] newRow = new boolean[screen[a].length];
                for (int i = 0; i < newRow.length; ++i) {
                    newRow[(i+b)%newRow.length] = screen[a][i];
                }
                screen[a] = newRow;
            } else if (ins.startsWith("rotate column")) {
                var split = ins.substring(16).split(" by ");
                int a = Integer.parseInt(split[0]);
                int b = Integer.parseInt(split[1]);
                boolean[] newCol = new boolean[screen.length];
                for (int i = 0; i < newCol.length; ++i) {
                    newCol[(i+b)%newCol.length] = screen[i][a];
                }
                for (int i = 0; i < newCol.length; ++i) {
                    screen[i][a] = newCol[i];
                }
            } else {
                throw new RuntimeException(String.format("Instruction '%s' not understood", ins));
            }
        }
        return screen;
    }
}
