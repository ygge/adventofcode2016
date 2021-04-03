import util.Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Day14 {

    public static void main(String[] args) {
        var input = Util.readString();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(String input) {
        return run(input, 2016);
    }

    private static int part1(String input) {
        return run(input, 0);
    }

    private static int run(String input, int extra) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Map<Integer, String> hashes = new HashMap<>();
        int keys = 0;
        for (int i = 0; ; ++i) {
            var hash = getHash(input, md, hashes, i, extra);
            for (int j = 2; j < hash.length(); ++j) {
                if (hash.charAt(j - 2) == hash.charAt(j) && hash.charAt(j - 1) == hash.charAt(j)) {
                    if (checkHash(hash.charAt(j), i, input, md, hashes, extra)) {
                        if (++keys == 64) {
                            return i;
                        }
                    }
                    break;
                }
            }
        }
    }

    private static boolean checkHash(char c, int i, String input, MessageDigest md, Map<Integer, String> hashes, int extra) {
        var toCheck = String.valueOf(c).repeat(5);
        for (int j = 1; j <= 1000; ++j) {
            var hash = getHash(input, md, hashes, i+j, extra);
            if (hash.contains(toCheck)) {
                return true;
            }
        }
        return false;
    }

    private static String getHash(String input, MessageDigest md, Map<Integer, String> hashes, int i, int extra) {
        if (!hashes.containsKey(i)) {
            var digest = input + i;
            for (int j = 0; j <= extra; ++j) {
                md.reset();
                md.update(digest.getBytes(StandardCharsets.UTF_8));
                digest = toHexString(md.digest());
            }
            hashes.put(i, digest);
        }
        return hashes.get(i);
    }

    private static String toHexString(byte[] bytes) {
        var sb = new StringBuilder();
        for (byte b : bytes) {
            int b1 = (b&0xf0)>>4;
            sb.append(toHexChar(b1));
            int b2 = b&0xf;
            sb.append(toHexChar(b2));
        }
        return sb.toString();
    }

    private static char toHexChar(int b) {
        if (b < 10) {
            return (char)(b+'0');
        }
        return (char)(b-10+'a');
    }
}
