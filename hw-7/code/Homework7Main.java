/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hw7;

/**
 *
 * @authors Iuri Andrade, Alexander Moriaty, Oscar Lima
 */
public class Homework7Main {

  public static void main(String args[]) {
    ConnectFour game = new ConnectFour();
    game.play(max(game.getState().getCopy()));
    game.play(max(game.getState().getCopy()));
    System.out.println(game);
    //System.out.println(state.getUtility());
    

  }
  
  private static int max(GameState state) {
    int bestCol = 0;
    int utility = Integer.MIN_VALUE;
    GameState tempState;
    tempState = state.getCopy();
    for (int i = 0; i < GameState.COLUMNS; i++) {
      if (tempState.dropTile(i)) {
        tempState.dropTile(min(tempState.getCopy()));
        if (utility < tempState.getUtility()) {
          utility =  tempState.getUtility();
          bestCol = i;
        }
      }
    }
    System.out.println("Max utility = " + utility);
    return bestCol;
  }
  
  private static int min(GameState state) {
    int bestCol = 0;
    int utility = Integer.MAX_VALUE;
    GameState tempState = state.getCopy();
    for (int i = 0; i < GameState.COLUMNS; i++) {
      if (tempState.dropTile(i)) {
        tempState.dropTile(max(tempState.getCopy()));
        if (utility > -tempState.getUtility()) {
          utility = -tempState.getUtility();
          bestCol = i;
        }
      }
    }
    System.out.println("Min utility = " + utility);
    return bestCol;
  }
}
