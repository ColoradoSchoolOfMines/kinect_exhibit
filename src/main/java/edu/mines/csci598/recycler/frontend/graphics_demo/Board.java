package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JPanel;

public class Board extends JPanel {

	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
	}
	
	public void addSprite(String name, Sprite sprite) {
		sprites.put(name, sprite);
		// TODO: sort sprites based on the sprite's depth attribute
	}
	
	public Sprite getSprite(String name) {
		return sprites.get(name);	
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		
		for (Sprite sprite : sprites.values()) {
			g2d.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_LEFT) {
				getSprite("2player").setHorizontalVelocity(-1);
			}

			if (key == KeyEvent.VK_RIGHT) {
				getSprite("2player").setHorizontalVelocity(1);
			}

			if (key == KeyEvent.VK_UP) {
				getSprite("2player").setVerticalVelocity(-1);
			}

			if (key == KeyEvent.VK_DOWN) {
				getSprite("2player").setVerticalVelocity(1);
			}
		}

		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				getSprite("2player").setHorizontalVelocity(0);
			}

			if (key == KeyEvent.VK_RIGHT) {
				getSprite("2player").setHorizontalVelocity(0);
			}

			if (key == KeyEvent.VK_UP) {
				getSprite("2player").setVerticalVelocity(0);
			}

			if (key == KeyEvent.VK_DOWN) {
				getSprite("2player").setVerticalVelocity(0);
			}
		}
	}
}
