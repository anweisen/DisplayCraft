package net.anweisen.displaycraft.api;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import javax.annotation.Nonnull;
import java.util.Objects;

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

  public int getAxis() {
    return direction.getAxis(x, y, z);
  }

  @Nonnull
  public Location toLocation(@Nonnull World world) {
    return new Location(world, x, y, z).setDirection(direction.getVector());
  }

  @Override
  public String toString() {
    return "Position[" + x + ", " + y + ", " + z + ", " + direction + ']';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Position position = (Position) o;
    return x == position.x && y == position.y && z == position.z && direction == position.direction;
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z, direction);
  }
}
