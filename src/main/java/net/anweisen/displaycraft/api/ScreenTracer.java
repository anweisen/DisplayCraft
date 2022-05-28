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

    private final Coordinates coordinates;

    public TraceResult(@Nonnull Position screen, @Nonnull Coordinates coordinates) {
      this.screen = screen;
      this.coordinates = coordinates;
    }

    @Nonnull
    public Position getScreen() {
      return screen;
    }

    @Nonnull
    public Coordinates getCoordinates() {
      return coordinates;
    }

    @Override
    public String toString() {
      return "TraceResult[" + "x=" + coordinates.getRelativeX() + " y=" + coordinates.getRelativeY() + " map=" + screen + ']';
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      TraceResult that = (TraceResult) o;
      return Objects.equals(screen, that.screen) && Objects.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
      return Objects.hash(screen, coordinates);
    }
  }

}
