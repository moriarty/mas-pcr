/*
 * PCR Homework assignment 7 Due on 13/06/2013
 * Class to define the state of a Connect Four game 
 * and calculate it's getUtility function.
 */
package hw7;

/**
 *
 * @authors Iuri Andrade, Alexander Moriaty, Oscar Lima
 */
public class GameState {

  private char[][] stateMatrix;
  public static final int ROWS = 6;
  public static final int COLUMNS = 7;
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

  public void nextPlayer() {
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

  public int getUtility() {
    int utility = 0;
    char[] row;
    char[] column;
    if(winner() == player){
      return Integer.MAX_VALUE;
    } else if(winner() != ' '){
      return Integer.MIN_VALUE;
    }
    for (int i = 0; i < COLUMNS; i++) {
      column = getColumn(i);
      utility += countOneOfFour(column);
      utility += 10 * countTwoOfFour(column);
      utility += 100 * countThreeOfFour(column);
    }
    for (int i = 0; i < ROWS; i++) {
      row = stateMatrix[i];
      utility += countOneOfFour(row);
      utility += 10 * countTwoOfFour(row);
      utility += 100 * countThreeOfFour(row);
    }
    return utility;
  }

  private int countOneOfFour(char[] gameLine) {
    String line = String.valueOf(gameLine);
    String subLine;
    int count = 0;
    for (int i = 0; i < gameLine.length - 3; i++) {
      subLine = line.substring(i, i + 4);
      if (subLine.equals(player + "   ") || subLine.equals(" " + player + "  ") 
              || subLine.equals("  " + player + ' ') || subLine.equals("   " + player)) {
        count++;
      }
    }
    return count;
  }

  private int countTwoOfFour(char[] gameLine) {
    String line = String.valueOf(gameLine);
    String subLine;
    int count = 0;
    for (int i = 0; i < gameLine.length - 3; i++) {
      subLine = line.substring(i, i + 4);
      if (subLine.equals(String.valueOf(player) + String.valueOf(player) + "  ") || subLine.equals(" " + player + player + " ")
              || subLine.equals("  " + player + player) || subLine.equals(player + " " + player + " ")
              || subLine.equals(" " + player + " " + player) || subLine.equals(player + "  " + player)) {
      count++;
      }
    }
    return count;
  }

  private int countThreeOfFour(char[] gameLine) {
    String line = String.valueOf(gameLine);
    String subLine;
    int count = 0;
    for (int i = 0; i < gameLine.length - 3; i++) {
      subLine = line.substring(i, i + 4);
      if (subLine.equals(String.valueOf(player) + String.valueOf(player) + String.valueOf(player) + " ") || 
              subLine.equals(String.valueOf(player) + String.valueOf(player) + " " + String.valueOf(player))
              || subLine.equals(String.valueOf(player) + " " + String.valueOf(player) + String.valueOf(player)) 
              || subLine.equals(" " + String.valueOf(player) + String.valueOf(player) + String.valueOf(player))) {
      count++;
      }
    }
    return count;
  }

  private char[] getColumn(int n) {
    char[] column = new char[ROWS];
    for (int i = 0; i < ROWS; i++) {
      column[i] = stateMatrix[i][n];
    }
    return column;
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

  //Check winner part
  //==================================================================
  //method used to check if there is a winner, return who wins, ' ' for not winner yet
  public char winner() {
    if (marble_winner('X')) {
      return 'X';
    } else if (marble_winner('O')) {
      return 'O';
    } else {
      return ' ';
    }
  }

  //method used to check if there is a winner in a horizontal line or vertical line given a certain marble
  private boolean marble_winner(char marble) {

    //horizontal decomposition
    for (int i = 0; i < ROWS; i++) {
      char[] vector = new char[COLUMNS];

      for (int j = 0; j < COLUMNS; j++) {
        vector[j] = stateMatrix[i][j];
      }
      if (vector_winner_checker(vector, marble)) {
        return true;
      }
    }

    //vertical decomposition
    for (int j = 0; j < COLUMNS; j++) {
      char[] vector = new char[ROWS];

      for (int i = 0; i < ROWS; i++) {
        vector[i] = stateMatrix[i][j];
      }
      if (vector_winner_checker(vector, marble)) {
        return true;
      }
    }

    //diagonal decomposition '\' type
    char[][] vector1 = new char[ROWS * 2][ROWS];

    //initialize vector1
    for (int i = 0; i < ROWS * 2; i++) {
      for (int j = 0; j < ROWS; j++) {
        vector1[i][j] = ' ';
      }
    }

    //first decomposition (1/2)
    for (int i = 0; i < COLUMNS; i++) {
      for (int k = 0; k <= i; k++) {
        //checking for index to be inside of matrix boundaries, (issue for non square matrix)
        if (k < ROWS) //k limited to game_y_value (5 value)
        {
          vector1[i][k] = stateMatrix[k][i - k];
        }
      }
    }

    //second decomposition (2/2)
    for (int i = 1; i < ROWS; i++) {
      int x = ROWS; //6
      for (int k = i; k < ROWS; k++) {
        vector1[i + 6][k] = stateMatrix[k][x--];
      }
    }

    //sending vectors to analyze if there is winner or not
    for (int i = 0; i < ROWS * 2; i++) {
      char[] vector = new char[ROWS];

      for (int j = 0; j < ROWS; j++) {
        vector[j] = vector1[i][j];
      }
      if (vector_winner_checker(vector, marble)) {
        return true;
      }
    }

    //diagonal decomposition '/' type
    char[][] vector2 = new char[ROWS * 2][ROWS];

    //initialize vector2
    for (int i = 0; i < ROWS * 2; i++) {
      for (int j = 0; j < ROWS; j++) {
        vector2[i][j] = ' ';
      }
    }

    //first decomposition (1/2)
    for (int i = 0; i < COLUMNS; i++) {
      for (int k = 0; k <= ROWS - i; k++) {
        if (k < ROWS) {
          vector2[i][k] = stateMatrix[k][k + i];
        }
      }
    }

    //second decomposition (2/2)
    for (int i = 1; i < ROWS; i++) {
      int x = 0;
      for (int k = i; k < ROWS; k++) {
        vector2[i + 6][k] = stateMatrix[k][x++];
      }
    }

    //sending vectors to analyze if there is winner or not
    for (int i = 0; i < ROWS * 2; i++) {
      char[] vector = new char[ROWS];

      for (int j = 0; j < ROWS; j++) {
        vector[j] = vector2[i][j];
      }
      if (vector_winner_checker(vector, marble)) {
        return true;
      }
    }

    return false;
  }

  //checker method to analyze if a vector is containing the winner or not, returns true if there is 4 marbles in a row
  private boolean vector_winner_checker(char vector[], char marble) {

    //checking for 4 continuous marbles in a vector
    int counter = 0;

    for (int i = 0; i < vector.length; i++) {
      if (vector[i] == marble) {
        counter++;
      } else {
        counter = 0;
      }
      if (counter > 3) {
        return true;
      }
    }

    return false;
  }

  private void setPlayer(char player){
    this.player = player;
  }
  
  private void setMatrix(char[][] matrix){
    this.stateMatrix = matrix;
  }
  
  public GameState getCopy() {
    GameState state = new GameState();
    state.setPlayer(player);
    char[][] matrix = new char[ROWS][COLUMNS];
    for(int i = 0;i<ROWS;i++)
      System.arraycopy(this.stateMatrix[i], 0, matrix[i], 0, COLUMNS);
    state.setMatrix(matrix);
    return state;
  }
}
