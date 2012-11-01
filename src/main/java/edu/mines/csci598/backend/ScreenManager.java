package edu.mines.csci598.backend;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

/* This is an older piece of code which manages most of the headaches involved
 * in setting full-screen windows in Java and managing their graphics. With a
 * little work, it can also work with non-full-screen windows.
 *
 * Usage is something like
 *   ScreenManager screen = new ScreenManager();
 *   screen.installNullRepaintManager();
 *   screen.setFullScreen(screen.getCurrentDisplayMode());
 *   attachListenersToJFrame(screen.getFullScreenWindow());
 *   while (theGameIsRunning()) {
 *     doStuff();
 *     Graphics2D g = screen.getGraphics();
 *     currentState.draw(g);
 *     g.dispose();
 *     screen.update();
 *   }
 *   screen.restoreScreen();
 *
 * This class is known to work correctly on:
 *   Windows 98SE, 2000, XP
 *   X11R6, X11R7
 *   OSX 10.2?
 * It probably works correctly on Windows Vista/7/8 as well.
 */

/**
    The ScreenManager class manages initializing and displaying
    full screen graphics modes.
*/
public class ScreenManager {
  private GraphicsDevice device;
  private DisplayMode oldMode;

  /**
    The NullRepaintManager is a RepaintManager that doesn't do anything. Useful
    when all the rendering is done manually in the application.

    Note that this breaks AWT-based repainting (ie, frame.repaint() and such)
    for all components in the current JVM.
  */
  private static final class NullRepaintManager extends RepaintManager {
    public void addInvalidComponent(JComponent c) { }
    public void addDirtyRegion(JComponent c, int x, int y, int w, int h) { }
    public void markCompletelyDirty(JComponent c) { }
    public void paintDirtyRegions() { }
  }

  /**
      Creates a new ScreenManager object.
  */
  public ScreenManager() {
    GraphicsEnvironment environment=GraphicsEnvironment.getLocalGraphicsEnvironment();
    device=environment.getDefaultScreenDevice();

    oldMode=getCurrentDisplayMode();
  }

  /**
     Installs the NullRepaintManager.
  */
  public void installNullRepaintManager() {
    RepaintManager rm = new NullRepaintManager();
    rm.setDoubleBufferingEnabled(false);
    RepaintManager.setCurrentManager(rm);
  }


  /**
      Returns a list of compatible display modes for the
      default device on the system.
  */
  public DisplayMode[] getCompatibleDisplayModes() {
    return device.getDisplayModes();
  }


  /**
      Returns the first compatible mode in a list of modes.
      Returns null if no modes are compatible.
  */
  public DisplayMode findFirstCompatibleMode(DisplayMode[] modes) {
    DisplayMode[] goodModes=device.getDisplayModes();
    for (int i=0; i<modes.length; ++i) {
      for (int j=0; j<goodModes.length; ++j) {
        if (displayModesMatch(modes[i], goodModes[j])) {
            return modes[i];
        }
      }
    }

    return null;
  }

  /**
      Fix for bug in Win2K - Refresh rate must NOT be 0; we'll get
      the highest refresh rate supported for this resolution and
      bit depth.
  */
  public DisplayMode resolveDisplayMode(DisplayMode mode) {
    if (mode==null) return mode;

    DisplayMode[] modes=getCompatibleDisplayModes();

    int maxRefresh=0;

    for (DisplayMode mode1: modes) {
      if (displayModesMatch(mode1, mode))
        maxRefresh=Math.max(maxRefresh, mode1.getRefreshRate());
    }

    return new DisplayMode(mode.getWidth(), mode.getHeight(),
                            mode.getBitDepth(), maxRefresh);
  }


  /**
      Returns the current display mode.
  */
  public DisplayMode getCurrentDisplayMode() {
    return device.getDisplayMode();
  }


  /**
      Determines if two display modes "match". Two display
      modes match if they have the same resolution, bit depth,
      and refresh rate. The bit depth is ignored if one of the
      modes has a bit depth of DisplayMode.BIT_DEPTH_MULTI.
      Likewise, the refresh rate is ignored if one of the
      modes has a refresh rate of
      DisplayMode.REFRESH_RATE_UNKNOWN.
  */
  public boolean displayModesMatch(DisplayMode mode1, DisplayMode mode2) {
    if (mode1.getWidth()!=mode2.getWidth() ||
        mode1.getHeight()!=mode2.getHeight())
      return false;

    if (mode1.getBitDepth()!=DisplayMode.BIT_DEPTH_MULTI &&
        mode2.getBitDepth()!=DisplayMode.BIT_DEPTH_MULTI &&
        mode1.getBitDepth()!=mode2.getBitDepth())
      return false;

    if (mode1.getRefreshRate()!=
        DisplayMode.REFRESH_RATE_UNKNOWN &&
        mode2.getRefreshRate()!=
        DisplayMode.REFRESH_RATE_UNKNOWN &&
        mode1.getRefreshRate()!=mode2.getRefreshRate())
      return false;

    return true;
  }


