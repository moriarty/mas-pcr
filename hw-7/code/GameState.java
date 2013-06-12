/*
 * PCR Homework assignment 7 Due on 13/06/2013
 * Class to define the state of a Connect Four game 
 * and calculate it's utility function.
 */
package hw7;

/**
 *
 * @authors Iuri Andrade, Alexander Moriaty, Oscar Lima
 */
public class GameState {

  private char[][] stateMatrix;
  private static final int ROWS = 6;
  private static final int COLUMNS = 7;
  private char player;

  public GameState() {
    setInitialState();
    player = 'O';
  }

  public boolean dropTile(int col) {
    int freeRow = getFreeRow(col);
    if (freeRow != -1) {
      stateMatrix[freeRow][col] = player;
      nextPlayer();
      return true;
    }
    return false;
  }

  private void nextPlayer() {
    if (player == 'O') {
      player = 'X';
    } else {
      player = 'O';
    }
  }

  private void setInitialState() {
    this.stateMatrix = new char[ROWS][COLUMNS];
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS; j++) {
        this.stateMatrix[i][j] = ' ';
      }
    }
  }

  public int getFreeRow(int col) {
    if (isColumnFree(col)) {
      for (int i = 0; i < ROWS; i++) {
        if (this.stateMatrix[i][col] == ' ') {
          return i;
        }
      }
    }
    return -1;
  }

  private boolean isColumnFree(int col) {
    if (this.stateMatrix[ROWS - 1][col] == ' ') {
      return true;
    }
    return false;
  }

  public int utility() {
    //TODO Calculate the utility value for the state.
    return 0;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("\n");
    for (int i = ROWS - 1; i >= 0; i--) {
      str.append("|");
      for (int j = 0; j < COLUMNS; j++) {
        str.append(this.stateMatrix[i][j]);
      }
      str.append("|\n");
    }
    str.append(" -------");
    return str.toString();
  }
}
