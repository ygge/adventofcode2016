import util.Direction;
import util.Pos;
import util.Util;

import java.util.*;

public class Day22 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 2; i < input.size(); ++i) {
            nodes.add(new Node(input.get(i)));
        }
        Map<Pos, Computer> map = new HashMap<>();
        for (Node node : nodes) {
            map.put(new Pos(node.x, node.y), new Computer(node.size, node.used));
        }
        Queue<State> queue = new LinkedList<>();
        queue.add(new State(map));
        Map<Pos, Integer> bestData = new HashMap<>();
        Pos goal = new Pos(0, 0);
        while (!queue.isEmpty()) {
            State state = queue.poll();
            if (bestData.containsKey(state.data) && bestData.get(state.data) <= state.moves) {
                continue;
            }
            bestData.put(state.data, state.moves);
            if (state.data.equals(goal)) {
                continue;
            }
            for (Direction dir : Direction.values()) {
                Pos newPos = state.data.move(dir);
                if (state.computers.containsKey(newPos) && state.computers.get(newPos).used < 100) {
                    var newState = new State(state);
                    move(newState, newPos);
                    queue.add(newState);
                }
            }
        }
        return bestData.get(goal);
    }

    private static void move(State state, Pos pos) {
        int moves = emptyToPos(state, pos);
        state.moves += moves+1;
        state.empty = state.data;
        state.data = pos;
    }

    private static int emptyToPos(State state, Pos pos) {
        Queue<EmptyState> queue = new LinkedList<>();
        queue.add(new EmptyState(state.empty, 0));
        Set<Pos> seen = new HashSet<>();
        while (!queue.isEmpty()) {
            var empty = queue.poll();
            if (empty.pos.equals(pos)) {
                return empty.moves;
            }
            if (!seen.add(empty.pos)) {
                continue;
            }
            for (Direction dir : Direction.values()) {
                var newPos = empty.pos.move(dir);
                if (state.computers.containsKey(newPos)
                        && !newPos.equals(state.data)
                        && state.computers.get(newPos).used < 100) {
                    queue.add(new EmptyState(newPos, empty.moves+1));
                }
            }
        }
        throw new RuntimeException("Empty could not be moved!");
    }

    private static int part1(List<String> input) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 2; i < input.size(); ++i) {
            nodes.add(new Node(input.get(i)));
        }
        int count = 0;
        for (Node a : nodes) {
            for (Node b : nodes) {
                if ((a.x != b.x || a.y != b.y) && a.used > 0 && b.used+a.used <= b.size) {
                    ++count;
                }
            }
        }
        return count;
    }

    private static class EmptyState {
        final Pos pos;
        final int moves;

        public EmptyState(Pos pos, int moves) {
            this.pos = pos;
            this.moves = moves;
        }
    }

    private static class State {
        Pos empty, data;
        Map<Pos, Computer> computers;
        int moves;

        public State(Map<Pos, Computer> computers) {
            this.computers = computers;
            data = new Pos(0, 0);
            for (Map.Entry<Pos, Computer> entry : computers.entrySet()) {
                if (entry.getValue().used == 0) {
                    empty = entry.getKey();
                }
                if (entry.getKey().y == 0 && entry.getKey().x > data.x) {
                    data = entry.getKey();
                }
            }
            moves = 0;
        }

        public State(State state) {
            this.empty = state.empty;
            this.data = state.data;
            this.computers = new HashMap<>(state.computers);
            this.moves = state.moves;
        }
    }

    private static class Computer {
        final int size, used;

        public Computer(int size, int used) {
            this.size = size;
            this.used = used;
        }
    }

    private static class Node {
        final int x, y, size, used;

        private Node(String row) {
            var split = row.split(" +");
            var pos = split[0].substring("/dev/grid/node-x".length()).split("-y");
            this.x = Integer.parseInt(pos[0]);
            this.y = Integer.parseInt(pos[1]);
            this.size = Integer.parseInt(split[1].substring(0, split[1].length()-1));
            this.used = Integer.parseInt(split[2].substring(0, split[2].length()-1));
        }
    }
}
