//package graphics;
//
//import java.awt.Image;
//import javax.swing.ImageIcon;
//
//public class Sprite {
//
//	private float dx;
//	private float dy;
//	private float x;
//	private float y;
//	private Image image;
//
//	public Sprite() {
//		setImage("default.jpg");
//		setHorizontalVelocity(0);
//		setVerticalVelocity(0);
//		moveTo(0, 0);
//	}
//
//	/*
//	 * Sets the file path for this sprite's icon image.
//	 *
//	 * param {String} fileName
//	 * 		The name of the file to be loaded.
//	 */
//	public void setImage(String fileName) {
//		ImageIcon ii = new ImageIcon(fileName);
//		image = ii.getImage();
//	}
//
//	/*
//	 * Moves the sprite based on current velocities.
//	 *
//	 * param {float} x
//	 * 		The x position on the board.
//	 * param {float} y
//	 * 		The y position on the board.
//	 */
//	public void move() {
//		x += dx;
//		y += dy;
//	}
//
//	/*
//	 * Moves the sprite to a specific coordinate.
//	 *
//	 * param {float} x
//	 * 		The x position on the board.
//	 * param {float} y
//	 * 		The y position on the board.
//	 */
//	public void moveTo(int x, int y) {
//		this.x = x;
//		this.y = y;
//	}
//
//	/*
//	 * Sets the horizontal velocity.
//	 *
//	 * param {float} dx
//	 * 		The positive or negative horizontal velocity.
//	 */
//	public void setHorizontalVelocity(float dx) {
//		this.dx = dx;
//	}
//
//	/*
//	 * Sets the vertical velocity.
//	 *
//	 * param {float} dy
//	 * 		The positive or negative vertical velocity.
//	 */
//	public void setVerticalVelocity(float dy) {
//		this.dy = dy;
//	}
//
//	/*
//	 * Gets the x position.
//	 *
//	 * return {int}
//	 */
//	public int getX() {
//		return (int) Math.round(x);
//	}
//
//	/*
//	 * Gets the y position.
//	 *
//	 * return {int}
//	 */
//	public int getY() {
//		return (int) Math.round(y);
//	}
//
//	/*
//	 * Gets the icon image.
//	 *
//	 * return {Image}
//	 */
//	public Image getImage() {
//		return image;
//	}
//}