import util.Direction;
import util.Pos;
import util.Util;

import java.util.*;

public class Day24 {

    public static void main(String[] args) {
        var input = Util.readBoard();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(char[][] board) {
        Map<Integer, Pos> positions = createBoard(board);

        Pos start = positions.remove(0);
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(start));
        int best = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            State state = queue.poll();
            if (state.moves >= best) {
                continue;
            }
            if (state.seenPositions.size() == positions.size()) {
                best = Math.min(best, state.moves + moveTo(board, state, start));
                continue;
            }
            for (Pos goal : positions.values()) {
                if (!state.seenPositions.contains(goal)) {
                    int moves = moveTo(board, state, goal);
                    queue.add(new State(state, goal, moves));
                }
            }
        }
        return best;
    }

    private static int part1(char[][] board) {
        Map<Integer, Pos> positions = createBoard(board);

        Queue<State> queue = new LinkedList<>();
        queue.add(new State(positions.remove(0)));
        int best = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            State state = queue.poll();
            if (state.moves >= best) {
                continue;
            }
            if (state.seenPositions.size() == positions.size()) {
                best = state.moves;
                continue;
            }
            for (Pos goal : positions.values()) {
                if (!state.seenPositions.contains(goal)) {
                    int moves = moveTo(board, state, goal);
                    queue.add(new State(state, goal, moves));
                }
            }
        }
        return best;
    }

    private static Map<Integer, Pos> createBoard(char[][] board) {
        Map<Integer, Pos> positions = new HashMap<>();
        for (int y = 0; y < board.length; ++y) {
            for (int x = 0; x < board[y].length; ++x) {
                if (board[y][x] >= '0' && board[y][x] <= '9') {
                    positions.put(board[y][x]-'0', new Pos(x, y));
                    board[y][x] = '.';
                }
            }
        }
        return positions;
    }

    private static int moveTo(char[][] board, State state, Pos goal) {
        Set<Pos> seen = new HashSet<>();
        Queue<WayState> queue = new LinkedList<>();
        queue.add(new WayState(state.pos, 0));
        while (!queue.isEmpty()) {
            WayState wayState = queue.poll();
            if (!seen.add(wayState.pos)) {
                continue;
            }
            if (wayState.pos.equals(goal)) {
                return wayState.moves;
            }
            for (Direction dir : Direction.values()) {
                Pos newPos = wayState.pos.move(dir);
                if (newPos.x >= 0 && newPos.y >= 0
                        && newPos.y < board.length
                        && newPos.x < board[newPos.y].length && board[newPos.y][newPos.x] == '.') {
                    queue.add(new WayState(newPos, wayState.moves + 1));
                }
            }
        }
        throw new RuntimeException("Goal pos not found");
    }

    private static class WayState {
        final Pos pos;
        final int moves;

        private WayState(Pos pos, int moves) {
            this.pos = pos;
            this.moves = moves;
        }
    }

    private static class State {
        final Pos pos;
        final Set<Pos> seenPositions;
        final int moves;

        private State(Pos pos) {
            this.pos = pos;
            this.seenPositions = new HashSet<>();
            this.moves = 0;
        }

        private State(State state, Pos pos, int moves) {
            this.pos = pos;
            this.seenPositions = new HashSet<>(state.seenPositions);
            this.seenPositions.add(pos);
            this.moves = state.moves + moves;
        }
    }
}
