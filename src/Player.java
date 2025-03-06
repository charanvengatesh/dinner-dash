public class Player {
  
  public int x;
  public int y;
  public int moves;

  public Player(){
    this.x = 0;
    this.y = 0;
    this.moves = 0;
  }

  public void down(int[][] maze){
    if (maze[this.y + 1][this.x] == 0) {
      maze[this.y][this.x] = 0;
      this.y += 1;
      maze[this.y][this.x] = 2;
      this.moves++;
    } else {
      System.out.println("cant");
    }
  }

  public void up(int[][] maze) {
    if (maze[this.y - 1][this.x] == 0) {
      maze[this.y][this.x] = 0;
      this.y -= 1;
      maze[this.y][this.x] = 2;
      this.moves++;
    } else {
      System.out.println("cant");
    }
  }

  public void right(int[][] maze) {
    if (maze[this.y][this.x + 1] == 0) {
      maze[this.y][this.x] = 0;
      this.x += 1;
      maze[this.y][this.x] = 2;
      this.moves++;
    } else {
      System.out.println("cant");
    }
  }

  public void left(int[][] maze) {
    if (maze[this.y][this.x - 1] == 0) {
      maze[this.y][this.x] = 0;
      this.x -= 1;
      maze[this.y][this.x] = 2;
      this.moves++;
    } else {
      System.out.println("cant");
    }
  }


}