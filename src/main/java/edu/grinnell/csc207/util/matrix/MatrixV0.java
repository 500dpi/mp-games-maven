package edu.grinnell.csc207.util.matrix;

/**
 * An implementation of two-dimensional matrices.
 *
 * @author Sara Jaljaa
 * @author Samuel A. Rebelsky
 *
 * @param <T> The type of values stored in the matrix.
 */
public class MatrixV0<T> implements Matrix<T> {

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The height of the matrix.
   */
  int height;

  /**
   * The width of the matrix.
   */
  int width;

  /**
   * The default value the matrix is set to.
   */
  T def;

  /**
   * The matrix array.
   */
  T[][] matrix;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new matrix of the specified width and height with the given
   * value as the default.
   *
   * @param width
   *    The width of the matrix.
   * @param height
   *    The height of the matrix.
   * @param def
   *    The default value, used to fill all the cells.
   *
   * @throws NegativeArraySizeException
   *    If either the width or height are negative.
   */
  @SuppressWarnings({"unchecked"})
  public MatrixV0(int width, int height, T def) {
    // Check if the height and width are valid (non-negative)
    if (height < 0 || width < 0) {
      throw new NegativeArraySizeException();
    } // if

    // Assign fields
    this.width = width;
    this.height = height;
    this.matrix = (T[][]) new Object[height][width];
    this.def = def;

    // Assign each (row, col) to the default value
    for (int rows = 0; rows < height; rows++) {
      for (int cols = 0; cols < width; cols++) {
        this.matrix[rows][cols] = def;
      } // for (cols)
    } // for (rows)
  } // MatrixV0(int, int, T)

  /**
   * Create a new matrix of the specified width and height with null as
   * the default value.
   *
   * @param width
   *    The width of the matrix.
   * @param height
   *    The height of the matrix.
   *
   * @throws NegativeArraySizeException
   *    If either the width or height are negative.
   */
  public MatrixV0(int width, int height) {
    this(width, height, null);
  } // MatrixV0(int, int)

  // +----------------+----------------------------------------------
  // | Static Methods |
  // +----------------+

  /**
   * Check if the row or column is not within the desired exclusive bounds
   * of 0 < x < limit.
   *
   * @param val
   *    The value to check.
   * @param limit
   *    The upper bound.
   * @return
   *    True or false if the value is within range.
   */
  public static boolean notExclusive(int val, int limit) {
    return (val < 0 || val > limit);
  } // notExclusive(int, int)

  /**
   * Check if the row or column is not within the desired inclusive bounds
   * of 0 < x <= limit.
   *
   * @param val
   *    The value to check.
   * @param limit
   *    The upper bound.
   * @return
   *    True or false if the value is within range.
   */
  public static boolean notInclusive(int val, int limit) {
    return (val < 0 || val >= limit);
  } // notInclusive(int, int)

  // +------------------+--------------------------------------------
  // | Instance Methods |
  // +------------------+

  /**
   * Determine the number of rows in the matrix.
   *
   * @return The number of rows.
   */
  public int height() {
    return this.height;
  } // height()

  /**
   * Determine the number of columns in the matrix.
   *
   * @return The number of columns.
   */
  public int width() {
    return this.width;
  } // width()

  /**
   * Get the element at the given row and column.
   *
   * @param row
   *    The row of the element.
   * @param col
   *    The column of the element.
   * @return
   *    The value at the specified location.
   *
   * @throws IndexOutOfBoundsException
   *    If either the row or column is out of reasonable bounds.
   */
  public T get(int row, int col) {
    // Check if the row and colums are within bounds, then retrieve value
    if (notInclusive(row, this.height) || notInclusive(col, this.width)) {
      throw new IndexOutOfBoundsException();
    } else {
      return this.matrix[row][col];
    } // elif
  } // get(int, int)

