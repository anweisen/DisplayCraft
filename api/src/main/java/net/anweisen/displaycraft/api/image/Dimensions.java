package net.anweisen.displaycraft.api.image;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class Dimensions {

  private final int x, y, width, height;

  public Dimensions(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  @Nonnull
  public static Dimensions fromCenter(int x, int y, int width, int height) {
    return new Dimensions(x - width / 2, y - height / 2, width, height);
  }

  @Nonnull
  public static Dimensions fromTopLeft(int x, int y, int width, int height) {
    return new Dimensions(x, y, width, height);
  }

  @Nonnull
  public static Dimensions fromTopRight(int x, int y, int width, int height) {
    return new Dimensions(x - width, y, width, height);
  }

  @Nonnull
  public static Dimensions fromBottomLeft(int x, int y, int width, int height) {
    return new Dimensions(x, y - height, width, height);
  }

  @Nonnull
  public static Dimensions fromBottomRight(int x, int y, int width, int height) {
    return new Dimensions(x - width, y - height, width, height);
  }

  public void verify(int maxWidth, int maxHeight) {
    if (x < 0 || y < 0 || width <= 0 || height <= 0)
      throw new IllegalArgumentException("Dimension position cannot be negative, size cannot be zero");
    if (x + width > maxWidth || y + height > maxHeight)
      throw new IllegalArgumentException(this + " out of bounds -> for (" + maxWidth + ", " + maxHeight + ")");
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions sliceCentered(int maxWidth, int maxHeight) {
    int x = this.x;
    int y = this.y;
    int width = this.width;
    int height = this.height;

    if (x < 0) {
      width -= Math.abs(x);
      x = 0;
    }
    if (y < 0) {
      height -= Math.abs(y);
      y = 0;
    }
    if (x + width > maxWidth) {
      width = width - x;
    }
    if (y + height > maxHeight) {
      height = height - y;
    }

    return new Dimensions(x, y, width, height);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public String toString() {
    return "Dimensions[" +
      "x=" + x +
      " y=" + y +
      " width=" + width +
      " height=" + height +
      ']';
  }
}
