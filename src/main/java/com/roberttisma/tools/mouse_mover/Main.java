package com.roberttisma.tools.mouse_mover;

import com.roberttisma.tools.mouse_mover.config.AppConfig;
import com.roberttisma.tools.mouse_mover.model.Blueprint;
import com.roberttisma.tools.mouse_mover.model.CircleBlueprint;

import java.awt.*;
import java.util.ArrayList;

import static java.awt.Toolkit.getDefaultToolkit;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

public class Main {

  public static void main(String[] args) throws Throwable{
    AppConfig c = parseConfig(args);
    System.out.println(c);
    Robot robot = new Robot();
    Dimension screenSize = getDefaultToolkit().getScreenSize();
    Blueprint blueprint = new CircleBlueprint(screenSize, c.getResolution());
    ArrayList<Point> points = blueprint.getBlueprint();
    Mover mover = new Mover(robot, points, c.getDelayMs(), c.getDurationMs());
    mover.run();
    hang(!c.isExitOnCompletion());
  }

  private static void hang(boolean enable) {
    if (enable){
      Object kill = new Object();
      try {
        synchronized (kill) {
          System.out.println("Program successfully done. Please kill this process manually...");
          while(true){
            kill.wait();
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
        throw new IllegalStateException(e);
      }
    }
  }

  private static AppConfig parseConfig(String[] args){
    int i=0;
    long durationSeconds = parseLong(args[i++]);
    long delayMs = parseLong(args[i++]);
    double resolution = parseDouble(args[i++]);
    boolean exitOnCompletion = parseBoolean(args[i++]);
    return new AppConfig(durationSeconds, delayMs, resolution, exitOnCompletion);
  }

}
