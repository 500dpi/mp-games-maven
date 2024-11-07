package edu.grinnell.csc207.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import edu.grinnell.csc207.util.logic.BingoCard;
import edu.grinnell.csc207.util.logic.BingoPrint;

import edu.grinnell.csc207.util.matrix.ArraySizeException;
import edu.grinnell.csc207.util.matrix.Matrix;
import edu.grinnell.csc207.util.matrix.MatrixV0;

/**
 * Tests for all bingo-related functions.
 *
 * @author Sara Jaljaa
 */
public class BingoTest {

  // +---------+-----------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Create a new 4x4 matrix that is filled with a value.
   *
   * @param val
   *    The default fill value.
   * @return
   *    A matrix array.
   */
  public static Matrix<Integer> example(int val) {
    return new MatrixV0<Integer>(4, 4, val);
  } // example(int)

  // +-----------------------+---------------------------------------
  // | Printing/String Tests |
  // +-----------------------+

  /**
   * Simple print/image test for cards filled with
   * a single value.
   */
  @Test
  public void simpleCardTest() {
    Matrix<Integer> card = example(0);
    BingoCard bingo = new BingoCard(card);

    // Empty card
    assertEquals(
        "+----+----+----+----+\n" +
        "| XX | XX | XX | XX |\n" +
        "+----+----+----+----+\n" +
        "| XX | XX | XX | XX |\n" +
        "+----+----+----+----+\n" +
        "| XX | XX | XX | XX |\n" +
        "+----+----+----+----+\n" +
        "| XX | XX | XX | XX |\n" +
        "+----+----+----+----+\n",
        BingoPrint.cardToString(bingo),
        "Printed card correctly.");

    Matrix<Integer> card2 = example(5);
    BingoCard bingo2 = new BingoCard(card2);

    // Only single digit card (spacing test)
    assertEquals(
        "+----+----+----+----+\n" +
        "| 05 | 05 | 05 | 05 |\n" +
        "+----+----+----+----+\n" +
        "| 05 | 05 | 05 | 05 |\n" +
        "+----+----+----+----+\n" +
        "| 05 | 05 | 05 | 05 |\n" +
        "+----+----+----+----+\n" +
        "| 05 | 05 | 05 | 05 |\n" +
        "+----+----+----+----+\n",
        BingoPrint.cardToString(bingo2),
        "Printed card 2 correctly.");

    Matrix<Integer> card3 = example(73);
    BingoCard bingo3 = new BingoCard(card3);

    // Only double digit card
    assertEquals(
        "+----+----+----+----+\n" +
        "| 73 | 73 | 73 | 73 |\n" +
        "+----+----+----+----+\n" +
        "| 73 | 73 | 73 | 73 |\n" +
        "+----+----+----+----+\n" +
        "| 73 | 73 | 73 | 73 |\n" +
        "+----+----+----+----+\n" +
        "| 73 | 73 | 73 | 73 |\n" +
        "+----+----+----+----+\n",
        BingoPrint.cardToString(bingo3),
        "Printed card 3 correctly.");
  } // simpleCardTest()

  /**
   * Tests print/images of bingo cards with different
   * cell values (no bingo values).
   */
  @Test
  public void normalCardTest() {
    // (Random) column values for bingo card
    Integer[] arr1 = { 3, 12, 88, 4 };
    Integer[] arr2 = { 54, 8, 21, 90 };
    Integer[] arr3 = { 33, 76, 49, 5 };
    Integer[] arr4 = { 87, 33, 65, 44 };

    Matrix<Integer> card = new MatrixV0<Integer>(0, 4);

    // Inserting values by column
    try {
    card.insertCol(0, arr4);
    card.insertCol(0, arr1);
    card.insertCol(1, arr2);
    card.insertCol(2, arr3);
    } catch (ArraySizeException e) {
      fail("Indexed column out of bounds.");
    } // try/catch

    BingoCard bingo = new BingoCard(card);

    assertEquals(
        "+----+----+----+----+\n" +
        "| 03 | 54 | 33 | 87 |\n" +
        "+----+----+----+----+\n" +
        "| 12 | 08 | 76 | 33 |\n" +
        "+----+----+----+----+\n" +
        "| 88 | 21 | 49 | 65 |\n" +
        "+----+----+----+----+\n" +
        "| 04 | 90 | 05 | 44 |\n" +
        "+----+----+----+----+\n",
        BingoPrint.cardToString(bingo),
        "Successful 'random' card.");
  } // normalCardTest()

