package edu.mines.csci598.backend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

import java.io.Closeable;
import java.util.LinkedList;
import java.lang.ref.WeakReference;

/**
 * The GameManager manages resources and system compatibility, and orchestrates
 * interactions between different components.
 *
 * The primary coordinate system used by this class and the rest of the backend
 * are virtual coordinates, where the bottom left of the window is the origin,
 * (1.0,0) is the bottom right, and (0,vheight()) is the top left, where
 * vheight() is some float such that and nXn region of the screen is
 * square. See vcxtopx, vcytopx, pxxtovc, and pxytovc to convert between this
 * coordinate system and the raw pixels of the window (for which (0,0) is the
 * top left).
 */
public final class GameManager
implements Destroyable, Runnable {
  private final ScreenManager screen = new ScreenManager();
  private JFrame frame;
  private final LinkedList<WeakReference<Destroyable> > resources =
    new LinkedList<WeakReference<Destroyable> >();
  private GameState state;
  private final InputStatus inputStatus = new InputStatus();
  private boolean alive = true;
  private final AudioPlayer audioPlayer = new AudioPlayer(this);

  private final LinkedList<InputDriver> inputDrivers =
    new LinkedList<InputDriver>();

  /**
   * Creates a GameManager using a window with the given title
   */
  public GameManager(String title) {
    screen.installNullRepaintManager();
    screen.setFullScreen(null);
    frame = (JFrame)screen.getFullScreenWindow();
    frame.setTitle(title);

    frame.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          alive = false;
        }
      });
    frame.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            alive = false;
        }
      });

    audioPlayer.start();
  }

  @Override
  public void destroy() {
    audioPlayer.stopPlaying();
    for (WeakReference<Destroyable> wd: resources) {
      Destroyable d = wd.get();
      if (d != null)
        d.destroy();
    }

    screen.restoreScreen();
    try {
      audioPlayer.join();
    } catch (InterruptedException e) {}
  }

  /**
   * Adds the given Destroyable to the list of resources to destroy with the
   * manager.
   *
   * These items are destroyed before the frame is destroyed.
   */
  public synchronized void addResource(Destroyable d) {
    resources.add(new WeakReference<Destroyable>(d));
  }

  /**
   * Installs the given InputDriver into this GameManager.
   */
  public synchronized void installInputDriver(InputDriver driver) {
    driver.installInto(this);
    inputDrivers.add(driver);
  }

  /**
   * Changes the current state to the one given. This should only be used to
   * set an initial state; external control should otherwise be avoided.
   */
  public synchronized void setState(GameState neu) {
    state = neu;
  }

  /** Runs the game loop. */
  public void run() {
    long lastClock = System.nanoTime();
    while (alive && state != null) {
      synchronized (this) {
        //Determine elapsed time
        long clock = System.nanoTime();
        float et = (clock-lastClock) * 1.0e-9f;
        lastClock = clock;

        //Handle updating
        if (state != null) {
          state = state.update(et);
        }
      }

      if (state != null) {
        Graphics2D g = screen.getGraphics();
        state.draw(g);
        g.dispose();
      }
      screen.update();

      synchronized (this) {
        //Handle inputs
        if (state != null) {
          for (InputDriver driver: inputDrivers)
            driver.pumpInput(state);
        }
      }
    }
  }

  /**
   * Returns the "virtual height" (vheight) of the drawing area.
   *
   * vheight is the maximum Y coordinate, selected so that any NxN region (for
   * any N) will be square on the display (assuming square pixels).
   */
  public final float vheight() {
    return frame.getHeight() / (float)frame.getWidth();
  }

  /**
   * Translates from virtual coordinates to pixels on the X axis.
   */
  public int vcxtopx(float vc) {
    return (int)(vc*frame.getWidth());
  }
  /**
   * Translates from virtual coordinates to pixels on the Y axis.
   */
  public int vcytopx(float vc) {
    return (int)((1.0f - vc/vheight())*frame.getHeight());
  }

  /**
   * Translates from pixel coordinates to virtual coordinates on the X axis.
   */
  public float pxvtovc(int px) {
    return px / (float)frame.getWidth();
  }

  /**
   * Translates from pixel coordinates to virtual coordinates on the Y axis.
   */
  public float pxytovc(int px) {
    return (frame.getHeight()-1 - px)/(float)frame.getHeight() * vheight();
  }

  /**
   * Converts a dimension along the X axis in virtual coordinates to pixels.
   */
  public int vdxtopx(float vc) {
    return (int)(vc * frame.getWidth());
  }

  /**
   * Converts a dimension along the Y axis in virtual coordinates to pixels.
   *
   * Note that the sign of the result will be opposite the input, since the Y
   * axes of the two coordinate systems are reverse.
   */
  public int vdytopx(float vc) {
    return -(int)(vc/vheight() * frame.getHeight());
  }

  /**
   * Returns the Frame used by this GameManager.
   */
  public Frame getFrame() { return frame; }

  /**
   * Returns a component which represents the canvas on which drawing is
   * performed.
   */
  public Component getCanvas() { return frame; }

  /**
   * Returns the shared InputStatus object.
   */
  public InputStatus getSharedInputStatus() { return inputStatus; }

  /**
   * Returns a copy of the current InputStatus.
   */
  public InputStatus getInputStatus() {
    //This would be SO much easier in C...
    InputStatus is = new InputStatus();
    for (int i = 0; i < inputStatus.bodies.length; ++i)
      is.bodies[i] = inputStatus.bodies[i];
    for (int i = 0; i < inputStatus.pointers.length; ++i)
      for (int j = 0; j < inputStatus.pointers[i].length; ++j)
        is.pointers[i][j] = inputStatus.pointers[i][j];
    return is;
  }

  /**
   * Returns the AudioPlayer managed by this GameManager.
   */
  public AudioPlayer getAudioPlayer() {
    return audioPlayer;
  }
}
