package edu.mines.csci598.backend;

/**
 * Describes the state of the abstracted input system.
 *
 * The input system tracks up to four "pointers" and two "bodies".
 */
public final class InputStatus {
  /**
   * The X positions of each body; 0 is the far left, 1 is the far right. A
   * value of -1 indicates not present.
   */
  public final float bodies[] = new float[] { -1, -1 };

  /**
   * The position of each pointer, as X-Y pairs. For X, 0 is the far left of
   * the screen, and 1 is the far right. For Y, 0 is the bottom, and some value
   * determined by GameManager.VHEIGHT is the top.
   *
   * An X coordinate of -1 indicates that that pointer is not currently
   * meaningful.
   */
  public final float pointers[][] = new float[][] {{-1,-1}, {-1,-1},
                                                   {-1,-1}, {-1,-1}};
}
