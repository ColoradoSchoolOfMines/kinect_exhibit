package edu.mines.csci598.recycler.splashscreen.playerdetector;

import edu.mines.csci598.recycler.backend.GameManager;
import edu.mines.csci598.recycler.backend.OpenNIHandTrackerInputDriver;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TestPlayerDetector {
    public static void main(String[] args) {
        GameManager man = new GameManager( "TestPlayerDetector" );
        OpenNIHandTrackerInputDriver driver = new OpenNIHandTrackerInputDriver();
        driver.installInto( man );

        DetectLocation dl = new DetectLocation( 1000, man);

        BufferedImage bimg = man.getImage();

        ImageIcon icon = new ImageIcon();
        icon.setImage(bimg);
        JOptionPane.showMessageDialog(null, icon);

        while(true ){
            System.out.println( dl.inWatchedArea() );
            dl.updateAreaLocation();

            Graphics g = man.getImage().createGraphics();

            g.setColor(Color.RED);
            for( int i = 0; i<4;i++){

                g.fillOval(dl.getWatchedArea()[i][0], dl.getWatchedArea()[i][1], 10, 10);

            }

            icon.setImage(man.getImage());
            JOptionPane.showMessageDialog(null, icon);
            driver.pumpImage();
        }

//        int i = 0;
//        while( !dl.startGame() ){
//            System.out.println( dl.inWatchedArea() );
//            driver.pumpImage();
//            driver.pumpDepth();
//            i++;
//            System.out.println( "NTESUOEUNSTHOES" );
//            //dl.interrupt();
//            //dl.stopWatchingArea();
//        }
    }

}
