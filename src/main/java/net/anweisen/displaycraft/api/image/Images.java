package net.anweisen.displaycraft.api.image;

import net.anweisen.displaycraft.api.implementation.image.ImageImpl;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class Images {

  public static final int RESOLUTION = 128;
  public static final int BORDER = RESOLUTION - 1;
  public static final int BUFFER_SIZE = RESOLUTION * RESOLUTION;

  private Images() {
  }

  @Nonnull
  public static Image newImage(int width, int height) {
    return new ImageImpl(width, height);
  }

  public static void checkBounds(int position, int size, int max) {
    if (position + size > max)
      throw new IllegalArgumentException("Given positioning " + " out of bounds (" + position + " + " + size + " > " + max + ")");
  }

}
