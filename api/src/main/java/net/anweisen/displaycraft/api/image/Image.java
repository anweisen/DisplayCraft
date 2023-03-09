package net.anweisen.displaycraft.api.image;

import net.anweisen.displaycraft.api.image.size.Dimensions;
import net.anweisen.displaycraft.api.image.size.DrawOrigin;
import net.anweisen.displaycraft.api.image.size.Scaling;
import net.anweisen.displaycraft.api.image.size.Sized;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.awt.*;

/**
 * We use a custom api for creating images, because using the java builtin awt classes
 * and converting these objects afterwards is taking too much time (up to 200ms).
 *
 * @author anweisen | https://github.com/anweisen
 * @see Images
 * @see DrawHelper
 * @since 1.0
 */
public interface Image extends Sized {

  @Override
  @Nonnegative
  int getWidth();

  @Override
  @Nonnegative
  int getHeight();

  byte getCurrentColor();

  void setCurrentColor(byte color);

  void drawPixel(int x, int y);

  void fillRect(int x, int y, int width, int height);

  void fillRect(@Nonnull Dimensions dimensions);

  @Deprecated
  void fillRect(@Nonnull DrawOrigin origin, @Nonnull Scaling x, @Nonnull Scaling y, @Nonnull Scaling width, @Nonnull Scaling height);

  void drawStroke(int startX, int startY, int destinationX, int destinationY, int size);

  @Deprecated
  void drawStroke(@Nonnull Scaling startX, @Nonnull Scaling startY, @Nonnull Scaling destinationX, @Nonnull Scaling destinationY, @Nonnull Scaling size);

  void drawCircle(int x, int y, int radius);

  void drawCircle(int x, int y, int radius, int thickness);

  @Deprecated
  void drawCircle(@Nonnull DrawOrigin origin, @Nonnull Scaling x, @Nonnull Scaling y, @Nonnull Scaling radius, @Nonnull Scaling thickness);

  void fillCircle(int x, int y, int radius);

  @Deprecated
  void fillCircle(@Nonnull DrawOrigin origin, @Nonnull Scaling x, @Nonnull Scaling y, @Nonnull Scaling radius);

  void drawRoundRect(int x, int y, int width, int height, int arcRadius);

  void drawRoundRect(int x, int y, int width, int height, int arcRadius, int thickness);

  @Deprecated
  void drawRoundRect(@Nonnull DrawOrigin origin, @Nonnull Scaling x, @Nonnull Scaling y, @Nonnull Scaling width, @Nonnull Scaling height, @Nonnull Scaling arcRadius, @Nonnull Scaling thickness);

  void fillRoundRect(int x, int y, int width, int height, int arcRadius);

  @Deprecated
  void fillRoundRect(@Nonnull DrawOrigin origin, @Nonnull Scaling x, @Nonnull Scaling y, @Nonnull Scaling width, @Nonnull Scaling height, @Nonnull Scaling arcRadius);

  void drawString(int x, int y, @Nonnull Font font, @Nonnull String text);

  void drawString(int x, int y, @Nonnull DrawOrigin origin, @Nonnull Font font, @Nonnull String text);

  @Deprecated
  void drawString(@Nonnull Scaling x, @Nonnull Scaling y, @Nonnull DrawOrigin origin, @Nonnull Font font, @Nonnull String text);

  void drawImage(int x, int y, @Nonnull Image image);

  void drawImage(int destinationX, int destinationY, int sourceX, int sourceY, int width, int height, @Nonnull byte[] image, boolean overwriteAsTransparent);

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
