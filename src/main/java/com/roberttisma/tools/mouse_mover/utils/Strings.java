package com.roberttisma.tools.mouse_mover.utils;

import static com.roberttisma.tools.mouse_mover.utils.Checks.checkArgument;
import static java.lang.String.format;

public class Strings {

  public static boolean isBlank(String s){
    return s == null || s.trim().equals("");
  }

  public static void checkBlank(String s, String formattedString, Object ... args){
    checkArgument(!isBlank(s), formattedString, args);
  }

}
