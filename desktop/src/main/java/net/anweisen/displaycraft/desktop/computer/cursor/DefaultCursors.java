package net.anweisen.displaycraft.desktop.computer.cursor;

import net.anweisen.displaycraft.api.image.Images;
import net.anweisen.displaycraft.api.image.ui.Sprite;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class DefaultCursors {

  private static final byte t = 0, b = -49, w = 34;

  @Nonnull
  public static Sprite getDefault() {
    return new Sprite(0, 0, Images.newImage(12, 21, new byte[]{
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
  public static Sprite getCrosshair() {
    return new Sprite(6, 6, Images.newImage(11, 11, new byte[]{
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
  public static Sprite getScaleHorizontal() {
    return new Sprite(11, 5, Images.newImage(21, 9, new byte[]{
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
  public static Sprite getScaleVertical() {
    return new Sprite(5, 11, Images.newImage(9, 21, new byte[]{
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
  public static Sprite getScaleDiagonalRight() {
    return new Sprite(8, 8, Images.newImage(15, 15, new byte[]{
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
  public static Sprite getScaleDiagonalLeft() {
    return new Sprite(8, 8, Images.newImage(15, 15, new byte[]{
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
  public static Sprite getMove() {
    return new Sprite(12, 12, Images.newImage(23, 23, new byte[]{
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

  @Nonnull
  public static Sprite getEmpty() {
    return new Sprite(0, 0, Images.zero());
  }

}
