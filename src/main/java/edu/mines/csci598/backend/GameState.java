package edu.mines.csci598.backend;

import java.awt.*;

/**
 * Encapsulates a "state" of the game.
 *
 * A state defines how status is updated, how graphics are drawn, and how input
 * is handled. A state may have substates, which are (by default) run instead
 * of the upper state when present; these subStates must use the same view
 * matrices as the upper ones.
 *
 * Subclasses do not need to be thread-safe; the game manager will ensure that
 * no more than one thread operates upon the root state at any given time. Note
 * that there is NO thread affinity --- there is no guarantee that any
 * particular set of invocations will occur on any particular threads.
 */
public abstract class GameState implements InputReceiver {
  /**
   * The current subordinate state, or null if none is present.
   */
  protected GameState subState = null;

  /**
   * Updates this state or its subordinate, given the time elapsed, in seconds.
   *
   * Subclasses will generally want to override updateThis() instead.
   *
   * @param elapsedTime The time that has passed (or that is to be assumed to
   * have passed) since the last call to update(), in seconds
   * @return The state to continue running, or null to terminate (ie, this to
   * keep running, another state to switch, null to terminate)
   */
  public GameState update(float elapsedTime) {
    if (subState != null) {
      subState = subState.update(elapsedTime);
      return this;
    } else {
      return this.updateThis(elapsedTime);
    }
  }

  /**
   * Performs updates specific to this state, when it has not subordinate.
   *
   * @param elapsedTime The time elapsed since the last call to update(), in
   * seconds (NOT since the last call to updateThis()!)
   * @return The state to continue running, or null to terminate.
   */
  protected abstract GameState updateThis(float elapsedTime);

  /**
   * Draws this state or its subordinate using the given graphics object.
   *
   * Most subclasses will want to implement drawThis() instead.
   *
   * The method MUST keep matrix pushes/pops balanced; the model matrix can be
   * assumed to be identity; the method MUST NOT leave the view matrix in a
   * modified state upon returning.
   */
  public void draw(Graphics2D g) {
    if (subState != null)
      subState.draw(g);
    else
      this.drawThis(g);
  }

  /**
   * Performs drawing for this particular state.
   */
  protected abstract void drawThis(Graphics2D g);

  @Override
  public void receiveInput(InputEvent e) {
    if (subState != null)
      subState.receiveInput(e);
    else
      this.receiveInputThis(e);
  }

  /**
   * Processes an input event which occurred while this state had no
   * subordinate.
   *
   * This is generally preferable to overriding receiveInput() since it does
   * not need to worry about the hierarchy.
   *
   * Default does nothing.
   */
  protected void receiveInputThis(InputEvent e) {}
}
