package edu.mines.csci598.recycler.backend;

/**
 * Describes a resource that needs to be manually released.
 *
 * Like Closeable, but its method doesn't throw IOException.
 */
public interface Destroyable {
  /**
   * Releases resources held by this object.
   */
  public void destroy();
}
