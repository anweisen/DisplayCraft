package net.anweisen.displaycraft.api.image;

import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class DrawHelper {

  private DrawHelper() {
  }

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
