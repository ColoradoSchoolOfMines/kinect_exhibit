package edu.mines.csci598.backend;

/**
 * Represents a source of audio data, conforming to AudioPlayer.FORMAT.
 */
public interface AudioSource {
  /**
   * Requests len samples of data to be written into dst.
   *
   * @param dst The target buffer
   * @param len The number of samples to write
   * @return The number of samples written. If -1 is returned, the AudioSource
   * is considered closed.
   */
  public int read(short[] dst, int len);
}
