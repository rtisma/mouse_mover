package com.roberttisma.tools.mouse_mover;

import com.roberttisma.tools.mouse_mover.utils.MathCalc;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.roberttisma.tools.mouse_mover.model.Duration.createDuration;
import static java.lang.String.format;

public class Mover implements Runnable{

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");

  /**
   * Params
   */
  private final Robot robot;
  private final ArrayList<Point> points;
  private final long delayMs;
  private final long durationMs;

  /**
   * State
   */
  private int currIdx = 0;

  public Mover(Robot robot, ArrayList<Point> points, long delayMs, long durationMs) {
    this.robot = robot;
    this.points = points;
    this.delayMs = delayMs;
    this.durationMs = durationMs;
  }


  @Override
  public void run()  {
    initPosition();
    Point currPoint = sampleActualPoint();
    Point actualPoint = currPoint;
    try{
      Date startDate = new Date();
      long startMs = startDate.getTime();
      Date endDate = new Date(startMs+durationMs);
      System.out.println(format("Start Date:        %s",  DATE_FORMAT.format(startDate)));
      System.out.println(format("End Date:          %s",  durationMs < 0 ? "NEVER!" : DATE_FORMAT.format(endDate)));
      System.out.println(format("Expected Duration: %s", createDuration(durationMs)));

      long currentDurationMs = 0;
      while(actualPoint.equals(currPoint) && (durationMs < 0 || currentDurationMs <= durationMs)){
        currPoint = getNextPlannedPoint();
        moveMouse(currPoint);
        Thread.sleep(delayMs);
        actualPoint = sampleActualPoint();
        currentDurationMs = System.currentTimeMillis() - startMs;
      }

      if (actualPoint.equals(currPoint)){
        System.out.println("Completed successfully, stopping mouse movement");
      } else {
        System.out.println("User moved mouse, stopping mouse movement.\n");
        System.out.println(format("Actual Duration:   %s\n", createDuration(currentDurationMs)));
      }
    }catch (Throwable t){
      t.printStackTrace();
      throw new IllegalStateException(t.getMessage(), t);
    }
  }

  private Point getNextPlannedPoint(){
    currIdx = MathCalc.floorMod(currIdx+1, points.size());
    return points.get(currIdx);
  }

  private void moveMouse(Point p){
    robot.mouseMove((int)p.getX(), (int)p.getY());
  }

  private void initPosition(){
    Point firstPoint = points.get(0);
    robot.mouseMove((int)firstPoint.getX(), (int)firstPoint.getY());
  }

  private Point sampleActualPoint(){
    return MouseInfo.getPointerInfo().getLocation();
  }

}
