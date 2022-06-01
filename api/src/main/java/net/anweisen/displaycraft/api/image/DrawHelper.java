package net.anweisen.displaycraft.api.image;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class DrawHelper {

  private DrawHelper() {
  }

  /**
   * Fills the given image recursively like a fill bucket in paint from the given position.
   * If the stack heap is too small a {@link StackOverflowError} will be thrown (can be changed with -Xss[size] parameter).
   *
   * @param image       the target image
   * @param originColor the color to replace
   * @param x           the current x position
   * @param y           the current y position
   * @throws StackOverflowError if the stack heap is too small, use -Xss[size] to increase it, eg. -Xss32m
   */
  public static void fillBucket(@Nonnull Image image, byte originColor, int x, int y) {
    if (!image.contains(x, y)) return;
    if (image.getPixel(x, y) != originColor) return;

    image.drawPixel(x, y);

    fillBucket(image, originColor, x + 1, y);
    fillBucket(image, originColor, x, y + 1);
    fillBucket(image, originColor, x - 1, y);
    fillBucket(image, originColor, x, y - 1);
  }
}
