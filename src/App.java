import java.util.Arrays;

public class App {
    public static void main(String[] args) {
        Maze maze = new Maze(20,20);
        maze.generateMaze();
        char[][] data = maze.getMaze();
        // for (int i = 0; i < data.length; i++) {
        //     System.out.println(Arrays.toString(data[i]));
        // }
        String path = "";
        Solver solver = new Solver(data, 1, 1, path, false);
        // maze.printMaze();
        solver.solveMaze();
        System.out.println(solver.getPath());
        // solver.printMaze();
    }


}
