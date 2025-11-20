import java.util.Scanner;
import java.util.Random;

public class Battle {
    static Random rand = new Random();

    public static void disp(String text) {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            sleep(0.05);
        }
        System.out.println();
    }

    public static void clear() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void sleep(double seconds) {
        int time = (int)(seconds * 1000);
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int rng(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    public static class Setup {
        String name = "PlayerName";
        String ename = "EnemyName";
        int maxhp = 100;
        int hp = maxhp;
        int ehp = 2500;
        int atk = 10;
        int eatk = 15;
        int def = 2;
        int edef = 5;
    }

    public static void battle() {
        clear();
        Setup state = new Setup();
        disp("Welcome, " + state.name + ", to a test of my Java skills in the form of making a turn-based combat system in pure Java");
        disp("I have a few things that I coded to ensure that it looked good and I stuck to OOP Principals.");
        disp("Anyway, you should get to testing the fight, bye!");
        sleep(3);
        clear();
        playerTurn(state);
    }

    public static void playerTurn(Setup state) {
        disp("Your Turn:");
        disp("You have " + state.hp + " health left (" + (((state.hp * 100) / state.maxhp)) + "%).");
        disp(state.ename + " has " + state.ehp + " left.");
        disp("What would you like to do:");
        disp("1. Attack");
        disp("2. Heal");
        disp("3. Forfeit");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        switch(choice) {
            case 1 -> {
                int atkamt = rng(1, 10);
                int damage = (state.atk - state.edef) * atkamt;
                state.ehp -= damage;
                disp("You attack " + state.ename + " " + atkamt + " times for " + damage + " damage!");
                if (state.ehp <= 0) {
                    disp("You defeated " + state.ename + "!");
                    disp("If you see this, im fairly sure my code works!");
                    System.exit(0);
                } else {
                    sleep(3);
                    clear();
                    enemyTurn(state);
                }
            }
            case 2 -> {
                disp("You drank a health potion...");
                if (state.hp == state.maxhp) {
                    disp("You are already at max health...");
                    disp("You genuinely wasted a turn");
                } else if (state.hp >= Math.round(state.maxhp / 2)) {
                    state.hp = state.maxhp;
                    disp("You healed to full health!");
                } else {
                    state.hp += Math.round(state.maxhp / 2);
                    disp("You have healed " + Math.round(state.maxhp / 2) + " health!");
                }
                sleep(3);
                clear();
                enemyTurn(state);
            }
            case 3 -> {
                disp("Are you really sure you want to quit? (y/N)");
                String forfeit = input.next().toLowerCase();
                switch(forfeit) {
                    case "y" -> {
                        disp("Your loss...");
                        sleep(1);
                        disp("3...");
                        sleep(1);
                        disp("2...");
                        sleep(1);
                        disp("1...");
                        sleep(3);
                        System.exit(0);
                    }
                    default -> {
                        disp("Thank you for not exiting!");
                        disp("Now back to fighting!");
                        sleep(0.5);
                        clear();
                        playerTurn(state);
                    }
                }
            }
            default -> {
                disp("You were too scared to move...");
                sleep(3);
                clear();
                enemyTurn(state);
            }
        }
    }

    public static void enemyTurn(Setup state) {
        disp(state.ename + "'s Turn:");
        disp(state.ename + " throws an attack at you...");
        int hitchance = rng(1, 4);
        switch(hitchance) {
            case 1 -> {
                int damage = Math.round(state.eatk / 2) - Math.round(state.def / 2);
                state.hp -= damage;
                disp("...and it lands a glancing blow, dealing " + damage + " damage.");
            }
            case 2 -> {
                int damage = (state.eatk * 2) - Math.round(state.def / 2);
                state.hp -= damage;
                disp("...and it landed a critical hit, dealing " + damage + " damage...");
            }
            case 3 -> {
                int damage = state.eatk - Math.round(state.def / 2);
                state.hp -= damage;
                disp("...and the hit landed, dealing " + damage + " damage.");
            }
            default -> {
                disp("...and the attack missed!");
                disp("You are still at " + state.hp + " health!");
            }
        }
        if (state.hp <= 0) {
            disp("You lost...");
            disp("It was a good run, but at least my code works!");
            System.exit(0);
        } else {
            sleep(3);
            clear();
            playerTurn(state);
        }
    }
}