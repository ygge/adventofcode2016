import util.Util;

import java.util.*;

public class Day7 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        int count = 0;
        for (String row : input) {
            Set<String> found = new HashSet<>();
            Set<String> foundInside = new HashSet<>();
            int open = 0;
            for (int i = 0; i < row.length(); ++i) {
                char c = row.charAt(i);
                if (c == '[') {
                    ++open;
                } else if (c == ']') {
                    --open;
                } else if (i > 1 && c == row.charAt(i-2) && row.charAt(i-1) != c) {
                    if (open > 0) {
                        foundInside.add(row.charAt(i-1) + "" + c);
                    } else {
                        found.add(c + "" + row.charAt(i-1));
                    }
                }
            }
            found.retainAll(foundInside);
            if (!found.isEmpty()) {
                ++count;
            }
        }
        return count;
    }

    private static int part1(List<String> input) {
        int count = 0;
        for (String row : input) {
            boolean found = false;
            boolean foundInside = false;
            int open = 0;
            for (int i = 0; i < row.length(); ++i) {
                char c = row.charAt(i);
                if (c == '[') {
                    ++open;
                } else if (c == ']') {
                    --open;
                } else if (i > 2 && c == row.charAt(i-3) && row.charAt(i-1) == row.charAt(i-2) && c != row.charAt(i-1)) {
                    if (open > 0) {
                        foundInside = true;
                    } else {
                        found = true;
                    }
                }
            }
            if (found && !foundInside) {
                ++count;
            }
        }
        return count;
    }
}
