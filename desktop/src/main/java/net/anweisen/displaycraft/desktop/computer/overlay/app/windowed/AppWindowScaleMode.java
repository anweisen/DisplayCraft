package net.anweisen.displaycraft.desktop.computer.overlay.app.windowed;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.Dimensions;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay;
import net.anweisen.displaycraft.desktop.computer.cursor.DesktopCursorDisplay.DefaultCursors;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public enum AppWindowScaleMode {

  TOP(DefaultCursors.getScaleVertical(), 0, -1, (dimensions, cursor, threshold) -> cursor.getAbsoluteY() - dimensions.getY() < threshold),
  BOTTOM(DefaultCursors.getScaleVertical(), 0, 1, (dimensions, cursor, threshold) -> dimensions.getY() + dimensions.getHeight() - cursor.getAbsoluteY() < threshold),
  LEFT(DefaultCursors.getScaleHorizontal(), -1, 0, (dimensions, cursor, threshold) -> cursor.getAbsoluteX() - dimensions.getX() < threshold),
  RIGHT(DefaultCursors.getScaleHorizontal(), 1, 0, (dimensions, cursor, threshold) -> dimensions.getX() + dimensions.getWidth() - cursor.getAbsoluteX() < threshold),

  TOP_LEFT(DefaultCursors.getScaleDiagonalRight(), AppWindowScaleMode.TOP, AppWindowScaleMode.LEFT),
  TOP_RIGHT(DefaultCursors.getScaleDiagonalLeft(), AppWindowScaleMode.TOP, AppWindowScaleMode.RIGHT),
  BOTTOM_RIGHT(DefaultCursors.getScaleDiagonalRight(), AppWindowScaleMode.BOTTOM, AppWindowScaleMode.RIGHT),
  BOTTOM_LEFT(DefaultCursors.getScaleDiagonalLeft(), AppWindowScaleMode.BOTTOM, AppWindowScaleMode.LEFT),
  ;

  private final DesktopCursorDisplay cursor;
  private final Tester tester;
  private final int modX, modY;

  AppWindowScaleMode(@Nonnull DesktopCursorDisplay cursorDisplay, int modX, int modY, @Nonnull Tester tester) {
    this.cursor = cursorDisplay;
    this.modX = modX;
    this.modY = modY;
    this.tester = tester;
  }

  AppWindowScaleMode(@Nonnull DesktopCursorDisplay cursorDisplay, @Nonnull AppWindowScaleMode firstMode, @Nonnull AppWindowScaleMode secondMode) {
    this.tester = firstMode.tester.and(secondMode.tester);
    this.modX = firstMode.modX + secondMode.modX;
    this.modY = firstMode.modY + secondMode.modY;
    this.cursor = cursorDisplay;
  }

  @Nullable
  public static AppWindowScaleMode findApplying(@Nonnull Dimensions dimensions, @Nonnull Cursor cursor, int threshold) {
    for (int i = values().length - 1; i >= 0; i--) {
      AppWindowScaleMode mode = values()[i];

      if (mode.tester.test(dimensions, cursor, threshold))
        return mode;
    }

    return null;
  }

  @Nonnull
  public DesktopCursorDisplay getCursor() {
    return cursor;
  }

  @Nonnull
  public Tester getTester() {
    return tester;
  }

  public int getModX() {
    return modX;
  }

  public int getModY() {
    return modY;
  }

  @FunctionalInterface
  public interface Tester {

    boolean test(@Nonnull Dimensions dimensions, @Nonnull Cursor cursor, int threshold);

    @Nonnull
    @CheckReturnValue
    default Tester and(@Nonnull Tester other) {
      return (dimensions, cursor, threshold) -> this.test(dimensions, cursor, threshold) && other.test(dimensions, cursor, threshold);
    }

    @Nonnull
    @CheckReturnValue
    default Tester or(@Nonnull Tester other) {
      return (dimensions, cursor, threshold) -> this.test(dimensions, cursor, threshold) || other.test(dimensions, cursor, threshold);
    }

  }
}
