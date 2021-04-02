import util.Util;

import java.util.*;

public class Day11 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        State initialState = createInitialState(input);
        initialState.floors.get(0).generators.add("elerium");
        initialState.floors.get(0).generators.add("dilithium");
        initialState.floors.get(0).chips.add("elerium");
        initialState.floors.get(0).chips.add("dilithium");
        return solve(initialState);
    }

    private static int part1(List<String> input) {
        State initialState = createInitialState(input);
        return solve(initialState);
    }

    private static int solve(State initialState) {
        Queue<State> queue = new LinkedList<>();
        queue.add(initialState);
        Set<State> seen = new HashSet<>();
        while (!queue.isEmpty()) {
            var state = queue.poll();
            if (state.done()) {
                return state.turns;
            }
            if (!seen.add(state)) {
                continue;
            }
            var floor = state.floors.get(state.elevator);
            for (int i = 0; i < floor.generators.size() + floor.chips.size(); ++i) {
                for (int j = 0; j < 2; ++j) {
                    var elevatorPos = state.elevator + ((j == 0) ? -1 : 1);
                    if (elevatorPos >= 0 && elevatorPos < state.floors.size()) {
                        var newState = new State(state, elevatorPos);
                        var newFloor = newState.floors.get(elevatorPos);
                        if (i < floor.generators.size()) {
                            newFloor.generators.add(newState.floors.get(state.elevator).generators.remove(i));
                        } else {
                            newFloor.chips.add(newState.floors.get(state.elevator).chips.remove(i-floor.generators.size()));
                        }
                        if (newState.isValid() && !seen.contains(newState)) {
                            queue.add(newState);
                        }
                    }
                }
                for (int j = i+1; j < floor.generators.size() + floor.chips.size(); ++j) {
                    var elevatorPos = state.elevator + 1;
                    if (elevatorPos < state.floors.size()) {
                        var newState = new State(state, elevatorPos);
                        var newFloor = newState.floors.get(elevatorPos);
                        if (j < floor.generators.size()) {
                            newFloor.generators.add(newState.floors.get(state.elevator).generators.remove(j));
                        } else {
                            newFloor.chips.add(newState.floors.get(state.elevator).chips.remove(j-floor.generators.size()));
                        }
                        if (i < floor.generators.size()) {
                            newFloor.generators.add(newState.floors.get(state.elevator).generators.remove(i));
                        } else {
                            newFloor.chips.add(newState.floors.get(state.elevator).chips.remove(i-floor.generators.size()));
                        }
                        if (newState.isValid() && !seen.contains(newState)) {
                            queue.add(newState);
                        }
                    }
                }
            }
        }
        throw new RuntimeException("No solution found!");
    }

    private static State createInitialState(List<String> input) {
        var initialState = new State();
        for (String row : input) {
            var split = row.split(" ");
            var floor = new Floor();
            if (!split[4].equals("nothing")) {
                for (int i = 5; i < split.length; ++i) {
                    if (split[i].startsWith("generator")) {
                        floor.generators.add(split[i-1]);
                    } else if (split[i].startsWith("microchip")) {
                        floor.chips.add(split[i-1].substring(0, split[i-1].length()-"-compatible".length()));
                    }
                }
            }
            initialState.floors.add(floor);
        }
        return initialState;
    }

    public static class State {
        int elevator, turns;
        final List<Floor> floors;

        public State() {
            elevator = 0;
            turns = 0;
            floors = new ArrayList<>();
        }

        public State(State state, int newFloor) {
            elevator = newFloor;
            turns = state.turns + 1;
            floors = new ArrayList<>();
            for (Floor floor : state.floors) {
                floors.add(new Floor(floor));
            }
        }

        public boolean done() {
            for (int i = 0; i < floors.size()-1; ++i) {
                if (!floors.get(i).generators.isEmpty() || !floors.get(i).chips.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        public boolean isValid() {
            for (Floor floor : floors) {
                if (floor.generators.isEmpty()) {
                    continue;
                }
                var chips = new HashSet<>(floor.chips);
                chips.removeAll(floor.generators);
                if (!chips.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State state = (State) o;
            return elevator == state.elevator && floors.equals(state.floors);
        }

        @Override
        public int hashCode() {
            return Objects.hash(elevator, floors);
        }
    }

    public static class Floor {
        final List<String> generators;
        final List<String> chips;

        public Floor() {
            generators = new ArrayList<>();
            chips = new ArrayList<>();
        }

        public Floor(Floor floor) {
            generators = new ArrayList<>(floor.generators);
            chips = new ArrayList<>(floor.chips);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Floor floor = (Floor) o;
            return floor.generators.size() == generators.size()
                    && floor.chips.size() == chips.size();
        }

        @Override
        public int hashCode() {
            return (generators.size()<<8) | chips.size();
        }
    }
}
