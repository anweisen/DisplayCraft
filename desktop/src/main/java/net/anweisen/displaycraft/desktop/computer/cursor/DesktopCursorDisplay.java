package net.anweisen.displaycraft.desktop.computer.cursor;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.Images;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopCursorDisplay {

  private final int hotspotX, hotspotY;
  private final Image image;

  public DesktopCursorDisplay(int hotspotX, int hotspotY, @Nonnull Image image) {
    this.hotspotX = hotspotX;
    this.hotspotY = hotspotY;
    this.image = image;
  }

  @Nonnull
  public static DesktopCursorDisplay getDefault() {
    byte t = 0, b = -49, w = 32;
    return new DesktopCursorDisplay(0, 0, Images.newImage(12, 21, new byte[]{
      b, t, t, t, t, t, t, t, t, t, t, t,
      b, b, t, t, t, t, t, t, t, t, t, t,
      b, w, b, t, t, t, t, t, t, t, t, t,
      b, w, w, b, t, t, t, t, t, t, t, t,
      b, w, w, w, b, t, t, t, t, t, t, t,
      b, w, w, w, w, b, t, t, t, t, t, t,
      b, w, w, w, w, w, b, t, t, t, t, t,
      b, w, w, w, w, w, w, b, t, t, t, t,
      b, w, w, w, w, w, w, w, b, t, t, t,
      b, w, w, w, w, w, w, w, w, b, t, t,
      b, w, w, w, w, w, w, w, w, w, b, t,
      b, w, w, w, w, w, w, b, b, b, b, b,
      b, w, w, w, b, w, w, b, t, t, t, t,
      b, w, w, b, b, w, w, b, t, t, t, t,
      b, w, b, t, t, b, w, w, b, t, t, t,
      b, b, t, t, t, b, w, w, b, t, t, t,
      b, t, t, t, t, t, b, w, w, b, t, t,
      t, t, t, t, t, t, b, w, w, b, t, t,
      t, t, t, t, t, t, t, b, w, w, b, t,
      t, t, t, t, t, t, t, b, w, w, b, t,
      t, t, t, t, t, t, t, t, b, b, t, t
    }));
  }

  @Nonnull
  public static DesktopCursorDisplay getCrosshair() {
    byte t = 0, b = -49, w = 32;
    return new DesktopCursorDisplay(6, 6, Images.newImage(11, 11, new byte[]{
      t, t, t, t, t, b, t, t, t, t, t,
      t, t, t, t, t, b, t, t, t, t, t,
      t, t, t, t, t, b, t, t, t, t, t,
      t, t, t, t, t, b, t, t, t, t, t,
      t, t, t, t, t, b, t, t, t, t, t,
      b, b, b, b, b, b, b, b, b, b, b,
      t, t, t, t, t, b, t, t, t, t, t,
      t, t, t, t, t, b, t, t, t, t, t,
      t, t, t, t, t, b, t, t, t, t, t,
      t, t, t, t, t, b, t, t, t, t, t,
      t, t, t, t, t, b, t, t, t, t, t,
    }));
  }

  public int getHotspotX() {
    return hotspotX;
  }

  public int getHotspotY() {
    return hotspotY;
  }

  public Image getImage() {
    return image;
  }

  public void draw(@Nonnull Image target, @Nonnull Cursor cursor) {
    int x = cursor.getAbsoluteX() - hotspotX;
    int y = cursor.getAbsoluteY() - hotspotY;
    int width = image.getWidth();
    int height = image.getHeight();

    if (x + image.getWidth() > target.getWidth())
      width -= x + image.getWidth() - target.getWidth();
    if (y + image.getHeight() > target.getHeight())
      height -= y + image.getHeight() - target.getHeight();

    target.drawImagePart(x, y, 0, 0, width, height, image);
  }
}
