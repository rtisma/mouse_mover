package com.roberttisma.tools.mouse_mover.utils;

import static java.lang.String.format;

public class Checks {

  public static void checkArgument(boolean expression, String formattedString, Object ... args){
    if (!expression){
      throw new IllegalArgumentException(format(formattedString, args));
    }
  }

  public static <T> void checkNotNull(T object){
    checkArgument(object != null, "Require a non-null object");
  }


}
