package net.anweisen.displaycraft.api.implementation;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.Direction;
import net.anweisen.displaycraft.api.Position;
import net.anweisen.displaycraft.api.ScreenTracer;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ScreenTracerImpl implements ScreenTracer {

  @Nullable
  @Override
  public TraceResult trace(@Nonnull Direction axis, int coordinate, @Nonnull Location origin, int maxDistance, int resolution) {
    Direction opposite = axis.getOpposite();

    Vector direction = origin.getDirection().normalize();

    float fraction = 1f / resolution;
    Vector fractionVector = direction.clone().multiply(fraction);

    double screenCoordinate = coordinate + (Math.max(opposite.getModEntirety(), 0) * .95);

    Location position = origin.clone();
    double lastDistance = maxDistance;
    for (float f = 0; f < maxDistance; f += fraction) {
      Location next = position.clone().add(fractionVector);

      double distance = Math.abs(screenCoordinate - axis.getAxis(next.getX(), next.getY(), next.getZ()));
      if (distance > lastDistance) {

        Location relative = position.clone().subtract(position.getBlockX(), position.getBlockY(), position.getBlockZ());
        float relX = opposite == Direction.SOUTH || opposite == Direction.WEST ? 1 - (float) (axis.getModX() != 0 ? relative.getZ() : relative.getX()) : (float) (axis.getModX() != 0 ? relative.getZ() : relative.getX());
        float relY = 1 - (float) relative.getY();

        return new TraceResult(
          Position.from(position.getBlockX(), position.getBlockY(), position.getBlockZ(), axis),
          Cursor.fromRelatives(relX, relY, resolution)
        );
      }

      lastDistance = distance;
      position = next;
    }

    return null;
  }
}
