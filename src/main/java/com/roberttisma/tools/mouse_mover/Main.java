package com.roberttisma.tools.mouse_mover;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.floorMod;
import static java.lang.Math.min;
import static java.lang.Math.sin;

public class Main {

  public static void main(String[] args) throws Throwable{
    Robot robot = new Robot();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Point centerPoint = new Point((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);
    int radius = (int)min(screenSize.getHeight(), screenSize.getWidth())/4;
    ArrayList<Point> points = generateCirclePoints3(centerPoint, radius, 0.03);

    Point firstPoint = points.get(0);
    robot.mouseMove((int)firstPoint.getX(), (int)firstPoint.getY());

    long delay = 5;
    Point currPoint = MouseInfo.getPointerInfo().getLocation();
    Point actualPoint = currPoint;
    int currIdx = 0;
    while(true){
      if (!actualPoint.equals(currPoint)){
        break;
      }
      currIdx= floorMod(currIdx+1, points.size());
      currPoint = points.get(currIdx);

      robot.mouseMove((int)currPoint.getX(), (int)currPoint.getY());
      Thread.sleep(delay);
      actualPoint = MouseInfo.getPointerInfo().getLocation();
    }
  }

  private static ArrayList<Point> generateCirclePoints3(Point center, int radius, double tx){
    ArrayList<Point> points = new ArrayList<Point>();
    double xCenter = center.getX();
    double yCenter = center.getY();
    double y;
    double x;
    for (double theta=0; theta<2*Math.PI; theta += tx){
      x = radius* sin(theta);
      y = radius* cos(theta);
      Point p = new Point();
      p.setLocation(x+xCenter, y+yCenter);
      points.add(p);
    }
    return points;
  }

}
