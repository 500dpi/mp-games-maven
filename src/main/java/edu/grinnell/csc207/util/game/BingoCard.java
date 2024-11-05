package edu.grinnell.csc207.util.game;

import edu.grinnell.csc207.util.matrix.Matrix;
import edu.grinnell.csc207.util.matrix.MatrixV0;
import edu.grinnell.csc207.util.matrix.ArraySizeException;

import java.util.Random;

public class BingoCard {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The bingo card.
   */
  protected Matrix<Integer> card;

  /**
   * The dimensions of the square bingo card.
   */
  protected int dimensions;

  /**
   * To generate randomized bingo card values.
   */
  protected Random randomCard;

  // +-------------+-------------------------------------------------
  // | Constructor |
  // +-------------+

  public BingoCard(int dimen) {
    this.dimensions = dimen;
    this.card = new MatrixV0<Integer>(dimen, dimen, 0);
    // + Something with a random seed idk
  } // BingoCard(int)
  
  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * If the value matches a cell on the bingo card, that cells is
   * crossed out.
   *
   * @param card
   *    The value on the bingo card's grid.
   * @param val
   *    The value to compare to.
   */
  public void match(int val) {
    return;
  } // match()

  /**
   * Horizontal bingo win.
   *
   * @return
   *    True or false if there is a line of bingos.
   */
  public boolean horizWin() {
    return false;
  } // horizWin()

  /**
   * Vertical bingo win.
   *
   * @return
   *    True or false if there is a line of bingos.
   */
  public boolean vertWin() {
    return false;
  } // vertWin()

  /**
   * Diagonal bingo win.
   *
   * @return
   *    True or false if there is a line of bingos.
   */
  public boolean diagWin() {
    return false;
  } // diagWin()

  // +-------+-------------------------------------------------------
  // | Extra |
  // +-------+

  /**
   * Determine if the bingo card is fully blacked-out.
   *
   * @return
   *    True or false if the entire card if full of bingos.
   */
  public boolean blackout() {
    return false;
  } // blackout()
} // BingoCard class
