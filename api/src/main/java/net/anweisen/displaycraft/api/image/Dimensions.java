package net.anweisen.displaycraft.api.image;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class Dimensions {

  private final int x, y, width, height;

  public Dimensions(@Nonnegative int x, @Nonnegative int y, @Nonnegative int width, @Nonnegative int height) {
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

  @Nonnull
  public static Dimensions fromHotspot(int x, int y, int hotspotX, int hotspotY, int width, int height) {
    return new Dimensions(x - hotspotX, hotspotY - y, width, height);
  }

  @Nonnull
  public static Dimensions from(int x, int y, int width, int height) {
    return new Dimensions(x, y, width, height);
  }

  @Nonnull
  public static Dimensions fromFull(@Nonnull Image image) {
    return new Dimensions(0, 0, image.getWidth(), image.getHeight());
  }

  public boolean fits(int maxWidth, int maxHeight) {
    return !(x < 0 || y < 0 || width <= 0 || height <= 0 || x + width > maxWidth || y + height > maxHeight);
  }

  public boolean contains(int x, int y) {
    return x >= this.x && y >= this.y && x < this.x + width && y < this.y + height;
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions withMargin(int top, int right, int bottom, int left) {
    return new Dimensions(x + left, y + top, width - left - right, height - top - bottom);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions withMargin(int horizontal, int vertical) {
    return withMargin(horizontal, vertical, horizontal, vertical);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions withMargin(int margin) {
    return withMargin(margin, margin);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions withMoved(int x, int y) {
    return new Dimensions(this.x + x, this.y + y, width, height);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions withX(int x) {
    return new Dimensions(x, y, width, height);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions withY(int y) {
    return new Dimensions(x, y, width, height);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions withWidth(int width) {
    return new Dimensions(x, y, width, height);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions withHeight(int height) {
    return new Dimensions(x, y, width, height);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions addX(int x) {
    return withX(this.x + x);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions addY(int y) {
    return withY(this.y + y);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions addWidth(int width) {
    return withWidth(this.width + width);
  }

  @Nonnull
  @CheckReturnValue
  public Dimensions addHeight(int height) {
    return withHeight(this.height + height);
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
