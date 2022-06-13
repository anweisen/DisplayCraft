package net.anweisen.displaycraft.desktop.computer.cursor;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.DrawHelper;
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
    DrawHelper.drawImageEdge(image, cursor.getAbsoluteX(), cursor.getAbsoluteY(), hotspotX, hotspotY, target);
  }

  public static final class DefaultCursors {

    private static final byte t = 0, b = -49, w = 34;

    @Nonnull
    public static DesktopCursorDisplay getDefault() {
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
        t, t, t, t, t, t, t, t, b, b, t, t,
      }));
    }

    @Nonnull
    public static DesktopCursorDisplay getCrosshair() {
      return new DesktopCursorDisplay(6, 6, Images.newImage(11, 11, new byte[]{
        t, t, t, t, b, b, b, t, t, t, t,
        t, t, t, t, b, w, b, t, t, t, t,
        t, t, t, t, b, w, b, t, t, t, t,
        t, t, t, t, b, w, b, t, t, t, t,
        b, b, b, b, b, w, b, b, b, b, b,
        b, w, w, w, w, w, w, w, w, w, b,
        b, b, b, b, b, w, b, b, b, b, b,
        t, t, t, t, b, w, b, t, t, t, t,
        t, t, t, t, b, w, b, t, t, t, t,
        t, t, t, t, b, w, b, t, t, t, t,
        t, t, t, t, b, b, b, t, t, t, t,
      }));
    }

    @Nonnull
    public static DesktopCursorDisplay getScaleHorizontal() {
      return new DesktopCursorDisplay(11, 5, Images.newImage(21, 9, new byte[]{
        t, t, t, t, b, b, t, t, t, t, t, t, t, t, t, b, b, t, t, t, t,
        t, t, t, b, w, b, t, t, t, t, t, t, t, t, t, b, w, b, t, t, t,
        t, t, b, w, w, b, t, t, t, t, t, t, t, t, t, b, w, w, b, t, t,
        t, b, w, w, w, b, b, b, b, b, b, b, b, b, b, b, w, w, w, b, t,
        b, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, b,
        t, b, w, w, w, b, b, b, b, b, b, b, b, b, b, b, w, w, w, b, t,
        t, t, b, w, w, b, t, t, t, t, t, t, t, t, t, b, w, w, b, t, t,
        t, t, t, b, w, b, t, t, t, t, t, t, t, t, t, b, w, b, t, t, t,
        t, t, t, t, b, b, t, t, t, t, t, t, t, t, t, b, b, t, t, t, t,
      }));
    }

    @Nonnull
    public static DesktopCursorDisplay getScaleVertical() {
      return new DesktopCursorDisplay(5, 11, Images.newImage(9, 21, new byte[]{
        t, t, t, t, b, t, t, t, t,
        t, t, t, b, w, b, t, t, t,
        t, t, b, w, w, w, b, t, t,
        t, b, w, w, w, w, w, b, t,
        b, w, w, w, w, w, w, w, b,
        b, b, b, b, w, b, b, b, b,
        t, t, t, b, w, b, t, t, t,
        t, t, t, b, w, b, t, t, t,
        t, t, t, b, w, b, t, t, t,
        t, t, t, b, w, b, t, t, t,
        t, t, t, b, w, b, t, t, t,
        t, t, t, b, w, b, t, t, t,
        t, t, t, b, w, b, t, t, t,
        t, t, t, b, w, b, t, t, t,
        t, t, t, b, w, b, t, t, t,
        b, b, b, b, w, b, b, b, b,
        b, w, w, w, w, w, w, w, b,
        t, b, w, w, w, w, w, b, t,
        t, t, b, w, w, w, b, t, t,
        t, t, t, b, w, b, t, t, t,
        t, t, t, t, b, t, t, t, t,
      }));
    }

    @Nonnull
    public static DesktopCursorDisplay getScaleDiagonalRight() {
      return new DesktopCursorDisplay(8, 8, Images.newImage(15, 15, new byte[]{
        t, b, b, b, b, b, t, t, t, t, t, t, t, t, t,
        b, w, w, w, w, w, b, t, t, t, t, t, t, t, t,
        b, w, w, w, w, b, t, t, t, t, t, t, t, t, t,
        b, w, w, w, b, t, t, t, t, t, t, t, t, t, t,
        b, w, w, b, w, b, t, t, t, t, t, t, t, t, t,
        b, w, b, t, b, w, b, t, t, t, t, t, t, t, t,
        t, b, t, t, t, b, w, b, t, t, t, t, t, t, t,
        t, t, t, t, t, t, b, w, b, t, t, t, t, t, t,
        t, t, t, t, t, t, t, b, w, b, t, t, t, b, t,
        t, t, t, t, t, t, t, t, b, w, b, t, b, w, b,
        t, t, t, t, t, t, t, t, t, b, w, b, w, w, b,
        t, t, t, t, t, t, t, t, t, t, b, w, w, w, b,
        t, t, t, t, t, t, t, t, t, b, w, w, w, w, b,
        t, t, t, t, t, t, t, t, b, w, w, w, w, w, b,
        t, t, t, t, t, t, t, t, t, b, b, b, b, b, t,
      }));
    }


    @Nonnull
    public static DesktopCursorDisplay getScaleDiagonalLeft() {
      return new DesktopCursorDisplay(8, 8, Images.newImage(15, 15, new byte[]{
        t, t, t, t, t, t, t, t, t, b, b, b, b, b, t,
        t, t, t, t, t, t, t, t, b, w, w, w, w, w, b,
        t, t, t, t, t, t, t, t, t, b, w, w, w, w, b,
        t, t, t, t, t, t, t, t, t, t, b, w, w, w, b,
        t, t, t, t, t, t, t, t, t, b, w, b, w, w, b,
        t, t, t, t, t, t, t, t, b, w, b, t, b, w, b,
        t, t, t, t, t, t, t, b, w, b, t, t, t, b, t,
        t, t, t, t, t, t, b, w, b, t, t, t, t, t, t,
        t, b, t, t, t, b, w, b, t, t, t, t, t, t, t,
        b, w, b, t, b, w, b, t, t, t, t, t, t, t, t,
        b, w, w, b, w, b, t, t, t, t, t, t, t, t, t,
        b, w, w, w, b, t, t, t, t, t, t, t, t, t, t,
        b, w, w, w, w, b, t, t, t, t, t, t, t, t, t,
        b, w, w, w, w, w, b, t, t, t, t, t, t, t, t,
        t, b, b, b, b, b, t, t, t, t, t, t, t, t, t,
      }));
    }

    @Nonnull
    public static DesktopCursorDisplay getMove() {
      return new DesktopCursorDisplay(12, 12, Images.newImage(23, 23, new byte[]{
        t, t, t, t, t, t, t, t, t, t, t, b, t, t, t, t, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, t, t, t, b, w, b, t, t, t, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, t, t, b, w, w, w, b, t, t, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, t, b, w, w, w, w, w, b, t, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, b, w, w, w, w, w, w, w, b, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, b, b, b, b, w, b, b, b, b, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, t, t, t, b, w, b, t, t, t, t, t, t, t, t, t, t,
        t, t, t, t, b, b, t, t, t, t, b, w, b, t, t, t, t, b, b, t, t, t, t,
        t, t, t, b, w, b, t, t, t, t, b, w, b, t, t, t, t, b, w, b, t, t, t,
        t, t, b, w, w, b, t, t, t, t, b, w, b, t, t, t, t, b, w, w, b, t, t,
        t, b, w, w, w, b, b, b, b, b, b, w, b, b, b, b, b, b, w, w, w, b, t,
        b, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, w, b,
        t, b, w, w, w, b, b, b, b, b, b, w, b, b, b, b, b, b, w, w, w, b, t,
        t, t, b, w, w, b, t, t, t, t, b, w, b, t, t, t, t, b, w, w, b, t, t,
        t, t, t, b, w, b, t, t, t, t, b, w, b, t, t, t, t, b, w, b, t, t, t,
        t, t, t, t, b, b, t, t, t, t, b, w, b, t, t, t, t, b, b, t, t, t, t,
        t, t, t, t, t, t, t, t, t, t, b, w, b, t, t, t, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, b, b, b, b, w, b, b, b, b, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, b, w, w, w, w, w, w, w, b, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, t, b, w, w, w, w, w, b, t, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, t, t, b, w, w, w, b, t, t, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, t, t, t, b, w, b, t, t, t, t, t, t, t, t, t, t,
        t, t, t, t, t, t, t, t, t, t, t, b, t, t, t, t, t, t, t, t, t, t, t,
      }));
    }

  }
}
