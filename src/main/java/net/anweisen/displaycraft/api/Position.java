package net.anweisen.displaycraft.api;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class Position {

  private final int x, y, z;
  private final Direction direction;

  public Position(int x, int y, int z, @Nonnull Direction direction) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.direction = direction;
  }

  @Nonnull
  public static Position from(@Nonnull Vector vector, @Nonnull Direction direction) {
    return new Position(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), direction);
  }

  @Nonnull
  public static Position from(@Nonnull Location location, @Nonnull Direction direction) {
    return new Position(location.getBlockX(), location.getBlockY(), location.getBlockZ(), direction);
  }

  @Nonnull
  public static Position from(int x, int y, int z, @Nonnull Direction direction) {
    return new Position(x, y, z, direction);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getZ() {
    return z;
  }

  @Nonnull
  public Direction getDirection() {
    return direction;
  }

  @Override
  public String toString() {
    return "Position[" +
      "" + x +
      ", " + y +
      ", " + z +
      " " + direction +
      ']';
  }
}
