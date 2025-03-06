import java.util.*;

public class Maze {

  private Stack<Point> stack = new Stack<>();
  private Random rand = new Random();
  private int[][] maze;

  private int x;
  private int y;
  private int endx;
  private int endy;

  public Maze(int width, int height) {
    this.x = width;
    this.y = height;
    this.maze = new int[this.y][this.x];
    this.endx = width - 1;
    this.endy = height - 1;
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
    Random rand = new Random();
    for (int i = 0; i < this.x * this.y; i++) {
      int tempY = rand.nextInt(this.y);
      int tempX = rand.nextInt(this.x);
      if (maze[tempY][tempX] == 1) {
        this.endx = tempX;
        this.endy = tempY;
        break;
      }
    }
  }

  public int[][] getMaze() {
    for (int i = 0; i < maze.length; i++) {
      for (int j = 0; j < maze[i].length; j++) {
        if (maze[i][j] == 0) {
          maze[i][j] = 100;
        } else if (maze[i][j] == 1) {
          maze[i][j] = 0;
        }
      }
    }
    return maze;
  }

  public void printMaze(Player player) {
    for (int i = 0; i < this.x + 2; i++) {
      System.out.print("\u001b[40m  \u001b[0m");
    }
    System.out.println();
    for (int i = 0; i < this.y; i++) {
      System.out.print("\u001b[40m  \u001b[0m");

      for (int j = 0; j < this.x; j++) {
        if (maze[i][j] == 100) {
          System.out.print("\u001b[40m  \u001b[0m");

        } else if (i == this.getEndy() && j == this.getEndx()) {
          System.out.print("\u001b[41m  \u001b[0m");
        } else if (maze[i][j] == 2) {
          System.out.print("\u001b[42m  \u001b[0m");
        } else if (maze[i][j] == 0) {
          System.out.print("  ");
        }
      }
      System.out.println("\u001b[40m  \u001b[0m");

    }
    for (int i = 0; i < this.x + 2; i++) {
      System.out.print("\u001b[40m  \u001b[0m");

    }
    System.out.println();
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

  public int getEndx() {
    return endx;
  }

  public int getEndy() {
    return endy;
  }
}