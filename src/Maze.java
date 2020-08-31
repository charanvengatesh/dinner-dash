import java.util.*;

public class Maze {

  private Stack<Point> stack = new Stack<>();
  private Random rand = new Random();
  private int[][] maze;

  private int x;
  private int y;

  public Maze(int width, int height) {
    this.x = width;
    this.y = height;
    this.maze = new int[this.y][this.x];

  }

  public void generateMaze() {
    stack.push(new Point(0, 0));
    while (!stack.empty()) {
      Point next = stack.pop();
      if (validNextPoint(next)) {
        maze[next.x][next.y] = 1;
        ArrayList<Point> neighbors = findNeighbors(next);
        randomlyAddPointsToStack(neighbors);
      }
    }
    maze[0][0] = 2;
    maze[this.y-1][this.x-1] = 3;
  }

  public char[][] getMaze() {
    char[][] charMaze = new char[this.y][this.x];
    for (int i = 0; i < charMaze.length; i++) {
      for (int j = 0; j < charMaze[i].length; j++) {
        if (maze[i][j] == 0) {
          charMaze[i][j] = 'B';
        } else if (maze[i][j] == 1) {
          charMaze[i][j] = 'O';
        } else if (maze[i][j] == 2) {
          charMaze[i][j] = 'S';
        } else if (maze[i][j] == 3) {
          charMaze[i][j] = 'E';
        }
      }
    }
    return charMaze;
  }

  public void printMaze() {
    for (int i = 0; i < this.x + 2; i++) {
      System.out.print("X ");
    }
    System.out.println();
    for (int i = 0; i < this.y; i++) {
      System.out.print("X ");
      for (int j = 0; j < this.x; j++) {
        if (maze[i][j] == 0) {
          System.out.print("X");
        } else if (maze[i][j] == 1) {
          System.out.print(" ");
        } else if (maze[i][j] == 2) {
          System.out.print("S");
        } else{
          System.out.print("E");
        }
        System.out.print(" ");
      }
      System.out.println("X");
    }
    for (int i = 0; i < this.x + 2; i++) {
      System.out.print("X ");
    }
  }

  private boolean validNextPoint(Point point) {
    int numNeighboringOnes = 0;
    for (int y = point.y - 1; y < point.y + 2; y++) {
      for (int x = point.x - 1; x < point.x + 2; x++) {
        if (pointOnGrid(x, y) && pointNotPoint(point, x, y) && maze[x][y] == 1) {
          numNeighboringOnes++;
        }
      }
    }
    return (numNeighboringOnes < 3) && maze[point.x][point.y] != 1;
  }

  private void randomlyAddPointsToStack(ArrayList<Point> points) {
    int targetIndex;
    while (!points.isEmpty()) {
      targetIndex = rand.nextInt(points.size());
      stack.push(points.remove(targetIndex));
    }
  }

  private ArrayList<Point> findNeighbors(Point point) {
    ArrayList<Point> neighbors = new ArrayList<>();
    for (int y = point.y - 1; y < point.y + 2; y++) {
      for (int x = point.x - 1; x < point.x + 2; x++) {
        if (pointOnGrid(x, y) && pointNotCorner(point, x, y) && pointNotPoint(point, x, y)) {
          neighbors.add(new Point(x, y));
        }
      }
    }
    return neighbors;
  }

  private Boolean pointOnGrid(int x, int y) {
    return x >= 0 && y >= 0 && x < this.y && y < this.x;
  }

  private Boolean pointNotCorner(Point point, int x, int y) {
    return (x == point.x || y == point.y);
  }

  private Boolean pointNotPoint(Point point, int x, int y) {
    return !(x == point.x && y == point.y);
  }
}