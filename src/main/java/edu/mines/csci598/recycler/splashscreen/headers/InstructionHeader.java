package edu.mines.csci598.recycler.splashscreen.headers;


import javax.swing.*;
import java.awt.*;

public class InstructionHeader extends JPanel {

    private Image image;

    public InstructionHeader() {
        image = new ImageIcon(this.getClass().getResource("instructions_half.png")).getImage();

    }

    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.drawImage(image, 0, 0, null);
    }

    public static void main(String[] arg) {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(1280, 720);

        InstructionHeader instructionHeader = new InstructionHeader();
        mainFrame.add(instructionHeader);

        mainFrame.setVisible(true);
    }
}
