import util.Util;

public class Day16 {

    public static void main(String[] args) {
        var input = Util.readString();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static String part2(String input) {
        return run(input, 35651584);
    }

    private static String part1(String input) {
        return run(input, 272);
    }

    private static String run(String input, int len) {
        var str = input;
        while (str.length() < len) {
            var sb = new StringBuilder();
            for (int i = str.length()-1; i >= 0; --i) {
                sb.append(str.charAt(i) == '1' ? '0' : '1');
            }
            str += "0" + sb.toString();
        }
        return checkSum(str.substring(0, len));
    }

    private static String checkSum(String str) {
        var checksum = str;
        while (true) {
            var sb = new StringBuilder();
            for (int i = 0; i < checksum.length(); i += 2) {
                if (checksum.charAt(i) == checksum.charAt(i+1)) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
            checksum = sb.toString();
            if ((checksum.length()&1) == 1) {
                return checksum;
            }
        }
    }
}
