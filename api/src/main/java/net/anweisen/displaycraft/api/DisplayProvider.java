package net.anweisen.displaycraft.api;

import net.anweisen.displaycraft.api.image.Image;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DisplayProvider {

  /**
   * Creates a fake map on the player's client in his current world at the given position.
   * The fake map will carry the given mapId or use the returned entityId if -1 is given.
   *
   * @param player   the target player
   * @param position the position of the new nap in the player's current world
   * @param mapId    the mapId the new fake map should carry, or -1 to use the returned entityId
   * @return the entityId of the created fake map
   */
  int create(@Nonnull Player player, @Nonnull Position position, int mapId);

  /**
   * Updates the target fake map held on the player's client with the given data.
   *
   * @param player  the target player
   * @param mapId   the mapId of the target map on the player's client
   * @param offsetX the x offset for the map data
   * @param offsetY the y offset for the map data
   * @param width   the width of the map data
   * @param height  the height of the map data
   * @param content the content of the map data
   * @see org.bukkit.map.MapPalette
   */
  void render(@Nonnull Player player, int mapId, int offsetX, int offsetY, int width, int height, @Nonnull byte[] content);

  /**
   * Updates the target fake map held on the player's client with the given data.
   *
   * @param player the target player
   * @param mapId  the mapId of the target map on the player's client
   * @param image  the content of the map data
   */
  void render(@Nonnull Player player, int mapId, @Nonnull Image image);

  /**
   * Destroys the fake map on the player's client with the given mapId.
   *
   * @param player   the target player
   * @param entityId the entityId of the fake map
   */
  void destroy(@Nonnull Player player, int entityId);

}
