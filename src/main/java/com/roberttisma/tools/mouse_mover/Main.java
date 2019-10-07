package com.roberttisma.tools.mouse_mover;

import java.awt.*;

public class Main {

  public static void main(String[] args) throws Throwable{
    Robot robot = new Robot();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int w = (int)screenSize.getWidth()/4;
    Point initial = MouseInfo.getPointerInfo().getLocation();
    int xInit = (int)initial.getX();
    int yInit = (int)initial.getY();
    int x = xInit;
    int y = yInit;
    while(true){
      if (MouseInfo.getPointerInfo().getLocation().getY() ==  initial.getY()){
        x = x > (xInit + w) ? xInit : x+1;
        robot.mouseMove(x, y);
        Thread.sleep(1);
      } else {
        break;
      }
    }
  }

}
