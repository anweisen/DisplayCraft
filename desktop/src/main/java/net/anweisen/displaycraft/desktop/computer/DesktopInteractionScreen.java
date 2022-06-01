package net.anweisen.displaycraft.desktop.computer;

import net.anweisen.displaycraft.DisplayCraft;
import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.Direction;
import net.anweisen.displaycraft.api.Position;
import net.anweisen.displaycraft.api.ScreenTracer;
import net.anweisen.displaycraft.api.image.Dimensions;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.Images;
import net.anweisen.displaycraft.nms.Reflect;
import net.anweisen.utility.common.collection.pair.Tuple;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopInteractionScreen {

  private final int resolution;
  private final int sizeX, sizeY;
  private final int width, height;

  /**
   * The map positions of the screen as a two-dimensional array matrix.
   * The first dimension of the matrix corresponds to the height (position[y]) and holds
   * the second dimension which represents the width (position[y][x]).
   * Each Tuple holds the map position as first value and the mapId as the second value.
   */
  private final Tuple<Position, Integer>[][] position;
  private final Direction axis;
  private final int coordinate;

  public DesktopInteractionScreen(int resolution, int sizeX, int sizeY, @Nonnull Tuple<Position, Integer>[][] position) {
    // check dimensions
    if (sizeX <= 0 || sizeY <= 0 || resolution <= 0)
      throw new IllegalArgumentException("Resolution or size cannot be zero or negative");
    if (position.length != sizeY)
      throw new IllegalArgumentException("The given map positions don't match the given height (" + position.length + " != " + sizeY + ")");
    for (var tuples : position) {
      if (tuples.length != sizeX)
        throw new IllegalArgumentException("The given map positions don't match the given width (" + tuples.length + " != " + sizeX + ")");
    }
    // check position axis
    Direction axis = null;
    Integer coordinate = null;
    for (var array : position) {
      for (var current : array) {
        if (axis != null && axis != current.getFirst().getDirection())
          throw new IllegalArgumentException("Not all screen positions are on the same axis! expected: " + axis + " got: " + current.getFirst());
        axis = current.getFirst().getDirection();

        if (coordinate != null && coordinate != current.getFirst().getAxis())
          throw new IllegalArgumentException("Not all screen positions are on the same axis coordinate! expected: " + coordinate + " got: " + current.getFirst());
        coordinate = current.getFirst().getAxis();
      }
    }
    this.axis = axis;
    this.coordinate = coordinate;

    this.resolution = resolution;
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.width = sizeX * resolution;
    this.height = sizeY * resolution;
    this.position = position;
  }

  @Nonnull
  @SuppressWarnings("unchecked") // generic array creation
  public static DesktopInteractionScreen withCenter(int resolution, int sizeX, int sizeY, @Nonnull Position center) {
    Direction direction = center.getDirection();
    Tuple<Position, Integer>[][] position = new Tuple[sizeY][sizeX];
    for (int y = 0; y < sizeY; y++) {
      for (int x = 0; x < sizeX; x++) {
        int posY = center.getY() + sizeY / 2 - y;
        // inflate direction 1. on other axis as we have to expand the screen sideways
        int posX = direction.getModX() != 0 ? center.getX() : center.getX() + (x - sizeX / 2);
        int posZ = direction.getModZ() != 0 ? center.getZ() : center.getZ() + (x - sizeX / 2);
        // TODO detect change positive or negative! [x -]/[- x]

        position[y][x] = Tuple.of(Position.from(posX, posY, posZ, direction), Reflect.newMapId());
      }
    }

    return new DesktopInteractionScreen(resolution, sizeX, sizeY, position);
  }

  @Nullable
  public Cursor trace(@Nonnull Player player, int maxDistance) {
    ScreenTracer.TraceResult result = DisplayCraft.getInstance().getScreenTracer().trace(axis, coordinate, player.getEyeLocation(), maxDistance, resolution);
    if (result == null) return null;

    for (int y = 0; y < sizeY; y++) {
      for (int x = 0; x < sizeX; x++) {
        if (position[y][x].getFirst().equals(result.getScreen())) {
          return Cursor.scaleRelatives(result.getCoordinates(), x, y, sizeX, sizeY, resolution);
        }
      }
    }

    return null;
  }

  public void render(@Nonnull Collection<? extends Player> viewers, @Nonnull Image image, @Nonnull RenderFilter filter) {
    if (image.getWidth() != width || image.getHeight() != height)
      throw new IllegalArgumentException("Dimensions of given image and screen dont match (" + width + ", " + height + ") != (" + image.getWidth() + ", " + image.getHeight() + ")");

    for (int y = 0; y < sizeY; y++) {
      for (int x = 0; x < sizeX; x++) {
        int mapId = position[y][x].getSecond();
        Image clip = image.clipImage(x * resolution, y * resolution, resolution, resolution);

        if (filter.shouldRender(clip, x, y)) {
          for (Player player : viewers) {
            DisplayCraft.getInstance().getDisplayProvider().render(player, mapId, clip);
          }
        }
      }
    }
  }

  public int[][] create(@Nonnull Player player) {
    int[][] ids = new int[sizeY][sizeX];
    for (int y = 0; y < sizeY; y++) {
      for (int x = 0; x < sizeX; x++) {
        ids[y][x] = DisplayCraft.getInstance().getDisplayProvider().create(player, position[y][x].getFirst(), position[y][x].getSecond());
      }
    }
    return ids;
  }

  public void destroy(@Nonnull Player player, int[][] ids) {
    for (var array : ids) {
      for (var id : array) {
        DisplayCraft.getInstance().getDisplayProvider().destroy(player, id);
      }
    }
  }

  @Nonnull
  public Image newImage() {
    return Images.newImage(width, height);
  }

  @Nonnull
  public Dimensions getFullDimensions() {
    return Dimensions.from(0, 0, width, height);
  }

  public int getResolution() {
    return resolution;
  }

  public int getSizeX() {
    return sizeX;
  }

  public int getSizeY() {
    return sizeY;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  // returning writeable array is unsafe api
  @Nonnull
  public Tuple<Position, Integer>[][] getPosition() {
    return position;
  }

  @Nonnull
  public Direction getAxis() {
    return axis;
  }

  public int getCoordinate() {
    return coordinate;
  }

  @FunctionalInterface
  public interface RenderFilter {
    boolean shouldRender(@Nonnull Image image, int x, int y);
  }
}
