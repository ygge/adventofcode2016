import util.Direction;
import util.Pos;
import util.Util;

import java.util.*;

public class Day13 {

    public static void main(String[] args) {
        var input = Util.readInt();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(int input) {
        Map<Pos, Boolean> board = new HashMap<>();
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(new Pos(1, 1), 0));
        board.put(new Pos(1, 1), false);
        int num = 0;
        while (!queue.isEmpty()) {
            var state = queue.poll();
            if (state.steps > 50) {
                continue;
            }
            ++num;
            for (Direction dir : Direction.values()) {
                var newPos = state.pos.move(dir);
                if (newPos.x >= 0 && newPos.y >= 0 && !board.containsKey(newPos)) {
                    boolean wall = isWall(newPos, input);
                    board.put(newPos, wall);
                    if (!wall) {
                        queue.add(new State(newPos, state.steps + 1));
                    }
                }
            }
        }
        return num;
    }

    private static int part1(int input) {
        Map<Pos, Boolean> board = new HashMap<>();
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(new Pos(1, 1), 0));
        while (!queue.isEmpty()) {
            var state = queue.poll();
            if (state.pos.x == 31 && state.pos.y == 39) {
                return state.steps;
            }
            for (Direction dir : Direction.values()) {
                var newPos = state.pos.move(dir);
                if (newPos.x >= 0 && newPos.y >= 0 && !board.containsKey(newPos)) {
                    boolean wall = isWall(newPos, input);
                    board.put(newPos, wall);
                    if (!wall) {
                        queue.add(new State(newPos, state.steps + 1));
                    }
                }
            }
        }
        throw new RuntimeException("No solution found!");
    }

    private static boolean isWall(Pos pos, int input) {
        var n = pos.x*pos.x + 3*pos.x + 2*pos.x*pos.y + pos.y + pos.y*pos.y + input;
        boolean wall = false;
        while (n > 0) {
            if ((n&1) > 0) {
                wall = !wall;
            }
            n >>= 1;
        }
        return wall;
    }

    private static class State {
        final Pos pos;
        final int steps;

        private State(Pos pos, int steps) {
            this.pos = pos;
            this.steps = steps;
        }
    }
}
