package com.roberttisma.tools.mouse_mover.utils;

import java.io.UnsupportedEncodingException;

public class MathCalc {
  public static final String INFINITY_SYMBOL = getInfinityString();

  public static int floorMod(int x, int y) {
    return x - floorDiv(x, y) * y;
  }

  public static int floorDiv(int x, int y) {
    int r = x / y;
    if ((x ^ y) < 0 && r * y != x) {
      --r;
    }
    return r;
  }

  public static String getInfinityString(){
    try {
      return new String(String.valueOf(Character.toString('\u221E')).getBytes("UTF-8"), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      throw new IllegalStateException("Could not process infinity symbol", e);
    }
  }

}
