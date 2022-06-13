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

  /**
   * Draws an image, or a part to the target.
   * It will only draw a part of the image, if the image at the given position
   * would protrude beyond the image border.
   *
   * @param image    the image to draw
   * @param x        the x position of the image on the target image
   * @param y        the y position of the image on the target image
   * @param hotspotX the x offset of the image, locates the center of the image / start of drawing
   * @param hotspotY the y offset of the image, locates the center of the image / start of drawing
   * @param target   the image to draw onto
   */
  public static void drawImageEdge(@Nonnull Image image, int x, int y, int hotspotX, int hotspotY, @Nonnull Image target) {
    int positionX = x - hotspotX;
    int positionY = y - hotspotY;
    int offsetX = 0;
    int offsetY = 0;
    int width = image.getWidth();
    int height = image.getHeight();

    if (positionX + image.getWidth() > target.getWidth())
      width -= positionX + image.getWidth() - target.getWidth();
    if (positionY + image.getHeight() > target.getHeight())
      height -= positionY + image.getHeight() - target.getHeight();

    if (positionX < 0) {
      offsetX = -positionX;
      positionX = 0;
    }
    if (positionY < 0) {
      offsetY = -positionY;
      positionY = 0;
    }

    target.drawImagePart(positionX, positionY, offsetX, offsetY, width - offsetX, height - offsetY, image, false);
  }
}
