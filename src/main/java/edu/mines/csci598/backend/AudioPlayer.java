package edu.mines.csci598.backend;

import java.util.*;
import javax.sound.sampled.*;

/**
 * Performs mixing of audio and logical inversion of the audio system.
 *
 * The AudioPlayer reads from multiple AudioSources, at a time that is
 * guaranteed to be while the game is not doing anything else (so it is
 * effectively single-threaded). It then adjusts the volume of each stream and
 * mixes it into one. (Mixing is handled in software by this class since Java
 * Sound is half-broken on many UNIX systems.)
 */
public final class AudioPlayer extends Thread {
  public static final AudioFormat FORMAT =
    new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    44100, 16, 1, 2, 44100, false);

  private static final int BUFFER_SIZE = 4410; //0.1 seconds

  private static final class Source {
    AudioSource source;
    short volume;
  }

  private final GameManager manager;
  private final LinkedList<Source> sources = new LinkedList<Source>();
  private final short sourceBuffer[] = new short[BUFFER_SIZE];
  private final short mixedBuffer[] = new short[BUFFER_SIZE];
  private final byte byteBuffer[] = new byte[BUFFER_SIZE*2];
  private boolean running = true;
  private SourceDataLine line;

  /* package-private */ AudioPlayer(GameManager man) {
    super("AudioPlayer");
    setDaemon(true);
    manager = man;

    try {
      line = AudioSystem.getSourceDataLine(FORMAT);
      line.open();
      line.start();
      //There are multiple exception types which can be thrown which do not
      //share a common superclass (other than Exception) and which indicate
      //essentially the same thing.
    } catch (Exception e) {
      System.err.println("Could not open audio line: " + e);
      running = false;
    }
  }

  public void run() {
    while (running) {
      //Zero out the mix buffer
      for (int i = 0; i < BUFFER_SIZE; ++i) mixedBuffer[i] = 0;
      //Get data from each source and mix into the mixed buffer
      //(Sync on the manager since games assume they are single-threaded; we
      //need to hold the lock so we can poll all the sources without being
      //interrupted by another frame.)
      synchronized (manager) {
        for (ListIterator<Source> it = sources.listIterator(); it.hasNext();) {
          Source src = it.next();
          int len = src.source.read(sourceBuffer, BUFFER_SIZE);
          if (len == -1) {
            it.remove();
            continue;
          }

          //Adjust volume and mix in
          for (int i = 0; i < len; ++i) {
            int sample = sourceBuffer[i];
            sample *= src.volume;
            sample >>= 15;
            sample += mixedBuffer[i];
            if (sample > Short.MAX_VALUE)
              sample = Short.MAX_VALUE;
            else if (sample < Short.MIN_VALUE)
              sample = Short.MIN_VALUE;

            mixedBuffer[i] = (short)sample;
          }
        }
      }

      //Blit the short buffer into the byte buffer
      for (int i = 0; i < BUFFER_SIZE; ++i) {
        byteBuffer[i*2+0] = (byte)(mixedBuffer[i] & 0xFF);
        byteBuffer[i*2+1] = (byte)((mixedBuffer[i] >>> 8) & 0xFF);
      }

      //Write to line
      line.write(byteBuffer, 0, byteBuffer.length);
    }

    if (line != null) {
      line.drain();
      line.close();
    }
  }

  /* package-private */ void stopPlaying() {
    running = false;
  }

  /**
   * Adds the given AudioSource to the list of sources, with the volume
   * given. A volume of 0 is mute, and Short.MAX_VALUE is maximum.
   *
   * The source will be played until it returns -1 from read or the AudioPlayer
   * is stopped.
   */
  public void addSource(AudioSource src, short volume) {
    if (!running || line == null) return;

    Source s = new Source();
    s.source = src;
    s.volume = volume;
    synchronized (manager) {
      sources.add(s);
    }
  }
}
