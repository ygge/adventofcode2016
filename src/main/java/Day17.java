import util.Direction;
import util.Pos;
import util.Util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Deque;
import java.util.LinkedList;

public class Day17 {

    public static void main(String[] args) {
        var input = Util.readString();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Deque<State> queue = new LinkedList<>();
        queue.add(new State(new Pos(0, 0), ""));
        int longest = 0;
        while (!queue.isEmpty()) {
            var state = queue.poll();
            if (state.pos.x == 3 && state.pos.y == 3) {
                longest = state.path.length();
                continue;
            }
            md.reset();
            md.update((input + state.path).getBytes(StandardCharsets.UTF_8));
            var digest = md.digest();
            for (Direction dir : Direction.values()) {
                var newPos = state.pos.move(dir);
                if (newPos.x >= 0 && newPos.y >= 0 && newPos.x <= 3 && newPos.y <= 3 && open(digest, dir)) {
                    queue.add(new State(newPos, state.path + dir(dir)));
                }
            }
        }
        return longest;
    }

    private static String part1(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Deque<State> queue = new LinkedList<>();
        queue.add(new State(new Pos(0, 0), ""));
        while (!queue.isEmpty()) {
            var state = queue.poll();
            if (state.pos.x == 3 && state.pos.y == 3) {
                return state.path;
            }
            md.reset();
            md.update((input + state.path).getBytes(StandardCharsets.UTF_8));
            var digest = md.digest();
            for (Direction dir : Direction.values()) {
                var newPos = state.pos.move(dir);
                if (newPos.x >= 0 && newPos.y >= 0 && newPos.x <= 3 && newPos.y <= 3 && open(digest, dir)) {
                    queue.add(new State(newPos, state.path + dir(dir)));
                }
            }
        }
        throw new RuntimeException("No solution found!");
    }

    private static boolean open(byte[] digest, Direction dir) {
        int b = switch (dir) {
            case UP -> (digest[0]&0xf0)>>4;
            case LEFT -> (digest[1]&0xf0)>>4;
            case DOWN -> digest[0]&0xf;
            case RIGHT -> digest[1]&0xf;
        };
        return b > 10;
    }

    private static String dir(Direction dir) {
        return switch (dir) {
            case UP -> "U";
            case LEFT -> "L";
            case DOWN -> "D";
            case RIGHT -> "R";
        };
    }

    private static class State {
        final Pos pos;
        final String path;

        private State(Pos pos, String path) {
            this.pos = pos;
            this.path = path;
        }
    }
}
