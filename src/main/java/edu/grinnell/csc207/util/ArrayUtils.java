package edu.grinnell.csc207.util;

import java.util.Arrays;

/**
 * A variety of utilities for working with arrays.
 *
 * @author Samuel A. Rebelsky
 */
public class ArrayUtils {
  // +------------------+--------------------------------------------
  // | Provided methods |
  // +------------------+

  /**
   * Determine if an array contains a particular value.
   *
   * @param <T>
   *   The type of values in the array.
   * @param vals
   *   The array to search.
   * @param val
   *   The value to look for.
   *
   * @return true if the array contains an equal value and false otherwise.
   */
  public static <T> boolean arrayContains(int[] vals, int val) {
    for (int tmp : vals) {
      if (tmp == val) {
        tmp = 0;
        return true;
      } // if
    } // for
    return false;
  } // arrayContains(T[], T)

  /**
   * Determine if an array of strings contains a string, treating
   * comparison as case insensitive.
   *
   * @param strings
   *   The array of strings to search.
   * @param str
   *   The string to look for.
   *
   * @return true if the element is in the array and false otherwise.
   */
  public static boolean arrayContainsCI(String[] strings, String str) {
    str = str.toLowerCase();
    for (String s : strings) {
      if (s.toLowerCase().equals(str)) {
        return true;
      } // if
    } // for
    return false;
  } // arrayContainsCI(String[], String)

  /**
   * Remove all copies of a value from an array.
   *
   * @param <T>
   *   The types of values in the array.
   * @param vals
   *   An array of values.
   * @param val
   *   The value to remove
   *
   * @return
   *   The same array, if the value does not appear.
   *   Otherwise, a new array without the value but with the
   *   remaing values in the same order.
   */
  public static <T> T[] removeAll(T[] vals, T val) {
    int offset = 0;
    T[] tmp = Arrays.copyOf(vals, vals.length);
    for (int i = 0; i < vals.length; i++) {
      if (val.equals(vals[i])) {
        ++offset;
      } else {
        tmp[i - offset] = vals[i];
      } // if/else
    } // for
    if (offset == 0) {
      return vals;
    } else {
      return Arrays.copyOf(tmp, vals.length - offset);
    } // if/else
  } // removeAll(String[], String)

} // class ArrayUtils
