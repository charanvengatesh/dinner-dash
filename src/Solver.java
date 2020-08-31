import java.util.Arrays;

public class Solver {

  private char[][] maze;
  private String currPath;
  private int currX;
  private int currY;
  private boolean unsolvable;


  // constructor
  Solver(char[][] aMaze, int stX, int stY, String currentPath, boolean noSolution) {
    maze = aMaze;
    currX = stX;
    currY = stY;
    currPath = currentPath;
    unsolvable = noSolution;
  }

  // indicate taken path
  void placePlus() {
    maze[currX][currY] = '+';
  }

  // for backtracking
  void placeMinus() {
    maze[currX][currY] = '-';
  }

  // solve
  // priority in this order East, West, South, North
  void solveMaze() {
    // check for a win
    if (checkForWin()) {
      return;
    }
    // No win, so let's check for an opening
    // check east
    if (currY + 1 < maze[currX].length && checkForOpen(currX, currY + 1)) {
      currY++;
      placePlus();
      currPath += "E"; // Append East to our current path
      // recursive call continue searching
      solveMaze();
      // check west
    } else if (currY - 1 >= 0 && checkForOpen(currX, currY - 1)) {
      currY--;
      placePlus();
      currPath += "W";
      solveMaze();
      // check south
    } else if (currX + 1 < maze.length && checkForOpen(currX + 1, currY)) {
      currX++;
      placePlus();
      currPath += "S";
      solveMaze();
      // check north
    } else if (currX - 1 >= 0 && checkForOpen(currX - 1, currY)) {
      currX--;
      placePlus();
      currPath += "N";
      solveMaze();
    } else { // we've hit a dead end, we need to backtrack
      if (currPath.length() == 0) {
        // we're back at the starting point, the maze is unsolvable
        unsolvable = true;
        return;
      } else {
        // we've reached a dead end, lets backtrack
        placeMinus();
        backTrack();
      }
    }
  }

  // see if the spot at a give x, y is open
  boolean checkForOpen(int x, int y) {
    return maze[x][y] == 'O';
  }

  // see if any of the surrounding spots are the exit
  boolean checkForWin() {
    // make sure to protect against out of bounds as well
    return ((currY + 1 < maze[currX].length && maze[currX][currY + 1] == 'X')
        || (currY - 1 >= 0 && maze[currX][currY - 1] == 'X')
        || (currX + 1 < maze[currX].length && maze[currX + 1][currY] == 'X')
        || (currX - 1 >= 0 && maze[currX - 1][currY] == 'X'));
  }

  void backTrack() {
    // sanity chek currPath.length() should always be > 0 when we call backTrack
    if (currPath.length() > 0) {
      placeMinus();
      switch (currPath.charAt(currPath.length() - 1)) {
        case 'E':
          currY--;
          break;
        case 'W':
          currY++;
          break;
        case 'S':
          currX--;
          break;
        case 'N':
          currX++;
          break;
      }
      currPath = currPath.substring(0, currPath.length() - 1);
      solveMaze();
    }
  }

  void printMaze() {
    for (int i = 0; i < maze.length; i++) {
      System.out.println(Arrays.toString(maze[i]));
    }
  }

  public String getPath(){
    return currPath;
  }

}