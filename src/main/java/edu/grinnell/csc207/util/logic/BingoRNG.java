package edu.grinnell.csc207.util.logic;

import java.util.Random;

import edu.grinnell.csc207.util.matrix.Matrix;

/**
 * Random number generation for bingo.
 *
 * @author Sal Karki
 * @author Sara Jaljaa
 */
public final class BingoRNG {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * Random bingo values called.
   */
  private Random callRng;

  /**
   * Randomized grid values for the bingo card.
   */
  private Random gridRng;

  /**
   * The maximum bound for bingo calls (inclusive).
   */
  private Integer bound;

  // +--------------+-------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Random numbers for a bingo card & game.
   *
   * @param dimen
   *    The dimensions of the bingo card.
   */
  public BingoRNG(Integer dimen) {
    this.callRng = new Random();
    this.gridRng = new Random();
    this.bound = (dimen * dimen) * 3;
  } // BingoNumbers()

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Get a random value for a bingo call.
   *
   * @return
   *    An integer random value.
   */
  public int getRandom() {
    return this.callRng.nextInt(this.bound) + 1;
  } // getRandom()

  /**
   * Randomizes a column of the bingo grid.
   *
   * @param card
   *    The matrix that stores the bingo card values.
   * @return
   *    A randomized array of Integers.
   */
  public Integer[] randomCard(Matrix<Integer> card) {
    Integer[] grid = new Integer[card.height()];

    for (int i = 0; i < grid.length; i++) {
      grid[i] = (Integer) this.gridRng.nextInt(
          3 * (grid.length * grid.length)) + 1;
    } // for
    return grid;
  } // randomize(Integer[])
} // class BingoNumbers
