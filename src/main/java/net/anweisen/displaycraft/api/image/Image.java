package net.anweisen.displaycraft.api.image;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * We use a custom api for creating images, because using the java builtin awt classes
 * and converting these objects afterwards is taking too much time (up to 200ms).
 *
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Image {

  @Nonnegative
  int getWidth();

  @Nonnegative
  int getHeight();

  void fillRect(int x, int y, int width, int height);

  byte getColor();

  void setColor(byte color);

  @Nonnull
  Image clipImage(int x, int y, int width, int height);

  @Nonnull
  Image copy();

  @Nonnull
  byte[] getContent();

}
