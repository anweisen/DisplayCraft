package net.anweisen.displaycraft.api.implementation;

import net.anweisen.displaycraft.api.DisplayProvider;
import net.anweisen.displaycraft.api.Position;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.nms.NmsProvider;
import net.anweisen.displaycraft.nms.Reflect;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DisplayProviderImpl implements DisplayProvider {

  private final NmsProvider provider;

  public DisplayProviderImpl(@Nonnull NmsProvider provider) {
    this.provider = provider;
  }

  @Override
  public void render(@Nonnull Player player, int mapId, int offsetX, int offsetY, int width, int height, @Nonnull byte[] content) {
    provider.sendPacket(
      provider.getPlayerConnection(provider.getNmsPlayer(player)),
      provider.getMapUpdatePacket(mapId, offsetX, offsetY, width, height, content)
    );
  }

  @Override
  public void render(@Nonnull Player player, int mapId, @Nonnull Image image) {
    render(player, mapId, 0, 0, image.getWidth(), image.getHeight(), image.getContent());
  }

  @Override
  public int create(@Nonnull Player player, @Nonnull Position position, int mapId) {
    Object nmsPlayer = provider.getNmsPlayer(player);
    Object playerConnection = provider.getPlayerConnection(nmsPlayer);

    Object nmsEntity = provider.getItemFrameEntity(provider.getPlayerWorld(nmsPlayer), position.getX(), position.getY(), position.getZ(), position.getDirection().name());
    int entityId = provider.getEntityId(nmsEntity);

    provider.setItemFrameItem(nmsEntity, provider.getNmsItem(Reflect.newMapItem(mapId == -1 ? entityId : mapId)));
    provider.sendPacket(playerConnection, provider.getEntitySpawnPacket(nmsEntity));
    provider.sendPacket(playerConnection, provider.getEntityMetadataPacket(entityId, provider.getDataWatcher(nmsEntity)));

    return entityId;
  }

  @Override
  public void destroy(@Nonnull Player player, int entityId) {
    provider.sendPacket(provider.getPlayerConnection(provider.getNmsPlayer(player)), provider.getEntityDestroyPacket(entityId));
  }
}
