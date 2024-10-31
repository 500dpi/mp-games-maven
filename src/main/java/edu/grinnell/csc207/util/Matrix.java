package edu.grinnell.csc207.util;

import java.io.PrintWriter;

/**
 * Two-dimensional matrices.
 *
 * @author Samuel A. Rebelsky
 *
 * @param <T>
 *   The type of value stored in the matrix.
 */
public interface Matrix<T> extends Cloneable {

  // +----------------+----------------------------------------------
  // | Static methods |
  // +----------------+

  /**
   * Convert an object to a string. Intended as an alternate to
   * obj.toString() because obj.toString() doesn't work if obj
   * is null.
   *
   * @param obj
   *   The object to convert to a string.
   *
   * @return
   *   The corresponding string.
   */
  static String toString(Object obj) {
    if (null == obj) {
      return "/";
    } else {
      return obj.toString();
    } // if/else
  } // toString(Object)

  /**
   * Print the row separator in a matrix.
   *
   * @param pen
   *   What we use for printing.
   * @param cellWidth
   *   The width of a cell.
   * @param width
   *   The width of the matrix.
   */
  static void printRowSeparator(PrintWriter pen, int cellWidth, int width) {
    for (int i = 0; i < width; i++) {
      pen.print("+" + "-".repeat(cellWidth));
    } // for
    pen.println("+");
  } // printRowSeparator(PrintWriter, int, int)

  /**
   * Print a string centered in a cellWidth box.
   *
   * @param pen
   *   What we use for printing.
   * @param str
   *   The string to print.
   * @param cellWidth
   *   The width of the box.
   */
  static void printCell(PrintWriter pen, String str, int cellWidth) {
    int len = str.length();
    if (len > cellWidth) {
      pen.println(str.substring(0, cellWidth));
    } else {
      int left = (cellWidth - len) / 2;
      int right = cellWidth - left - len;
      pen.print(" ".repeat(left) + str + " ".repeat(right));
    } // if/else
  } // printCell

  /**
   * Print a matrix (without labels).
   *
   * @param <T>
   *   The type of values stored in the matrix.
   * @param pen
   *   The PrintWriter to use for printing.
   * @param matrix
   *   The matrix to print.
   */
  public static <T> void print(PrintWriter pen, Matrix<T> matrix) {
    print(pen, matrix, false);
  } // print(PrintWriter, Matrix<T>)

