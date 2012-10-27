package edu.mines.csci598.recycler.frontend.graphics;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: jrramey11
 * Date: 10/27/12
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */

public class Board extends JPanel {


    public Board() {

        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

    }

    public void paint(Graphics g) {
        super.paint(g);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
}
