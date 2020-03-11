package com.roberttisma.tools.mouse_mover.model;

import com.roberttisma.tools.mouse_mover.utils.MathCalc;

import java.util.Date;

import static com.roberttisma.tools.mouse_mover.utils.MathCalc.INFINITY_SYMBOL;
import static java.lang.String.format;

public class Duration {

  /**
   * Constants
   */
  private static final long MS_IN_SECOND = 1000;
  private static final long MS_IN_MINUTE = 60*MS_IN_SECOND;
  private static final long MS_IN_HOUR = 60*MS_IN_MINUTE;
  private static final long MS_IN_DAY = 24 * MS_IN_HOUR;

  /**
   * Data
   */
  private final long  days;
  private final long  hours;
  private final long  minutes;
  private final long  seconds;

  public Duration(long days, long hours, long minutes, long seconds) {
    this.days = days;
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
  }

  public long getDays() {
    return days;
  }

  public long getHours() {
    return hours;
  }

  public long getMinutes() {
    return minutes;
  }

  public long getSeconds() {
    return seconds;
  }

  @Override public String toString() {
    if (days <0 || hours <0 || minutes <0 || seconds < 0){
      return format("%s seconds", INFINITY_SYMBOL);
    } else {
      return format("%d days, %d hours, %d minutes and %d seconds",days,hours, minutes, seconds);
    }
  }

  public static Duration createDuration(long durationMs){
    long days = -1;
    long hours = -1;
    long minutes = -1;
    long seconds = -1;
    if (durationMs >= 0){
      days = durationMs/MS_IN_DAY;
      hours = (durationMs - days*MS_IN_DAY)/MS_IN_HOUR;
      minutes = (durationMs - days*MS_IN_DAY - hours*MS_IN_HOUR)/MS_IN_MINUTE;
      seconds = (durationMs - days*MS_IN_DAY - hours*MS_IN_HOUR - minutes*MS_IN_MINUTE)/MS_IN_SECOND;
    }
    return new Duration(days, hours, minutes, seconds);
  }




}