  /**
   * Print a matrix, with or without labels.
   * @param <T>
   *   The type of values stored in the matrix.
   * @param pen
   *   The PrintWriter to use for printing.
   * @param matrix
   *   The matrix to print.
   * @param includeLabels
   *   Set to true if you want labels and false otherwise.
   */
  public static <T> void print(PrintWriter pen, Matrix<T> matrix,
      boolean includeLabels) {
    int width = matrix.width();
    int height = matrix.height();

    // Find the maximum width of cells.
    int cellWidth = 0;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        T val = matrix.get(row, col);
        int valWidth = toString(val).length();
        cellWidth = Math.max(cellWidth, valWidth);
      } // for col
    } // for row

    // Add some space on the sides
    cellWidth += 2;

    // Print everything out
    if (includeLabels) {
      pen.print(" ".repeat(4));
      for (int col = 0; col < width; col++) {
        printCell(pen, String.format("%2d", col), cellWidth + 1);
      } // for
      pen.println();
    } // if

    for (int row = 0; row < height; row++) {
      if (includeLabels) {
        pen.print(" ".repeat(4));
      } // if
      printRowSeparator(pen, cellWidth, width);
      if (includeLabels) {
        pen.printf(" %2d ", row);
      } // if
      for (int col = 0; col < width; col++) {
        pen.print("|");
        printCell(pen, toString(matrix.get(row, col)), cellWidth);
      } // for col
      pen.println("|");
    } // for row
    if (includeLabels) {
      pen.print(" ".repeat(4));
    } // if
    printRowSeparator(pen, cellWidth, width);
  } // print(PrintWriter, Matrix)

  // +--------------+------------------------------------------------
  // | Core methods |
  // +--------------+

  /**
   * Get the element at the given row and column.
   *
   * @param row
   *   The row of the element.
   * @param col
   *   The column of the element.
   *
   * @return the value at the specified location.
   *
   * @throws IndexOutOfBoundsException
   *   If either the row or column is out of reasonable bounds.
   */
  public T get(int row, int col);

  /**
   * Set the element at the given row and column.
   *
   * @param row
   *   The row of the element.
   * @param col
   *   The column of the element.
   * @param val
   *   The value to set.
   *
   * @throws IndexOutOfBoundsException
   *   If either the row or column is out of reasonable bounds.
   */
  public void set(int row, int col, T val);

  /**
   * Determine the number of rows in the matrix.
   *
   * @return the number of rows.
   */
  public int height();

  /**
   * Determine the number of columns in the matrix.
   *
   * @return the number of columns.
   */
  public int width();

  /**
   * Insert a row filled with the default value.
   *
   * @param row
   *   The number of the row to insert.
   *
   * @throws IndexOutOfBoundsException
   *   If the row is negative or greater than the height.
   */
  public void insertRow(int row);

  /**
   * Insert a row filled with the specified values.
   *
   * @param row
   *   The number of the row to insert.
   * @param vals
   *   The values to insert.
   *
   * @throws IndexOutOfBoundsException
   *   If the row is negative or greater than the height.
   * @throws ArraySizeException
   *   If the size of vals is not the same as the width of the matrix.
   */
  public void insertRow(int row, T[] vals) throws ArraySizeException;

  /**
   * Insert a column filled with the default value.
   *
   * @param col
   *   The number of the column to insert.
   *
   * @throws IndexOutOfBoundsException
   *   If the column is negative or greater than the width.
   */
  public void insertCol(int col);

  /**
   * Insert a column filled with the specified values.
   *
   * @param col
   *   The number of the column to insert.
   * @param vals
   *   The values to insert.
   *
   * @throws IndexOutOfBoundsException
   *   If the column is negative or greater than the width.
   * @throws ArraySizeException
   *   If the size of vals is not the same as the height of the matrix.
   */
  public void insertCol(int col, T[] vals) throws ArraySizeException;

  /**
   * Delete a row.
   *
   * @param row
   *   The number of the row to delete.
   *
   * @throws IndexOutOfBoundsException
   *   If the row is negative or greater than or equal to the height.
   */
  public void deleteRow(int row);

  /**
   * Delete a column.
   *
   * @param col
   *   The number of the column to delete.
   *
   * @throws IndexOutOfBoundsException
   *   If the column is negative or greater than or equal to the width.
   */
  public void deleteCol(int col);

  /**
   * Fill a rectangular region of the matrix.
   *
   * @param startRow
   *   The top edge / row to start with (inclusive).
   * @param startCol
   *   The left edge / column to start with (inclusive).
   * @param endRow
   *   The bottom edge / row to stop with (exclusive).
   * @param endCol
   *   The right edge / column to stop with (exclusive).
   * @param val
   *   The value to store.
   *
   * @throw IndexOutOfBoundsException
   *   If the rows or columns are inappropriate.
   */
  public void fillRegion(int startRow, int startCol, int endRow, int endCol,
      T val);

  /**
   * Fill a line (horizontal, vertical, diagonal).
   *
   * @param startRow
   *   The row to start with (inclusive).
   * @param startCol
   *   The column to start with (inclusive).
   * @param deltaRow
   *   How much to change the row in each step.
   * @param deltaCol
   *   How much to change the column in each step.
   * @param endRow
   *   The row to stop with (exclusive).
   * @param endCol
   *   The column to stop with (exclusive).
   * @param val
   *   The value to store.
   *
   * @throw IndexOutOfBoundsException
   *   If the rows or columns are inappropriate.
   */
  public void fillLine(int startRow, int startCol, int deltaRow, int deltaCol,
      int endRow, int endCol, T val);

  /**
   * A make a copy of the matrix. May share references (e.g., if individual
   * elements are mutable, mutating them in one matrix may affect the other
   * matrix) or may not.
   *
   * @return a copy of the matrix.
   */
  @SuppressWarnings({"rawtypes"})
  public Matrix clone();

  /**
   * Determine if this object is equal to another object.
   *
   * @param other
   *   The object to which we compare ourselves.
   *
   * @return true if the other object is a matrix with the same width,
   * height, and equal elements; false otherwise.
   */
  public boolean equals(Object other);
} // interface Matrix<T>
