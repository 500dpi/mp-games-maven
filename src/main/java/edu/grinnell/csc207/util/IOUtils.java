package edu.grinnell.csc207.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;

/**
 * A variety of utilities for getting input.
 *
 * @author Samuel A. Rebelsky
 */
public class IOUtils {
  // +---------------+-----------------------------------------------
  // | Local helpers |
  // +---------------+

  // +------------------+--------------------------------------------
  // | Provided methods |
  // +------------------+

  /**
   * Repeatedly prompt for a command until one is returned.
   *
   * @param pen
   *   Where to print the prompt.
   * @param eyes
   *   How to read input.
   * @param prompt
   *   The prompt to print.
   * @param commands
   *   The valid commands.
   *
   * @return the command entered.
   *
   * @throws IOException
   *   If an I/O exception occurs.
   */
  public static String readCommand(PrintWriter pen, BufferedReader eyes,
      String prompt, String[] commands) throws IOException {
    String command = "";
    boolean done = false;
    while (!done) {
      pen.print(prompt);
      pen.flush();
      command = eyes.readLine();
      if (ArrayUtils.arrayContainsCI(commands, command)) {
        done = true;
      } else {
        pen.printf("Invalid command: '%s'\n", command);
        pen.printf("Valid commands: %s\n", Arrays.toString(commands));
      } // if/else
    } // while
    return command;
  } // readCommand(PrintWrtier, BufferedReader, String, String[])

  /**
   * Read an integer.
   *
   * @param pen
   *   Where to print the prompt.
   * @param eyes
   *   How to read input.
   * @param prompt
   *   The prompt to print.
   *
   * @return the integer read
   *
   * @throws IOException
   *   If an I/O exception occurs.
   */
  public static int readInt(PrintWriter pen, BufferedReader eyes,
      String prompt) throws IOException {
    int result = 0;
    boolean done = false;
    while (!done) {
      pen.print(prompt);
      pen.flush();
      String response = eyes.readLine();
      try {
        result = Integer.parseInt(response);
        done = true;
      } catch (NumberFormatException e) {
        pen.printf("I'm sorry, but I can't interpret '%s'\n", response);
      } // try/catch
    } // while
    return result;
  } // readInt(PrintWriter, BufferedReader, String)

  /**
   * Read an integer within a particular range.
   *
   * @param pen
   *   Where to print the prompt.
   * @param eyes
   *   How to read input.
   * @param prompt
   *   The prompt to print.
   * @param lower
   *   The lower bound (inclusive).
   * @param upper
   *   The upper bound (exclusive).
   *
   * @return the integer read
   *
   * @throws IOException
   *   If an I/O exception occurs.
   */
  public static int readInt(PrintWriter pen, BufferedReader eyes,
      String prompt, int lower, int upper) throws IOException {
    int result = 0;
    boolean done = false;
    while (!done) {
      pen.print(prompt);
      pen.flush();
      String response = eyes.readLine();
      try {
        result = Integer.parseInt(response);
        if ((result >= lower) && (result < upper)) {
          done = true;
        } else {
          pen.printf("I'm sorry, but %d is outside the range %d to %d\n",
              result, lower, upper - 1);
        } // if/else
      } catch (NumberFormatException e) {
        pen.printf("I'm sorry, but I can't interpret '%s'\n", response);
      } // try/catch
    } // while
    return result;
  } // readInt(PrintWriter, BufferedReader, String)
} // class IOUtils
