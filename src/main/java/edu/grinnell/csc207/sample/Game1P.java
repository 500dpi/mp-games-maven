package edu.grinnell.csc207.sample;

import edu.grinnell.csc207.util.ArrayUtils;
import edu.grinnell.csc207.util.IOUtils;
import edu.grinnell.csc207.util.matrix.Matrix;
import edu.grinnell.csc207.util.matrix.MatrixV0;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A sample one-player game (is that a puzzle?). Intended as a potential
 * use of our Matrix interface.
 *
 * @author Samuel A. Rebelsky
 */
public class Game1P {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default width.
   */
  static final int DEFAULT_WIDTH = 10;

  /**
   * The default number of rows.
   */
  static final int DEFAULT_HEIGHT = 8;

  // +----------------+----------------------------------------------
  // | Helper methods |
  // +----------------+

  /**
   * Print the insturctions.
   *
   * @param pen
   *  The printwriter used to print the instructions.
   */
  public static void printInstructions(PrintWriter pen) {
    pen.println("""
                Welcome to the sample one-player game.

                Command-line arguments:

                * -w width - set up the width of the board
                * -h height - set up the height of the board
                * -s game-number - choose the game setup number (useful if
                  you want to play the same setup multiple times).

                Your game board is a grid of X's, O's, *'s, and blanks.

                Your goal is to eliminate as many X's as possible while
                keeping as many O's as possible.

                You have four basic moves. You can do each move up to
                three times. You can also SKIP a step and just let the
                *'s destroy things.

                * RR: remove a row
                * RC: remove a column
                * IR: insert a blank row
                * IC: insert a blank column
                * SKIP: Do nothing, just let the *'s move.

                After each move, any *'s eliminate one neighboring piece
                and move over that piece, using the following priority grid.

                    1|6|7
                    -+-+-
                    5|*|4
                    -+-+-
                    8|3|2
                """);
  } // printInstructions(PrintWriter)

  /**
   * Print the results of the game.
   *
   * @param pen
   *   What to use for printing.
   * @param board
   *   The game board at the end.
   */
  static void printResults(PrintWriter pen, Matrix<String> board) {
    int xs = 0;
    int os = 0;
    for (int row = 0; row < board.height(); row++) {
      for (int col = 0; col < board.width(); col++) {
        String cell = board.get(row, col);
        if ("O".equals(cell)) {
          ++os;
        } else if ("X".equals(cell)) {
          ++xs;
        } // if/else
      } // for
    } // for
    pen.println();
    pen.println("Xs remaining: " + xs);
    pen.println("Os remaining: " + os);
    pen.println("Score: " + (os - xs));
  } // printResults

  /**
   * Process the board, eliminating any matching cells. (The efficiency
   * of this method could be improved.)
   *
   * @param board
   *   The board to process.
   */
  static void process(Matrix<String> board) {
    int[][] offsets = new int[][] {{-1, -1}, {1, 1}, {1, 0}, {0, 1},
        {0, -1}, {-1, 0}, {-1, 1}, {1, -1}};
    String newStar = ".";       // The new place a star moves.
    String oldStar = ":";       // The place the star was.
    String repStar = "@";       // A star about to be overwritten.

    // Run through all the cells, looking for *'s.
    for (int row = 0; row < board.height(); row++) {
      for (int col = 0; col < board.width(); col++) {
        String cell = board.get(row, col);
        if (cell.equals("*") || cell.equals("repStar")) {
          for (int[] offset : offsets) {
            String neighbor = "";
            int newrow = row + offset[0];
            int newcol = col + offset[1];
            try {
              neighbor = board.get(newrow, newcol);
              if (neighbor.equals("X") || neighbor.equals("O")) {
                board.set(row, col, oldStar);
                board.set(newrow, newcol, newStar);
                break; // escape from the loop
              } // if/else
            } catch (IndexOutOfBoundsException e) {
              // No neighbor, go on.
            } // try/catch
          } // for offset
        } // if
      } // for col
    } // for row

    // Now get rid of temporary things.
    for (int row = 0; row < board.height(); row++) {
      for (int col = 0; col < board.width(); col++) {
        String cell = board.get(row, col);
        if (cell.equals(oldStar)) {
          board.set(row, col, " ");
        } else if (cell.equals(newStar) || cell.equals(repStar)) {
          board.set(row, col, "*");
        } // if/else
      } // for
    } // for
  } // process(Matrix<String>)

