import util.Util;

import java.util.*;

public class Day10 {

    public static void main(String[] args) {
        var input = Util.readStrings();
        Util.submitPart1(part1(input));
        Util.submitPart2(part2(input));
    }

    private static int part2(List<String> input) {
        Map<Integer, Bot> bots = createBots(input);
        int[] outputs = new int[100];
        while (outputs[0] == 0 || outputs[1] == 0 || outputs[2] == 0) {
            for (Bot bot : bots.values()) {
                if (bot.values.size() == 2) {
                    Collections.sort(bot.values);
                    for (int i = 0; i < 2; ++i) {
                        if (bot.instructions.get(i).type == Type.Bot) {
                            getBot(bots, bot.instructions.get(i).value).values.add(bot.values.get(i));
                        } else if (outputs[bot.instructions.get(i).value] == 0) {
                            outputs[bot.instructions.get(i).value] = bot.values.get(i);
                        }
                    }
                    bot.values.clear();
                }
            }
        }
        return outputs[0]*outputs[1]*outputs[2];
    }

    private static int part1(List<String> input) {
        Map<Integer, Bot> bots = createBots(input);
        while (true) {
            for (Bot bot : bots.values()) {
                if (bot.values.size() == 2) {
                    Collections.sort(bot.values);
                    if (bot.values.get(0) == 17 && bot.values.get(1) == 61) {
                        return bot.id;
                    }
                    for (int i = 0; i < 2; ++i) {
                        if (bot.instructions.get(i).type == Type.Bot) {
                            getBot(bots, bot.instructions.get(i).value).values.add(bot.values.get(i));
                        }
                    }
                    bot.values.clear();
                }
            }
        }
    }

    private static Map<Integer, Bot> createBots(List<String> input) {
        Map<Integer, Bot> bots = new HashMap<>();
        for (String s : input) {
            if (s.startsWith("value")) {
                var split = s.split(" ");
                int bot = Integer.parseInt(split[5]);
                int value = Integer.parseInt(split[1]);
                getBot(bots, bot).values.add(value);
            } else if (s.startsWith("bot")) {
                var split = s.split(" ");
                int bot = Integer.parseInt(split[1]);
                Type t1 = split[5].equals("bot") ? Type.Bot : Type.Output;
                int v1 = Integer.parseInt(split[6]);
                getBot(bots, bot).instructions.add(new Instruction(t1, v1));
                Type t2 = split[10].equals("bot") ? Type.Bot : Type.Output;
                int v2 = Integer.parseInt(split[11]);
                getBot(bots, bot).instructions.add(new Instruction(t2, v2));
            } else {
                throw new RuntimeException(String.format("Instruction '%s' not understood", s));
            }
        }
        return bots;
    }

    private static Bot getBot(Map<Integer, Bot> bots, int bot) {
        if (!bots.containsKey(bot)) {
            bots.put(bot, new Bot(bot));
        }
        return bots.get(bot);
    }

    private static class Bot {
        int id;
        List<Integer> values = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();

        public Bot(int id) {
            this.id = id;
        }
    }

    private enum Type {
        Bot,
        Output
    }

    private static class Instruction {
        Type type;
        int value;

        public Instruction(Type type, int value) {
            this.type = type;
            this.value = value;
        }
    }
}
