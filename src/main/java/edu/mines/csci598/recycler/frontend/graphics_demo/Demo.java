package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Demo extends JFrame implements ActionListener {

	private Timer timer;
	private Board screen = new Board();

	public Demo() {
		timer = new Timer(5, this);
		
		add(screen);
		
		Sprite background = new Sprite();
		background.setImage("background.jpg");
		screen.addSprite("1background", background);

		Sprite player = new Sprite();
		screen.addSprite("2player", player);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLocationRelativeTo(null);
		setTitle("Sprite Demo");
		setResizable(false);
		setVisible(true);
		timer.start();
	}

	public static void main(String[] args) {
		new Demo();
	}

	public void actionPerformed(ActionEvent e) {
		screen.getSprite("2player").move();
		screen.repaint();
	}
}