  /**
   * Set the element at the given row and column.
   *
   * @param row
   *    The row of the element.
   * @param col
   *    The column of the element.
   * @param val
   *    The value to set.
   *
   * @throws IndexOutOfBoundsException
   *    If either the row or column is out of reasonable bounds.
   */
  public void set(int row, int col, T val) {
    // Check if the row and column are within bounds, then set value to val
    if (notInclusive(row, this.height) || notInclusive(col, this.width)) {
      throw new IndexOutOfBoundsException();
    } else {
      this.matrix[row][col] = val;
    } // elif
  } // set(int, int, T)

  /**
   * Insert a row filled with the default value.
   *
   * @param row
   *    The number of the row to insert.
   *
   * @throws IndexOutOfBoundsException
   *    If the row is negative or greater than the height.
   */
  @SuppressWarnings({"unchecked"})
  public void insertRow(int row) throws IndexOutOfBoundsException {
    // Check if the row is within bounds
    if (notExclusive(row, this.height())) {
      throw new IndexOutOfBoundsException();
    } // if

    // Increase the row size
    this.height++;

    // A new 2-D array to store the modified matrix
    T[][] copy = (T[][]) new Object[this.height][this.width];

    // When at the index to add a row, assign that row to the default
    // value and move all the remaining rows up one value
    for (int rows = 0; rows < this.height; rows++) {
      if (rows < row) {
        copy[rows] = this.matrix[rows];
      } else if (rows == row) {
        for (int cols = 0; cols < this.width; cols++) {
          copy[rows][cols] = this.def;
        } // for (cols)
      } else {
        copy[rows] = this.matrix[rows - 1];
      } // elif
    } // for (rows)
    this.matrix = copy;
  } // insertRow(int)

  /**
   * Insert a row filled with the specified values.
   *
   * @param row
   *    The number of the row to insert.
   * @param vals
   *    The values to insert.
   *
   * @throws IndexOutOfBoundsException
   *    If the row is negative or greater than the height.
   * @throws ArraySizeException
   *    If the size of vals is not the same as the width of the matrix.
   */
  public void insertRow(int row, T[] vals) throws ArraySizeException {
    // Check if the row is within bounds
    if (notExclusive(row, this.height())) {
      throw new IndexOutOfBoundsException();
    } // if

    // Check if the values to insert are the appropriate length (= width)
    if (vals.length != this.width) {
      throw new ArraySizeException();
    } // if

    // Create new row with default values
    this.insertRow(row);

    // Set new default row to vals
    for (int cols = 0; cols < this.width; cols++) {
      this.matrix[row][cols] = vals[cols];
    } // for (cols)
  } // insertRow(int, T[])

  /**
   * Insert a column filled with the default value.
   *
   * @param col
   *    The number of the column to insert.
   *
   * @throws IndexOutOfBoundsException
   *    If the column is negative or greater than the width.
   */
  @SuppressWarnings({"unchecked"})
  public void insertCol(int col) {
    // Check if the column is within bounds
    if (notExclusive(col, this.width())) {
      throw new IndexOutOfBoundsException();
    } // if

    // Increase the column size
    this.width++;

    // New 2-D array to store the modified array
    T[][] copy = (T[][]) new Object[this.height][this.width];

    // When at the index to add a column, assign that column to the
    // default value and move all the remaining columns over by one
    for (int rows = 0; rows < this.height; rows++) {
      for (int cols = 0; cols < this.width; cols++) {
        if (cols < col) {
          copy[rows][cols] = this.matrix[rows][cols];
        } else if (cols == col) {
          copy[rows][col] = this.def;
        } else if (cols > col) {
          copy[rows][cols] = this.matrix[rows][cols - 1];
        } // elif
      } // for (cols)
    } // for (rows)
    this.matrix = copy;
  } // insertCol(int)

