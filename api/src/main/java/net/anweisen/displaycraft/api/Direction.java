package net.anweisen.displaycraft.api;

import org.bukkit.util.Vector;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public enum Direction {

  NORTH(0, 0, -1),
  SOUTH(0, 0, 1),
  EAST(1, 0, 0),
  WEST(-1, 0, 0),

  /**
   * Not supported/implemented yet
   * <p>
   * Only available in higher versions (starting from 1.13+)
   *
   * @see #DOWN
   */
  @Deprecated
  UP(0, 1, 0),

  /**
   * Not supported/implemented yet
   * <p>
   * Only available in higher versions (starting from 1.13+)
   *
   * @see #UP
   */
  @Deprecated
  DOWN(0, -1, 0),
  ;

  private final int modX, modY, modZ;

  Direction(int modX, int modY, int modZ) {
    this.modX = modX;
    this.modY = modY;
    this.modZ = modZ;
  }

  public int getModX() {
    return modX;
  }

  public int getModY() {
    return modY;
  }

  public int getModZ() {
    return modZ;
  }

  public int getModEntirety() {
    return modX + modY + modZ;
  }

  @Nonnull
  public Direction getOpposite() {
    return ordinal() % 2 == 0 ? values()[ordinal() + 1] : values()[ordinal() - 1];
  }

  @Nonnull
  public Vector getVector() {
    return new Vector(modX, modY, modZ);
  }

  public <T> T getOnAxis(T x, T y, T z) {
    return switch (this) {
      case NORTH, SOUTH -> z;
      case WEST, EAST -> x;
      case UP, DOWN -> y;
    };
  }

}