  /**
   * Set up a new board.
   *
   * @param width
   *   The width of the board.
   * @param height
   *   The height of the board.
   * @param game
   *   The game number.
   *
   * @return the newly created board
   */
  static Matrix<String> setupBoard(int width, int height, int game) {
    Random setup = new Random(game);
    Matrix<String> board = new MatrixV0<String>(width, height, " ");
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        double rand = setup.nextDouble();
        if (rand < 0.10) {
          board.set(row, col, "*");
        } else if (rand < 0.25) {
          board.set(row, col, "X");
        } else if (rand < 0.4) {
          board.set(row, col, "O");
        } // if/else
      } // for col
    } // for row
    return board;
  } // setupBoard(int, int, int)

  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Run the game.
   *
   * @param args
   *   Command-line arguments.
   */
  public static void main(String[] args) throws IOException {
    PrintWriter pen = new PrintWriter(System.out, true);
    BufferedReader eyes = new BufferedReader(new InputStreamReader(System.in));

    int rrRemaining = 2;
    int rcRemaining = 2;
    int icRemaining = 2;
    int irRemaining = 2;
    int width = DEFAULT_WIDTH;
    int height = DEFAULT_HEIGHT;
    Random rand = new Random();
    int game = rand.nextInt();

    // Process the command line
    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-w":
          try {
            width = Integer.parseInt(args[++i]);
          } catch (Exception e) {
            System.err.printf("Invalid width: %s (not an integer)\n", args[i]);
            return;
          } // try/catch
          if (width < 4) {
            System.err.printf("Invalid width: %s (less than 4)\n", width);
            return;
          } // if
          break;

        case "-h":
          try {
            height = Integer.parseInt(args[++i]);
          } catch (Exception e) {
            System.err.printf("Invalid height: %s (not an integer)\n", args[i]);
            return;
          } // try/catch
          if (height < 4) {
            System.err.printf("Invalid height: %s (less than 4)\n", height);
            return;
          } // if
          break;

        case "-s":
          try {
            game = Integer.parseInt(args[++i]);
          } catch (Exception e) {
            System.err.printf("Invalid game number: %s\n", args[i]);
            return;
          } // try/catch
          break;

        default:
          System.err.printf("Invalid command-line flag: %s\n", args[i]);
          return;
      } // switch
    } // for

    // Get started
    printInstructions(pen);
    pen.print("Hit return to continue");
    pen.flush();
    eyes.readLine();

    // Set up the board
    Matrix<String> board = setupBoard(width, height, game);

    // Run the game
    pen.println("Game setup number " + game);
    pen.println();

    String[] commands = new String[] {"RR", "RC", "IR", "IC", "SKIP", "UNDO", "DONE"};
    boolean done = false;
    Matrix<String> prev = board.clone();
    while (!done) {
      Matrix.print(pen, board, true);
      String command = IOUtils.readCommand(pen, eyes, "Action: ", commands);
      switch (command.toUpperCase()) {
        case "RR":
          if (rrRemaining < 0) {
            pen.println("Sorry, you've used up your remove row commands.");
            break;
          } // if
          --rrRemaining;
          if (rrRemaining <= 0) {
            commands = ArrayUtils.removeAll(commands, "RR");
          } // if
          int rowToRemove =
              IOUtils.readInt(pen, eyes, "Row: ", 0, board.height());
          prev = board.clone();
          board.deleteRow(rowToRemove);
          process(board);
          break;

        case "RC":
          if (rcRemaining < 0) {
            pen.println("Sorry, you've used up your remove column commands.");
            break;
          } // if
          --rcRemaining;
          if (rcRemaining <= 0) {
            commands = ArrayUtils.removeAll(commands, "RC");
          } // if
          int colToRemove =
              IOUtils.readInt(pen, eyes, "Column: ", 0, board.width());
          prev = board.clone();
          board.deleteCol(colToRemove);
          process(board);
          break;

        case "IR":
          if (irRemaining < 0) {
            pen.println("Sorry, you've used up your insert row commands.");
            break;
          } // if
          --irRemaining;
          if (irRemaining <= 0) {
            commands = ArrayUtils.removeAll(commands, "IR");
          } // if
          int rowToInsert =
              IOUtils.readInt(pen, eyes, "Row: ", 0, board.height() + 1);
          prev = board.clone();
          board.insertRow(rowToInsert);
          process(board);
          break;

        case "IC":
          if (icRemaining < 0) {
            pen.println("Sorry, you've used up your insert row commands.");
            break;
          } // if
          --icRemaining;
          if (icRemaining <= 0) {
            commands = ArrayUtils.removeAll(commands, "IC");
          } // if
          int colToInsert =
              IOUtils.readInt(pen, eyes, "Column: ", 0, board.width() + 1);
          prev = board.clone();
          board.insertCol(colToInsert);
          process(board);
          break;

        case "DONE":
          done = true;
          break;

        case "SKIP":
          prev = board.clone();
          process(board);
          break;

        case "UNDO":
          if (board == prev) {
            pen.println("Sorry: There's only one level of undo.");
          } else {
            board = prev;
          } // if/else
          break;

        default:
          pen.printf("Unexpected command: '%s'. Please try again.\n", command);
          break;
      } // switch
    } // while

    // Print final results
    printResults(pen, board);

    // And we're done
    pen.close();
  } // main(String[])
} // class Game1P
