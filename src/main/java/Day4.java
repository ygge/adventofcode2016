import util.Util;

import java.util.ArrayList;
import java.util.List;

public class Day4 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        for (String s : input) {
            int pos = s.lastIndexOf('-');
            int pot = s.indexOf('[', pos);
            int sector = Integer.parseInt(s.substring(pos+1, pot));
            if (valid(s, pos, pot)) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < pos; ++i) {
                    sb.append(next(sector, s.charAt(i)));
                }
                if (sb.toString().equals("northpole object storage")) {
                    return sector;
                }
                System.out.println(sector + " " + sb.toString());
            }
        }
        throw new RuntimeException("No room with correct name found");
    }

    private static char next(int sector, char c) {
        if (c == '-') {
            return ' ';
        }
        return (char)(((c-'a')+sector)%26+'a');
    }

    private static int part1(List<String> input) {
        int sum = 0;
        for (String s : input) {
            int pos = s.lastIndexOf('-');
            int pot = s.indexOf('[', pos);
            int sector = Integer.parseInt(s.substring(pos+1, pot));
            if (valid(s, pos, pot)) {
                sum += sector;
            }
        }
        return sum;
    }

    private static boolean valid(String s, int pos, int pot) {
        String checksum = s.substring(pot +1, s.length()-1);
        int[] count = new int[26];
        for (int i = 0; i < pos; ++i) {
            var c = s.charAt(i);
            if (c != '-') {
                ++count[c-'a'];
            }
        }
        List<Character> all = new ArrayList<>();
        for (int i = 0; i < count.length; ++i) {
            if (count[i] > 0) {
                all.add((char)(i+'a'));
            }
        }
        all.sort((c1, c2) -> {
            int cmp = count[c1 - 'a'] - count[c2 - 'a'];
            if (cmp != 0) {
                return -cmp;
            }
            return c1 - c2;
        });
        boolean ok = true;
        for (int i = 0; i < checksum.length(); ++i) {
            if (checksum.charAt(i) != all.get(i)) {
                ok = false;
                break;
            }
        }
        return ok;
    }
}