  /**
   * Tests for bingo card images that have single
   * digits, double digits, and marked bingo values.
   */
  @Test
  public void bingoCard() {
    // FIX
  } // bingoCard()

  // +-------------+-------------------------------------------------
  // | Match Tests |
  // +-------------+

  /**
   * Tests that a bingo value is correctly matched and marked off.
   */
  @Test
  public void matchTest() {
    Matrix<Integer> ex0 = example(77);
    BingoCard bingo = new BingoCard(ex0);
    int[] vals = { 11, 12, 34, 86, 99 };

    /*     ex0     */
    /* 77 12 86 77 */
    /* 77 77 77 77 */
    /* 77 34 77 99 */
    /* 11 77 77 77 */

    ex0.set(0, 1, 12);
    ex0.set(3, 0, 11);
    ex0.set(2, 1, 34);
    ex0.set(0, 2, 86);
    ex0.set(2, 3, 99);

    for (int i = 0; i < vals.length; i++) {
      assertEquals(
          true,
          bingo.match(vals[i]),
          "Values match the bingo card.");
    } // for

    assertEquals(
        true,
        (ex0.get(0, 1) == 0),
        "12 has been marked as a bingo cell.");

    assertEquals(
        true,
        (ex0.get(3, 0) == 0),
        "11 has been marked as a bingo cell.");

    assertEquals(
        false,
        (ex0.get(2, 1) == 34),
        "Value has been correctly updated.");

    assertEquals(
        true,
        (ex0.get(2, 1) == 0),
        "34 has been marked as a bingo cell.");

    assertEquals(
        true,
        (ex0.get(0, 2) == 0),
        "86 has been marked as a bingo cell.");

    assertEquals(
        false,
        (ex0.get(2, 3) == 99),
        "Value has been correctly updated.");

    assertEquals(
        true,
        (ex0.get(2, 3) == 0),
        "99 has been marked as a bingo cell.");
  } // matchTest()

  // +------------------------+--------------------------------------
  // | Horizontal Bingo Tests |
  // +------------------------+

  /**
   * Simple horizontal bingo test.
   */
  @Test
  public void horizontalBingoTest() {
    Matrix<Integer> ex1 = example(52);
    BingoCard bingo = new BingoCard(ex1);

    // Set row 2 to bingo/0
    for (int i = 0; i < ex1.height(); i++) {
      ex1.set(2, i, 0);
    } // for

    assertEquals(
        true,
        bingo.horizWin(),
        "Successful horizontal bingo in a single row.");
  } // horizontalBingoTest()

  /**
   * Tests for four in a row between adjacent rows.
   */
  @Test
  public void horizontalEdgeTest() {
    Matrix<Integer> ex2 = example(11);
    BingoCard bingo1 = new BingoCard(ex2);

    Matrix<Integer> ex3 = example(25);
    BingoCard bingo2 = new BingoCard(ex3);

    /*     ex2     */
    /* 11 11 11 11 */
    /* 11 XX XX XX */
    /* XX 11 11 11 */
    /* 11 11 11 11 */

    ex2.set(1, 1, 0);
    ex2.set(1, 2, 0);
    ex2.set (1, 3, 0);
    ex2.set(2, 0, 0);

    /*     ex3     */
    /* 25 XX XX XX */
    /* XX XX XX 25 */
    /* 25 25 25 XX */
    /* XX XX XX 25 */

    ex3.set(0, 1, 0);
    ex3.set(0, 2, 0);
    ex3.set(0, 3, 0);
    ex3.set(1, 0, 0);
    ex3.set(1, 1, 0);
    ex3.set(1, 2, 0);
    ex3.set(2, 3, 0);
    ex3.set(3, 0, 0);
    ex3.set(3, 1, 0);
    ex3.set(3, 2, 0);

    assertEquals(
        false,
        bingo1.horizWin(),
        "No bingo found in bingo1.");

    assertEquals(
        false,
        bingo2.horizWin(),
        "No bingo found in bingo2.");
  } // horizontalEdgeTest()

