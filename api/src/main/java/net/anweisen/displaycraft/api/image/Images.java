package net.anweisen.displaycraft.api.image;

import net.anweisen.displaycraft.api.implementation.image.ImageImpl;
import javax.annotation.Nonnull;
import java.util.Arrays;

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

  @Nonnull
  public static Image newImage(int width, int height, @Nonnull byte[] content) {
    return new ImageImpl(width, height, content);
  }

  @Nonnull
  public static Image clipImage(int x, int y, int width, int height, int originalWidth, @Nonnull byte[] original) {
    byte[] result = new byte[width * height];
    for (int i = 0; i < height; i++) {
      System.arraycopy(original, (i + y) * originalWidth + x, result, i * width, width);
    }
    return newImage(width, height, result);
  }

  @Nonnull
  public static Image copyImage(@Nonnull Image image) {
    return newImage(image.getWidth(), image.getHeight(), Arrays.copyOf(image.getContent(), image.getContent().length));
  }

  public static void checkBounds(int position, int size, int max) {
    if (position + size > max)
      throw new IllegalArgumentException("Given positioning out of bounds (" + position + " + " + size + " > " + max + ")");
  }

  public static void checkPosition(int x, int y, int width, int height) {
    if (x < 0 || y < 0 || x >= width || y >= height)
      throw new IllegalArgumentException("Given position outside image size! given: (" + x + ", " + y + ") size: (" + width + ", " + height + ")");
  }

  public static void checkResolution(int width, int height, int size) {
    if (width * height != size)
      throw new IllegalArgumentException("Given resolutions dont match (" + width + ", " + height + " != " + size + ")");
  }

  public static void checkSizes(int width, int height) {
    if (width < 1)
      throw new IllegalArgumentException("Width cannot be negative or zero");
    if (height < 1)
      throw new IllegalArgumentException("Height cannot be negative or zero");
  }

}
