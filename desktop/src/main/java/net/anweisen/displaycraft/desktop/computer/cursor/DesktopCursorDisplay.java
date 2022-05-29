package net.anweisen.displaycraft.desktop.computer.cursor;

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

  public int getHotspotX() {
    return hotspotX;
  }

  public int getHotspotY() {
    return hotspotY;
  }

  public Image getImage() {
    return image;
  }
}
