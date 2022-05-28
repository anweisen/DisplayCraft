package net.anweisen.displaycraft.api.image;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * We use a custom api for creating images, because using the java builtin awt classes
 * and converting these objects afterwards is taking too much time (up to 200ms).
 *
 * @author anweisen | https://github.com/anweisen
 * @see Images
 * @since 1.0
 */
public interface Image {

  @Nonnegative
  int getWidth();

  @Nonnegative
  int getHeight();

  byte getColor();

  void setColor(byte color);

  void drawPixel(int x, int y);

  void fillRect(int x, int y, int width, int height);

  void drawImage(int x, int y, @Nonnull Image image);

  @Nonnull
  Image clipImage(int x, int y, int width, int height);

  @Nonnull
  Image copy();

  byte getPixel(int x, int y);

  @Nonnull
  byte[] getContent();

}