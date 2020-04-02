package com.roberttisma.tools.mouse_mover.gui;

import com.roberttisma.tools.mouse_mover.Processor;
import com.roberttisma.tools.mouse_mover.entity.AppConfigEntity;
import com.roberttisma.tools.mouse_mover.model.AppConfig;
import com.roberttisma.tools.mouse_mover.repository.AppConfigEntityRepository;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static com.roberttisma.tools.mouse_mover.entity.AppConfigEntity.createAppConfigEntity;
import static com.roberttisma.tools.mouse_mover.utils.Files.provisionFile;

public class GUI {

  private static final AppConfig DEFAULT_APP_CONFIG = new AppConfig(-1, 1000, 0.3, false);
  private static AppConfigEntityRepository APP_CONFIG_ENTITY_REPOSITORY = new AppConfigEntityRepository(getStorageFile());

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
  private JPanel executePanel;
  private JButton startButton;
  private JButton resetButton;
  private JTextPane console;
  private JProgressBar progressBar1;
  private JTextField countdownTF;
  private JLabel countdownInSecondsLabel;
  private JPanel controlPanel;
  private JPanel outputPanel;
  private JPanel rightPanel;
  private JPanel sessionPanel;
  private JList list1;
  private JButton saveButton;
  private JButton loadButton;
  private JButton deleteButton;
  private JPanel savePanel;
  private JTextField saveTF;

  private static File getStorageFile() {
    File storageFile = new File(System.getProperty("user.home")+File.separator+"mouse_mover_config.csv");
    provisionFile(storageFile);
    return storageFile;
  }


  private AppConfig getConfig(){
    long durationSeconds = Long.parseLong(durationTF.getText());
    long delayMs = Long.parseLong(delayTF.getText());
    double resolution = Double.parseDouble(resolutionTF.getText());
    return new AppConfig(durationSeconds, delayMs, resolution, false);
  }

  private static void sleep(long ms){
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void progressBarLoad(){
    int seconds = Integer.parseInt(countdownTF.getText());
    long res = 500;
    long slices = seconds*(1000/res);
    for (int s=1; s<=slices; s++){
      int v = (int)((s*100)/slices);
      sleep(res);
      progressBar1.setValue(v);
    }
    progressBar1.setValue(0);
  }

  public GUI() {
    updateList();
    startButton.addActionListener(new ActionListener() {

      @Override public void actionPerformed(ActionEvent actionEvent) {
        new Thread(new Runnable() {

          @Override public void run() {

            console.setText("");
            progressBarLoad();

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
        console.setText("");
      }
    });
    saveButton.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent actionEvent) {
        APP_CONFIG_ENTITY_REPOSITORY.save(createAppConfigEntity(saveTF.getText(), getConfig()));
        saveTF.setText("");
        updateList();
      }
    });
    deleteButton.addActionListener(new ActionListener() {

      @Override public void actionPerformed(ActionEvent actionEvent) {
        APP_CONFIG_ENTITY_REPOSITORY.delete(list1.getSelectedValue().toString());
        updateList();
      }
    });
    loadButton.addActionListener(new ActionListener() {

      @Override public void actionPerformed(ActionEvent actionEvent) {
        AppConfigEntity c = APP_CONFIG_ENTITY_REPOSITORY.findOne(list1.getSelectedValue().toString());
        loadConfig(c);
      }

    });
  }

  private void updateList(){
    list1.setListData(APP_CONFIG_ENTITY_REPOSITORY.listIds().toArray());
  }

  private void loadConfig(AppConfigEntity c){
    durationTF.setText(Long.toString(c.getDurationMs()/1000));
    delayTF.setText(Long.toString(c.getDelayMs()));
    resolutionTF.setText(Double.toString(c.getResolution()));
    console.setText("");
  }

  public static void main(String[] args){
    JFrame jFrame = new JFrame("BMK Randy");
    jFrame.setContentPane(new GUI().panel1);
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    Dimension screenSize = getDefaultToolkit().getScreenSize();
////    jFrame.setMinimumSize(new Dimension(600,600));
////    jFrame.setPreferredSize(new Dimension((int)(screenSize.getWidth()/4), (int)(screenSize.getHeight()/4)));
    jFrame.pack();;
    jFrame.setVisible(true);

  }
}
