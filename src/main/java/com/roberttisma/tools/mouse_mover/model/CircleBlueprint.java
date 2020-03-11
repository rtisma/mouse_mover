package com.roberttisma.tools.mouse_mover.model;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.sin;

public class CircleBlueprint implements Blueprint {

  /**
   * AppConfig
   */
  private final Dimension screenSize;
  private final double resolution;

  public CircleBlueprint(Dimension screenSize, double resolution) {
    this.screenSize = screenSize;
    this.resolution = resolution;
  }

  private Point getCenterPoint(){
    return new Point((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2);
  }

  private int getRadius(){
    return (int)min(screenSize.getHeight(), screenSize.getWidth())/4;
  }

  @Override
  public ArrayList<Point> getBlueprint() {
    return generatePoints(resolution);
  }

  public ArrayList<Point> generatePoints(final double tx){
    ArrayList<Point> points = new ArrayList<Point>();
    Point center = getCenterPoint();
    int radius = getRadius();
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
