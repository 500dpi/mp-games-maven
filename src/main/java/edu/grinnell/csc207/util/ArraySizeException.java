package edu.grinnell.csc207.util;

/**
 * Thrown to indicate that the size of an array is incorrect (e.g.,
 * if we are expecting an array of size 10 and receive one of size 11).
 *
 * @author Samuel A. Rebelsky
 */
public class ArraySizeException extends Exception {
  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Build a new exception with the default message.
   */
  public ArraySizeException() {
    super("Incorrect array size");
  } // ArraySizeException

  /**
   * Build a new exception with a particular message.
   *
   * @param message
   *   The accompanying message.
   */
  public ArraySizeException(String message) {
    super(message);
  } // ArraySizeException(String)
} // class ArraySizeException
