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
   * There is no guaranty that there is actually a map, you have to validate the result your self.
   *
   * @param axis        the direction of the pending map(s)
   * @param coordinate  the block coordinate of the screen map on the wall axis (not the given axis)
   * @param origin      the origin location and looking direction
   * @param maxDistance the max distance from the origin in blocks
   * @param resolution  the resolution of the trace result
   * @return a new trace result or null if the sight won't hit the screen wall within the maxDistance
   */
  @Nullable
  TraceResult trace(@Nonnull Direction axis, int coordinate, @Nonnull Location origin, int maxDistance, int resolution);

  final class TraceResult {
    private final Position screen;
    private final float relX, relY;
    private final int absX, absY;

    public TraceResult(@Nonnull Position screen, float relX, float relY, int absX, int absY) {
      this.screen = screen;
      this.relX = relX;
      this.relY = relY;
      this.absX = absX;
      this.absY = absY;
    }

    @Nonnull
    public Position getScreen() {
      return screen;
    }

    public float getRelativeX() {
      return relX;
    }

    public float getRelativeY() {
      return relY;
    }

    public int getAbsoluteX() {
      return absX;
    }

    public int getAbsoluteY() {
      return absY;
    }

    @Override
    public String toString() {
      return "TraceResult[" + "x=" + relX + " y=" + relY + " at=" + screen + ']';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      TraceResult that = (TraceResult) o;
      return Float.compare(that.relX, relX) == 0 && Float.compare(that.relY, relY) == 0 && Objects.equals(screen, that.screen);
    }

    @Override
    public int hashCode() {
      return Objects.hash(screen, relX, relY);
    }
  }

}
