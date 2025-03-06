
import java.util.*;

public class Solver {
  private final List<Node> open;
  private final List<Node> closed;
  private final List<Node> path;
  private final int[][] maze;
  private Node now;
  private final int xstart;
  private final int ystart;
  private int xend, yend;
  private final boolean diag;

  

  public Solver(final int[][] maze, final int xstart, final int ystart, final boolean diag) {
    this.open = new ArrayList<Node>();
    this.closed = new ArrayList<Node>();
    this.path = new ArrayList<Node>();
    this.maze = maze;
    this.now = new Node(null, xstart, ystart, 0, 0);
    this.xstart = xstart;
    this.ystart = ystart;
    this.diag = diag;
  }

  public List<Node> findPathTo(final int xend, final int yend) {
    this.xend = xend;
    this.yend = yend;
    this.closed.add(this.now);
    addNeigborsToOpenList();
    while (this.now.x != this.xend || this.now.y != this.yend) {
      if (this.open.isEmpty()) { 
        return null;
      }
      this.now = this.open.get(0); 
      this.open.remove(0);
      this.closed.add(this.now);
      addNeigborsToOpenList();
    }
    this.path.add(0, this.now);
    while (this.now.x != this.xstart || this.now.y != this.ystart) {
      this.now = this.now.parent;
      this.path.add(0, this.now);
    }
    return this.path;
  }

  public void printMaze(final int[][] data){
    if (path != null) {
      path.forEach((n) -> {
        data[n.y][n.x] = -1;
      });
      System.out.printf("\nTotal Moves: %.00f\n", path.get(path.size() - 1).g);

      for (int i = 0; i < data[0].length + 2; i++) {
        System.out.print("\u001b[40m  \u001b[0m");
      }
      System.out.println();
      for (final int[] maze_row : data) {
        System.out.print("\u001b[40m  \u001b[0m");
        for (final int maze_entry : maze_row) {
          switch (maze_entry) {
            case 0:
              System.out.print("  ");
              break;
            case -1:
              System.out.print("\u001b[43m  \u001b[0m");
              break;
            default:
              System.out.print("\u001b[40m  \u001b[0m");
          }
        }
        System.out.print("\u001b[40m  \u001b[0m");
        System.out.println();

      }
      for (int i = 0; i < data[0].length + 2; i++) {
        System.out.print("\u001b[40m  \u001b[0m");
      }
      System.out.println();
    }
  }

  private static boolean findNeighborInList(final List<Node> array, final Node node) {
    return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
  }

  private double distance(final int dx, final int dy) {
    if (this.diag) { 
      return Math.hypot(this.now.x + dx - this.xend, this.now.y + dy - this.yend);
    } else {
      return Math.abs(this.now.x + dx - this.xend) + Math.abs(this.now.y + dy - this.yend);
    }
  }

  private void addNeigborsToOpenList() {
    Node node;
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        if (!this.diag && x != 0 && y != 0) {
          continue; 
        }
        node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y));
        if ((x != 0 || y != 0)
            && this.now.x + x >= 0 && this.now.x + x < this.maze[0].length
            && this.now.y + y >= 0 && this.now.y + y < this.maze.length
            && this.maze[this.now.y + y][this.now.x + x] != -1 
            && !findNeighborInList(this.open, node) && !findNeighborInList(this.closed, node)) { 
          node.g = node.parent.g + 1.;
          node.g += maze[this.now.y + y][this.now.x + x]; 

          this.open.add(node);
        }
      }
    }
    Collections.sort(this.open);
  }
}