  /**
      Enters full screen mode and changes the display mode.
      If the specified display mode is null or not compatible
      with this device, or if the display mode cannot be
      changed on this system, the current display mode is used.
      <p>
      The display uses a BufferStrategy with 2 buffers.
  */
  public void setFullScreen(DisplayMode displayMode) {
    final JFrame frame=new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setUndecorated(true);
    frame.setIgnoreRepaint(true);
    frame.setResizable(false);

    device.setFullScreenWindow(frame);

    if (displayMode != null && device.isDisplayChangeSupported()) {
      try {
        device.setDisplayMode(displayMode);
      } catch (IllegalArgumentException ex) {
        ex.printStackTrace();
      }
      // fix some systems
      frame.setSize(displayMode.getWidth(), displayMode.getHeight());
    }
    // avoid potential deadlock in 1.4.1_02
    try {
      EventQueue.invokeAndWait(new Runnable() {
        public void run() {
            frame.createBufferStrategy(2);
        }
      });
    }
    catch (InterruptedException ex) {
        // ignore
    }
    catch (InvocationTargetException  ex) {
        // ignore
    }
  }

  public void setDisplayMode(DisplayMode mode) {
    if (mode==null) mode=oldMode;

    if (!device.isDisplayChangeSupported()) return;

    try {
      device.setDisplayMode(mode);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }

    getFullScreenWindow().setSize(mode.getWidth(), mode.getHeight());
  }


  /**
      Gets the graphics context for the display. The
      ScreenManager uses double buffering, so applications must
      call update() to show any graphics drawn.
      <p>
      The application must dispose of the graphics object.
  */
  public Graphics2D getGraphics() {
    Window window=device.getFullScreenWindow();
    if (window!=null) {
      BufferStrategy strategy=window.getBufferStrategy();
      return (Graphics2D)strategy.getDrawGraphics();
    } else {
      return null;
    }
  }


  /**
      Updates the display.
  */
  public void update() {
    Window window=device.getFullScreenWindow();
    if (window!=null) {
      BufferStrategy strategy=window.getBufferStrategy();
      if (!strategy.contentsLost()) {
        strategy.show();
      }
    }
    // Sync the display on some systems.
    // (on Linux, this fixes event queue problems)
    Toolkit.getDefaultToolkit().sync();
  }


  /**
      Returns the window currently used in full screen mode.
      Returns null if the device is not in full screen mode.
  */
  public JFrame getFullScreenWindow() {
    return (JFrame)device.getFullScreenWindow();
  }


  /**
      Returns the width of the window currently used in full
      screen mode. Returns 0 if the device is not in full
      screen mode.
  */
  public int getWidth() {
    Window window=device.getFullScreenWindow();
    if (window!=null) return window.getWidth();
    else              return 0;
  }


  /**
      Returns the height of the window currently used in full
      screen mode. Returns 0 if the device is not in full
      screen mode.
  */
  public int getHeight() {
    Window window=device.getFullScreenWindow();
    if (window!=null) return window.getHeight();
    else              return 0;
  }


  /**
      Restores the screen's display mode.
  */
  public void restoreScreen() {
    if (oldMode!=null && device.isDisplayChangeSupported()) {
      try {
        device.setDisplayMode(oldMode);
      } catch (IllegalArgumentException ex) { }
    }

    Window window=device.getFullScreenWindow();
    if (window!=null) {
      window.dispose();
    }
    device.setFullScreenWindow(null);
  }


  /**
      Creates an image compatible with the current display.
  */
  public BufferedImage createCompatibleImage(int w, int h,
                                             int transparancy) {
    Window window=device.getFullScreenWindow();
    if (window!=null) {
      GraphicsConfiguration gc=window.getGraphicsConfiguration();
      return gc.createCompatibleImage(w, h, transparancy);
    }
    return null;
  }
}
