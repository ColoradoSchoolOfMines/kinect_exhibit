package edu.mines.csci598.recycler.splashscreen.headers;


import javax.swing.*;
import java.awt.*;

public class InstructionHeader extends JPanel {

    private Image image;

    public InstructionHeader() {
        image = new ImageIcon("src/main/resources/SpriteImages/FinalSpriteImages/instructions_half.png").getImage();
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        add(imageLabel);
        setVisible(true);
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
