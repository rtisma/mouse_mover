package com.roberttisma.tools.mouse_mover.entity;

import com.roberttisma.tools.mouse_mover.model.AppConfig;
import com.roberttisma.tools.mouse_mover.serialization.Deserializer;
import com.roberttisma.tools.mouse_mover.serialization.Serializer;

import static com.roberttisma.tools.mouse_mover.utils.Checks.checkArgument;
import static com.roberttisma.tools.mouse_mover.utils.Checks.checkNotNull;
import static com.roberttisma.tools.mouse_mover.utils.Strings.checkBlank;

public class AppConfigEntity {

  public static final Serializer<AppConfigEntity, String> SERIALIZER = new AppConfigSerializer();
  public static final Deserializer<AppConfigEntity, String> DESERIALIZER = new AppConfigDeserializer();

  private final String id;
  private final long durationMs;
  private final long delayMs;
  private final double resolution;
  private final boolean exitOnCompletion;

  public AppConfigEntity(String id, long durationMs, long delayMs, double resolution, boolean exitOnCompletion) {
    this.id = id;
    this.durationMs = durationMs;
    this.delayMs = delayMs;
    this.resolution = resolution;
    this.exitOnCompletion = exitOnCompletion;
  }

  public String getId(){
    return id;
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

  @Override
  public String toString() {
    return "" +
        "DurationSeconds:   " + durationMs/1000 +
        "\nId:                " + id +
        "\nDelayMs:           " + delayMs +
        "\nResolution:        " + resolution +
        "\nExitOnCompletion:  " + exitOnCompletion;
  }

  public static AppConfigEntity createAppConfigEntity(String id, AppConfig c){
    return new AppConfigEntity(id, c.getDurationMs(), c.getDelayMs(), c.getResolution(), c.isExitOnCompletion());
  }


  public static class AppConfigSerializer implements Serializer<AppConfigEntity, String>{

    @Override
    public String serialize(AppConfigEntity c) {
      checkNotNull(c);
      StringBuilder sb = new StringBuilder();
      sb .append(c.getId())
          .append(",")
          .append(c.getDurationMs())
          .append(",")
          .append(c.getDelayMs())
          .append(",")
          .append(c.getResolution())
          .append(",")
          .append(c.isExitOnCompletion());
      return sb.toString();
    }
  }

  public static class AppConfigDeserializer implements Deserializer<AppConfigEntity, String>{

    @Override public AppConfigEntity deserialize(String s) {
      checkBlank(s, "String to be deserialized cannot be null");
      String[] items = s.split(",");
      checkArgument(items.length == 5, "Found %s items, when there should be %s items", items.length, 5);

      String id = items[0];
      long durationMs = Long.parseLong(items[1]);
      long delayMS = Long.parseLong(items[2]);
      double resolution = Double.parseDouble(items[3]);
      boolean exitOnCompletion = Boolean.getBoolean(items[4]);
      return new AppConfigEntity(id, durationMs, delayMS, resolution, exitOnCompletion);
    }
  }
}
