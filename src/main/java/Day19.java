import util.Util;

public class Day19 {

    public static void main(String[] args) {
        var input = Util.readInt();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(int input) {
        var first = new Node(1);
        Node r = null;
        var p = first;
        for (int i = 2; i <= input; ++i) {
            var pp = new Node(i);
            p.next = pp;
            p = pp;
            if (i == input/2) {
                r = p;
            }
        }
        p.next = first;
        assert r != null;
        boolean odd = (input&1) == 1;
        while (r != r.next) {
            r.next = r.next.next;
            if (odd) {
                r = r.next;
            }
            odd = !odd;
        }
        return r.id;
    }

    private static int part1(int input) {
        return solve(input);
    }

    private static int solve(int input) {
        if (input <= 2) {
            return 1;
        }
        if (input == 3) {
            return 3;
        }
        var a = solve(input/2);
        if ((input&1) == 1) {
            return (a-1)*2+3;
        }
        return (a-1)*2+1;
    }

    private static class Node {
        final int id;
        Node next;

        private Node(int id) {
            this.id = id;
        }
    }
}
