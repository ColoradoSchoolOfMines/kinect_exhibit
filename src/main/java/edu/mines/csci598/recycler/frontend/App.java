package edu.mines.csci598.recycler.frontend;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The main driver application for the input prototype.
 */
public class App {
   public static void main(String[] args) {

      JFrame frame = new JFrame();
      frame.setSize(500, 500);
      frame.setTitle("Hello, world");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JPanel panel = new JPanel();
      panel.add(new JLabel("Hello, World!"));

      frame.add(panel);
      frame.setVisible(true);

      System.out.println("Hello, World!");
   }
}
