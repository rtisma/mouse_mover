package com.roberttisma.tools.mouse_mover.gui;

import com.roberttisma.tools.mouse_mover.Processor;
import com.roberttisma.tools.mouse_mover.config.AppConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;

import static java.awt.Toolkit.getDefaultToolkit;

public class GUI {

  private static final AppConfig DEFAULT_APP_CONFIG = new AppConfig(-1, 1000, 0.3, false);

  private JPanel panel1;
  private JPanel durationPanel;
  private JLabel durationInSecondsLabel;
  private JTextField durationTF;
  private JPanel delayPanel;
  private JLabel delayLabel;
  private JTextField delayTF;
  private JPanel resolutionPanel;
  private JLabel resolutionLabel;
  private JTextField resolutionTF;
  private JPanel exitOnCompletionPanel;
  private JCheckBox exitOnCompletionCheckBox;
  private JPanel executePanel;
  private JButton startButton;
  private JButton resetButton;
  private JTextPane console;



  private AppConfig getConfig(){
    long durationSeconds = Long.parseLong(durationTF.getText());
    long delayMs = Long.parseLong(delayTF.getText());
    double resolution = Double.parseDouble(resolutionTF.getText());
    boolean exitOnCompletion = exitOnCompletionCheckBox.isSelected();
    return new AppConfig(durationSeconds, delayMs, resolution, exitOnCompletion);
  }

  public GUI() {
    startButton.addActionListener(new ActionListener() {

      @Override public void actionPerformed(ActionEvent actionEvent) {
        new Thread(new Runnable() {

          @Override public void run() {
            console.setText("");
            final AppConfig appConfig = getConfig();
            try {
              Processor p = new Processor(appConfig, new Processor.StringListener() {
                @Override
                public void publishString(final String s) {
                      console.setText(console.getText()+s);
                }
              });
              p.run();
            } catch (Throwable t) {
              console.setText(appConfig.toString() + "\n\n\n" + t.getMessage());
            }
          }
        }).start();
      }
    });
    resetButton.addActionListener(new ActionListener() {

      @Override public void actionPerformed(ActionEvent actionEvent) {
        durationTF.setText(Long.toString(DEFAULT_APP_CONFIG.getDurationMs()/1000));
        delayTF.setText(Long.toString(DEFAULT_APP_CONFIG.getDelayMs()));
        resolutionTF.setText(Double.toString(DEFAULT_APP_CONFIG.getResolution()));
        exitOnCompletionCheckBox.setSelected(DEFAULT_APP_CONFIG.isExitOnCompletion());
        console.setText("");
      }
    });
  }


  public static void main(String[] args){
    JFrame jFrame = new JFrame("BMK Randy");
    jFrame.setContentPane(new GUI().panel1);
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension screenSize = getDefaultToolkit().getScreenSize();
    jFrame.setMinimumSize(new Dimension(600,600));
    jFrame.setPreferredSize(new Dimension((int)(screenSize.getWidth()/4), (int)(screenSize.getHeight()/4)));
    jFrame.pack();;
    jFrame.setVisible(true);

  }
}