  /**
   * Insert a column filled with the specified values.
   *
   * @param col
   *    The number of the column to insert.
   * @param vals
   *    The values to insert.
   *
   * @throws IndexOutOfBoundsException
   *    If the column is negative or greater than the width.
   * @throws ArraySizeException
   *    If the size of vals is not the same as the height of the matrix.
   */
  public void insertCol(int col, T[] vals) throws ArraySizeException {
    // Check if the column is within bounds
    if (notExclusive(col, this.width)) {
      throw new IndexOutOfBoundsException();
    } // if

    // Check if the values to insert are the appropriate length (= height)
    if (vals.length != this.height) {
      throw new ArraySizeException();
    } // if

    // Create new column with default values
    this.insertCol(col);

    // Set newly added default column to vals
    for (int rows = 0; rows < this.height; rows++) {
      this.matrix[rows][col] = vals[rows];
    } // for (rows)
  } // insertCol(int, T[])

  /**
   * Delete a row.
   *
   * @param row
   *    The number of the row to delete.
   *
   * @throws IndexOutOfBoundsException
   *    If the row is negative or greater than or equal to the height.
   */
  @SuppressWarnings({"unchecked"})
  public void deleteRow(int row) {
    // Check if row is within bounds
    if (notInclusive(row, this.height)) {
      throw new IndexOutOfBoundsException();
    } // if

    // Decrease the row size
    this.height--;

    // A new 2-D array to store the modified matrix
    T[][] copy = (T[][]) new Object[this.height][this.width];

    // Copy all the values in each row; when the row value is >= the
    // row to be deleted, all rows will be shifted up one
    for (int rows = 0; rows < this.height; rows++) {
      if (rows < row) {
        copy[rows] = this.matrix[rows];
      } else if (rows >= row) {
        copy[rows] = this.matrix[rows + 1];
      } // elif
    } // for (rows)
    this.matrix = copy;
  } // deleteRow(int)

  /**
   * Delete a column.
   *
   * @param col
   *    The number of the column to delete.
   *
   * @throws IndexOutOfBoundsException
   *    If the column is negative or greater than or equal to the width.
   */
  @SuppressWarnings({"unchecked"})
  public void deleteCol(int col) {
    // Check if column is within bounds
    if (notInclusive(col, this.width)) {
      throw new IndexOutOfBoundsException();
    } // if

    // Decrease the column size
    this.width--;

    // A new 2-D array to store the modified matrix
    T[][] copy = (T[][]) new Object[this.height][this.width];

    // Copy all the values in each column, overwriting the value of the
    // deleted column by shifting every column up by one at that index
    for (int rows = 0; rows < this.height; rows++) {
      for (int cols = 0; cols < this.width + 1; cols++) {
        if (cols < col) {
          copy[rows][cols] = this.matrix[rows][cols];
        } else if (cols > col) {
          copy[rows][cols - 1] = this.matrix[rows][cols];
        } // elif
      } // for (cols)
    } // for (rows)
    this.matrix = copy;
  } // deleteCol(int)

  /**
   * Fill a rectangular region of the matrix.
   *
   * @param startRow
   *    The top edge/row to start with (inclusive).
   * @param startCol
   *    The left edge/column to start with (inclusive).
   * @param endRow
   *    The bottom edge/row to stop with (exclusive).
   * @param endCol
   *    The right edge/column to stop with (exclusive).
   * @param val
   *    The value to store.
   *
   * @throws IndexOutOfBoundsException
   *    If the rows or columns are inappropriate.
   */
  public void fillRegion(int startRow, int startCol, int endRow, int endCol, T val) {
    // Check that the start & end values for row & column are within bounds
    if (!((startRow >= 0 && endRow <= this.height)
        && (startCol >= 0 && startCol <= this.width))) {
      throw new IndexOutOfBoundsException();
    } // if

    // Loop through and set index to val for the bounds specified
    for (int rows = startRow; rows < endRow; rows++) {
      for (int cols = startCol; cols < endCol; cols++) {
        this.matrix[rows][cols] = val;
      } // for (cols)
    } // for (rows)
  } // fillRegion(int, int, int, int, T)

