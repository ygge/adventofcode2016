import util.Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day5 {

    public static void main(String[] args) {
        var input = Util.readString();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static String part2(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        char[] password = new char[8];
        int seenChars = 0;
        int index = 0;
        for (; seenChars < 8; ++index) {
            md.reset();
            md.update((input + index).getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            if (digest[0] == 0 && digest[1] == 0 && (digest[2]>>4) == 0) {
                int p = digest[2]&0xf;
                if (p > 7 || password[p] != 0) {
                    continue;
                }
                int c = (digest[3]&0xf0)>>4;
                char cc;
                if (c < 10) {
                    cc = (char)(c+'0');
                } else {
                    cc = (char) (c + 'a' - 10);
                }
                password[p] = cc;
                ++seenChars;
            }
        }
        return new String(password);
    }

    private static String part1(String input) {
        int index = 0;
        var password = new StringBuilder();
        for (; password.length() < 8; ++index) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            md.update((input + index).getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            if (digest[0] == 0 && digest[1] == 0 && (digest[2]>>4) == 0) {
                int c = digest[2]&0xf;
                if (c < 10) {
                    password.append(c);
                } else {
                    password.append((char) (c + 'a' - 10));
                }
            }
        }
        return password.toString();
    }
}
