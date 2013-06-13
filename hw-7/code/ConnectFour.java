/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hw7;

/**
 *
 * @authors Iuri Andrade, Alexander Moriaty, Oscar Lima
 */
public class ConnectFour {
   GameState game;
  
  
  public ConnectFour(){
    game = new GameState();
    
  }
  
  public boolean play(int col){
    
    return game.dropTile(col);
    
  }
  
  public GameState getState(){
    return game;
  }
  
  @Override
  public String toString(){
    return game.toString();
  }
}