  // +----------------------+----------------------------------------
  // | Vertical Bingo Tests |
  // +----------------------+

  /**
   * Simple vertical bingo test.
   */
  @Test
  public void verticalBingoTest() {
    Matrix<Integer> ex4 = example(33);
    BingoCard bingo = new BingoCard(ex4);

    // Set column 3 to bingo/0
    for (int i = 0; i < ex4.height(); i++) {
      ex4.set(i, 3, 0);
    } // for

    assertEquals(
        true,
        bingo.vertWin(),
        "Successful vertical bingo in a single column.");
  } // verticalBingoTest()

  /**
   * Tests for four in a vertical line between adjacent columns.
   */
  @Test
  public void verticalEdgeTest() {
    Matrix<Integer> ex5 = example(21);
    BingoCard bingo1 = new BingoCard(ex5);

    Matrix<Integer> ex6 = example(40);
    BingoCard bingo2 = new BingoCard(ex6);

    /*     ex5     */
    /* 21 XX 21 XX */
    /* 21 XX XX 21 */
    /* 21 XX 21 21 */
    /* XX 21 21 21 */

    ex5.set(3, 0, 0);
    ex5.set(0, 1, 0);
    ex5.set(1, 1, 0);
    ex5.set(2, 1, 0);
    ex5.set(1, 2, 0);
    ex5.set(0, 3, 0);

    /*     ex6     */
    /* 40 40 XX 40 */
    /* 40 XX 40 40 */
    /* 40 XX XX 40 */
    /* 40 XX 40 40 */

    ex6.set(1, 1, 0);
    ex6.set(2, 1, 0);
    ex6.set(3, 1, 0);
    ex6.set(0, 2, 0);
    ex6.set(2, 2, 0);

    assertEquals(
        false,
        bingo1.vertWin(),
        "No bingo found in bingo1.");

    assertEquals(
        false,
        bingo2.vertWin(),
        "No bingo found in bingo2.");
  } // verticalEdgeTest()

  // +----------------------+----------------------------------------
  // | Diagonal Bingo Tests |
  // +----------------------+

  /**
   * Check if a card has an incomplete diagonal bingo.
   */
  @Test
  public void notDiagonalTest() {
    Matrix<Integer> ex7 = example(64);
    BingoCard bingo = new BingoCard(ex7);

    // Set an incomplete diagonal
    //
    /*     ex7     */
    /* XX 64 64 64 */
    /* 64 XX XX 64 */
    /* 64 XX XX 64 */
    /* XX 64 64 64 */

    ex7.set(0, 0, 0);
    ex7.set(1, 1, 0);
    ex7.set(2, 2, 0);
    ex7.set(3, 0, 0);
    ex7.set(2, 1, 0);
    ex7.set(1, 2, 0);

    assertEquals(
        false,
        bingo.diagWin(),
        "Incomplete diagonal bingo sequence.");
  } // notDiagonalTest()

  /**
   * Tests for a complete diagonal bingo.
   */
  @Test
  public void diagonalBingoTest() {
    Matrix<Integer> ex8 = example(89);
    BingoCard bingo1 = new BingoCard(ex8);

    Matrix<Integer> ex9 = example(56);
    BingoCard bingo2 = new BingoCard(ex9);

    // Set a forward diagonal bingo
    //
    /*     ex8     */
    /* XX 89 89 89 */
    /* 89 XX 89 89 */
    /* 89 89 XX 89 */
    /* 89 89 89 XX */

    for (int i = 0; i < bingo1.length(); i++) {
      ex8.set(i, i, 0);
    } // for

    // Set a reverse diagonal bingo
    //
    /*     ex9     */
    /* 56 56 56 XX */
    /* 56 56 XX 56 */
    /* 56 XX 56 56 */
    /* XX 56 56 56 */

    for (int i = bingo2.length() - 1; i >= 0; i--) {
      ex9.set(i, i, 0);
    } // for

    assertEquals(
        true,
        bingo1.diagWin(),
        "Successful forward diagonal bingo.");

    assertEquals(
        true,
        bingo2.diagWin(),
        "Successful reverse diagonal bingo.");
  } // diagonalBingoTest()
} // class BingoExperiment