  /**
   * Fill a line (horizontal, vertical, diagonal).
   *
   * @param startRow
   *    The row to start with (inclusive).
   * @param startCol
   *    The column to start with (inclusive).
   * @param deltaRow
   *    How much to change the row in each step.
   * @param deltaCol
   *    How much to change the column in each step.
   * @param endRow
   *    The row to stop with (exclusive).
   * @param endCol
   *    The column to stop with (exclusive).
   * @param val
   *    The value to store.
   *
   * @throws IndexOutOfBoundsException
   *    If the rows or columns are inappropriate.
   */
  public void fillLine(int startRow, int startCol, int deltaRow, int deltaCol, int endRow,
      int endCol, T val) {
    // Check that the start & end values for row & column are within bounds
    if (!((startRow >= 0 && endRow <= this.height)
        && (startCol >= 0 && startCol <= this.width))) {
      throw new IndexOutOfBoundsException();
    } // if

    // Starting coordinates (row, col)
    int rows = startRow;
    int cols = startCol;

    // Find the coordinate (row, col) from Δcol & Δrow (slope), then
    // set the value at that point until col >= endCol or row >= endRow
    do {
      this.set(rows, cols, val);
      rows += deltaRow;
      cols += deltaCol;
    } // do/while
    while (cols != endCol && rows != endRow);
  } // fillLine(int, int, int, int, int, int, T)

  /**
   * A make a copy of the matrix. May share references (e.g., if individual
   * elements are mutable, mutating them in one matrix may affect the other
   * matrix) or may not.
   *
   * @return A copy of the matrix.
   */
  @SuppressWarnings({"rawtypes"})
  public Matrix clone() {
    // To store the cloned matrix
    MatrixV0<T> cloned = new MatrixV0<>(this.width, this.height, this.def);

    // Assign all the values of the original to the cloned matrix
    for (int rows = 0; rows < this.height; rows++) {
      for (int cols = 0; cols < this.width; cols++) {
        cloned.set(rows, cols, this.get(rows, cols));
      } // for (cols)
    } // for (rows)
    return cloned;
  } // clone()

  /**
   * Determine if this object is equal to another object.
   *
   * @param other
   *   The object to compare.
   *
   * @return
   *    True if the other object is a matrix with the same width,
   *    height, and equal elements; false otherwise.
   */
  @SuppressWarnings({"rawtypes"})
  public boolean equals(Object other) {
    // Check if other is a Matrix object; if not, cast it as one
    if (!(other instanceof Matrix)) {
      return false;
    } else {
      Matrix compare = (Matrix) other;

      // Check that both matrices have the same field values
      if (!((this.height() == compare.height())
          && (this.width() == compare.width()))) {
        return false;
      } // if

      // Check if all the values stored are the same
      for (int rows = 0; rows < this.height(); rows++) {
        for (int cols = 0; cols < this.width(); cols++) {
          if (!this.get(rows, cols).equals(compare.get(rows, cols))) {
            return false;
          } // if
        } // for (cols)
      } // for (rows)
      return true;
    } // elif
  } // equals(Object)

  /**
   * Compute a hash code for this matrix. Included because any object
   * that implements `equals` is expected to implement `hashCode` and
   * ensure that the hash codes for two equal objects are the same.
   *
   * @return The hash code.
   */
  public int hashCode() {
    int multiplier = 7;
    int code = this.width() + multiplier * this.height();
    for (int row = 0; row < this.height(); row++) {
      for (int col = 0; col < this.width(); col++) {
        T val = this.get(row, col);
        if (val != null) {
          // It's okay if the following computation overflows, since
          // it will overflow uniformly.
          code = code * multiplier + val.hashCode();
        } // if
      } // for col
    } // for row
    return code;
  } // hashCode()
} // class MatrixV0
