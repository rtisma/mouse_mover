package com.roberttisma.tools.mouse_mover.config;

import com.roberttisma.tools.mouse_mover.model.Duration;

import java.util.Date;

import static com.roberttisma.tools.mouse_mover.model.Duration.createDuration;

public class AppConfig {
  private final long durationMs;
  private final long delayMs;
  private final double resolution;
  private final boolean exitOnCompletion;

  public AppConfig(long durationSeconds, long delayMs, double resolution, boolean exitOnCompletion) {
    this.durationMs = durationSeconds*1000;
    this.delayMs = delayMs;
    this.resolution = resolution;
    this.exitOnCompletion = exitOnCompletion;
  }

  public long getDurationMs() {
    return durationMs;
  }

  public long getDelayMs() {
    return delayMs;
  }

  public double getResolution() {
    return resolution;
  }

  public boolean isExitOnCompletion() {
    return exitOnCompletion;
  }

  @Override public String toString() {
    return "" +
        "DurationSeconds:   " + durationMs/1000 +
        "\nDelayMs:           " + delayMs +
        "\nResolution:        " + resolution +
        "\nExitOnCompletion:  " + exitOnCompletion;
  }
}
