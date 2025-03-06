
import java.util.*;

public class App {

    static int width = 7;
    static int height = 4;
    static int score = 0;

    public static void main(String[] args) {

        clearScreen();
        printTitle();

        boolean gameRunning = true;
        Scanner kb = new Scanner(System.in);

        System.out.println("Start game | Tutorial [s/t]");
        String str = kb.next();

        if (str.equals("game") || str.equals("s")) {
            while (gameRunning) {
                gameRunning = gameLoop(kb);
            }
        } else {
            clearScreen();
            tutorial(kb, gameRunning);
        }

    }

    public static boolean gameLoop(Scanner kb) {
        Maze maze = new Maze(width, height);
        Player player = new Player();
        maze.generateMaze();
        int[][] data = maze.getMaze();
        clearScreen();

        while (!(player.x == maze.getEndx() && player.y == maze.getEndy())) {
            maze.printMaze(player);

            System.err.println(maze.getEndx() + " " + maze.getEndy() + " " );
            System.out.println("\nChoose a direction (up) (down) (right) (left) or use WASD");
            System.out.print("Direction: ");

            int dist = 1;

            String dir = kb.nextLine();
            if (dir.contains(" ")) {
                String[] strArr = dir.split(" ");
                dir = strArr[0];
                dist = Integer.parseInt(strArr[1]);
            }

            for (int i = 0; i < dist; i++) {

                try {
                    if (dir.equals("up") || dir.equals("w")) {
                        player.up(data);
                    } else if (dir.equals("down") || dir.equals("s")) {
                        player.down(data);
                    } else if (dir.equals("right") || dir.equals("d")) {
                        player.right(data);
                    } else if (dir.equals("left") || dir.equals("a")) {
                        player.left(data);
                    }
                } catch (Exception e) {
                    //
                }
            }
            clearScreen();
        }
        Solver solver = new Solver(data, 0, 0, false);
        List<Node> path = solver.findPathTo(maze.getEndx(), maze.getEndy());
        // solver.printMaze(data);

        if (player.moves == path.size() - 1) {
            System.out.println("Congratulations! You reached your destination in " + player.moves
                    + " minutes. That is the quickest route!");
        } else if(player.moves >= path.size() + 4){
            System.out.println("You're fired because the pizza got cold and the customer was not satisfied.");
            return false;
        }else {
            System.out.println("You reached your destination in " + player.moves
                    + " minutes, because you didn't go quickly. You should have reached your destination in "
                    + (path.size() - 1) + " minutes. You still get some coins.");
            System.out.println("\nYou can still view the fastest view possible.");
            System.out.println("\n\nDo you want to see it? (yes) (no)");
            System.out.print("Solution: ");

            String ans = kb.next().toLowerCase();

            while (!(ans.equals("no") || ans.equals("yes"))) {
                System.out.println("Invalid answer!");
                System.out.println("\n\nDo you want to see it? (yes) (no)");
                System.out.print("Solution: ");

                ans = kb.next().toLowerCase();
            }

            if (ans.equals("yes") || ans.equals("y")) {
                System.out.println("Solution:");
                solver.printMaze(data);
            }

        }

        score += (path.size() - 1) / player.moves + width + height;
        System.out.println("\nScore: " + score + " coins.");

        System.out.println("Enter any character to continue to the next level");
        System.out.println("Or \"stop\" or \"quit\" to leave the game");
        System.out.print("Next level: ");

        String ans = kb.next().toLowerCase();

        if (ans.equals("stop") || ans.equals("quit")) {
            return false;
        } else {
            width += Math.round(Math.random() * 10) % 6;
            height += Math.round(Math.random() * 10) % 9;
            return true;
        }
    }

    public static void clearScreen() {

        System.out.print("\033[H\033[2J");

        System.out.flush();

    }

    public static void printTitle() {
        System.out.println("  ╔╗                       ╔╗         ╔╗");
        System.out.println("  ║║                       ║║         ║║  ");
        System.out.println("╔═╝║╔╗╔═╗ ╔═╗ ╔══╗╔═╗    ╔═╝║╔══╗ ╔══╗║╚═╗");
        System.out.println("║╔╗║╠╣║╔╗╗║╔╗╗║╔╗║║╔╝    ║╔╗║║╔╗║ ║══╣║╔╗║");
        System.out.println("║╚╝║║║║║║║║║║║║║═╣║║     ║╚╝║║╚╝╚╗╠══║║║║║");
        System.out.println("╚══╝╚╝╚╝╚╝╚╝╚╝╚══╝╚╝     ╚══╝╚═══╝╚══╝╚╝╚╝");
    }

    public static void tutorial(Scanner kb, boolean gameRunning) {
        System.out.println(
                "You are working at Pizza Hut and you are a delivery guy who got to deliver fresh cheese pizza to a house.\n");
        System.out.println(
                "This game is a maze game where you start at the top left corner of the maze which is the fast food restaurant.\n");
        System.out.println(
                "You are trying to deliver the pizza to the red square which is the location of the house you are going to deliver the pizza to.\n");
        System.out.println(
                "The objective is to go to your destination with the lowest number of moves possible, and each move is a minute.\n");
        System.out.println("If you go to your destination 5 minutes after your target time, you will be fired.\n");
        System.out.println(
                "The faster (or with the lowest number of moves) you reach your destination, the more coins you get.\n\n");

        Maze maze = new Maze(20, 20);
        maze.generateMaze();
        int[][] data = maze.getMaze();
        System.out.println("Here's an example maze.\n\n");
        maze.printMaze(new Player());
        System.out.println("You are the player (P) and you are trying to reach the destination (H)\n");
        System.out.println("You move by typing the direction and you can also add multiple moves at a time as an optional feature\n");
        System.out.println("Example: down 4 \n\n");

        System.out.println("Are you ready? (yes) (no) ");
        System.out.print("Start game: ");

        String ans = kb.next().toLowerCase();
        if (ans.equals("yes") || ans.equals("y")) {
            while (gameRunning) {
                gameRunning = gameLoop(kb);
            }
        }
    }

    
}
