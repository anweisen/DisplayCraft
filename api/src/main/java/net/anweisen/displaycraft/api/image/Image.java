package net.anweisen.displaycraft.api.image;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * We use a custom api for creating images, because using the java builtin awt classes
 * and converting these objects afterwards is taking too much time (up to 200ms).
 *
 * @author anweisen | https://github.com/anweisen
 * @see Images
 * @see DrawHelper
 * @since 1.0
 */
public interface Image {

  @Nonnegative
  int getWidth();

  @Nonnegative
  int getHeight();

  byte getCurrentColor();

  void setCurrentColor(byte color);

  void drawPixel(int x, int y);

  void fillRect(int x, int y, int width, int height);

  void fillRect(@Nonnull Dimensions dimensions);

  void drawStroke(int startX, int startY, int destinationX, int destinationY, int size);

  void drawImage(int x, int y, @Nonnull Image image);

  void drawImagePart(int destinationX, int destinationY, int sourceX, int sourceY, int width, int height, @Nonnull Image image, boolean overwriteAsTransparent);

  void drawImagePart(int destinationX, int destinationY, @Nonnull Dimensions dimensions, @Nonnull Image image);

  @Nonnull
  Image clipImage(int x, int y, int width, int height);

  @Nonnull
  Image clipImage(@Nonnull Dimensions dimensions);

  @Nonnull
  Image copy();

  boolean contains(int x, int y);

  byte getPixel(int x, int y);

  @Nonnull
  byte[] getContent();

}
