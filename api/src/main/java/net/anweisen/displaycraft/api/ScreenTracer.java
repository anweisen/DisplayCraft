package net.anweisen.displaycraft.api;

import org.bukkit.Location;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ScreenTracer {

  /**
   * Calculates the point of view on the given screen wall.
   * There is no guarantee that there is actually a map, you have to validate the result yourself
   *
   * @param axis        the direction of the pending map(s)
   * @param coordinate  the block coordinate of the screen map on the wall axis (not the given axis!)
   * @param origin      the origin location and looking direction
   * @param maxDistance the max distance from the origin in blocks
   * @param resolution  the resolution of the target screen and upcoming trace result
   * @return a new trace result or null if the sight won't hit the screen wall within the maxDistance
   */
  @Nullable
  TraceResult trace(@Nonnull Direction axis, int coordinate, @Nonnull Location origin, int maxDistance, int resolution);

  final class TraceResult {

    private final Position screen;
    private final Cursor cursor;

    public TraceResult(@Nonnull Position screen, @Nonnull Cursor cursor) {
      this.screen = screen;
      this.cursor = cursor;
    }

    @Nonnull
    public Position getScreen() {
      return screen;
    }

    @Nonnull
    public Cursor getCursor() {
      return cursor;
    }

    @Override
    public String toString() {
      return "TraceResult[" + "x=" + cursor.getRelativeX() + " y=" + cursor.getRelativeY() + " map=" + screen + ']';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      TraceResult that = (TraceResult) o;
      return Objects.equals(screen, that.screen) && Objects.equals(cursor, that.cursor);
    }

    @Override
    public int hashCode() {
      return Objects.hash(screen, cursor);
    }
  }

}
