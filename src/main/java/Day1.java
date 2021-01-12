import util.Direction;
import util.Pos;
import util.Util;

import java.util.HashSet;
import java.util.Set;

public class Day1 {

    public static void main(String[] args) {
        String[] split = Util.readString().split(", ");
        //Util.submitPart1(part1(split));
        Util.submitPart2(part2(split));
    }

    private static int part2(String[] split) {
        Set<Pos> seen = new HashSet<>();
        Pos p = new Pos(0, 0);
        Direction dir = Direction.UP;
        for (String s : split) {
            var d = s.charAt(0);
            if (d == 'L') {
                dir = dir.turnLeft();
            } else {
                dir = dir.turnRight();
            }
            int num = Integer.parseInt(s.substring(1));
            for (int i = 0; i < num; ++i) {
                if (!seen.add(p)) {
                    return Math.abs(p.x) + Math.abs(p.y);
                }
                p = p.move(dir);
            }
        }
        throw new RuntimeException("No pos seen twice");
    }

    private static int part1(String[] split) {
        Pos p = new Pos(0, 0);
        Direction dir = Direction.UP;
        for (String s : split) {
            var d = s.charAt(0);
            if (d == 'L') {
                dir = dir.turnLeft();
            } else {
                dir = dir.turnRight();
            }
            int num = Integer.parseInt(s.substring(1));
            for (int i = 0; i < num; ++i) {
                p = p.move(dir);
            }
        }
        return Math.abs(p.x) + Math.abs(p.y);
    }
}
