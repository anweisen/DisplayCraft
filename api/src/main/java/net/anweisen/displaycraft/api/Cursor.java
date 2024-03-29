package net.anweisen.displaycraft.api;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class Cursor {

  private final float relativeX, relativeY;
  private final int absoluteX, absoluteY;

  public Cursor(@Nonnegative float relativeX, @Nonnegative float relativeY, @Nonnegative int absoluteX, @Nonnegative int absoluteY) {
    if (relativeX < 0 || relativeX > 1 || relativeY < 0 || relativeY > 1)
      throw new IllegalArgumentException("Relative positions have to be between 0 and 1 (" + relativeX + ", " + relativeY + ")");

    this.relativeX = relativeX;
    this.relativeY = relativeY;
    this.absoluteX = absoluteX;
    this.absoluteY = absoluteY;
  }

  @Nonnull
  public static Cursor fromRelatives(float relativeX, float relativeY, int resolution) {
    return fromRelatives(relativeX, relativeY, resolution, resolution);
  }

  @Nonnull
  public static Cursor fromRelatives(float relativeX, float relativeY, int resolutionX, int resolutionY) {
    return new Cursor(relativeX, relativeY, (int) ((resolutionX - 1) * relativeX), (int) ((resolutionY - 1) * relativeY));
  }

  @Nonnull
  public static Cursor scaleRelatives(@Nonnull Cursor relatives, int screenX, int screenY, int amountX, int amountY, int tileResolution) {
    return fromRelatives((screenX + relatives.getRelativeX()) / amountX, (screenY + relatives.getRelativeY()) / amountY, tileResolution * amountX, tileResolution * amountY);
  }

  @Nonnull
  public static Cursor fromAbsolutes(int absoluteX, int absoluteY, int resolution) {
    return fromAbsolutes(absoluteX, absoluteY, resolution, resolution);
  }

  @Nonnull
  public static Cursor fromAbsolutes(int absoluteX, int absoluteY, int resolutionX, int resolutionY) {
    return new Cursor((float) absoluteX / resolutionX, (float) absoluteY / resolutionY, absoluteX, absoluteY);
  }

  @Nonnull
  public static Cursor from(float relativeX, float relativeY, int absoluteX, int absoluteY) {
    return new Cursor(relativeX, relativeY, absoluteX, absoluteY);
  }

  public float getRelativeX() {
    return relativeX;
  }

  public float getRelativeY() {
    return relativeY;
  }

  public int getAbsoluteX() {
    return absoluteX;
  }

  public int getAbsoluteY() {
    return absoluteY;
  }

  public int getMaxWidth() {
    return (int) (1d / relativeX * absoluteX);
  }

  public int getMaxHeight() {
    return (int) (1d / relativeY * absoluteY);
  }

  @Nonnull
  @CheckReturnValue
  public Cursor multiply(float x, float y) {
    return new Cursor(relativeX * x, relativeY * y, (int) (absoluteX * x), (int) (absoluteY * y));
  }

  @Override
  public String toString() {
    return "Cursor[" + "x=" + relativeX + " y=" + relativeY + " X=" + absoluteX + " Y=" + absoluteY + ']';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cursor that = (Cursor) o;
    return Float.compare(that.relativeX, relativeX) == 0 && Float.compare(that.relativeY, relativeY) == 0 && absoluteX == that.absoluteX && absoluteY == that.absoluteY;
  }

  @Override
  public int hashCode() {
    return Objects.hash(relativeX, relativeY, absoluteX, absoluteY);
  }
}
