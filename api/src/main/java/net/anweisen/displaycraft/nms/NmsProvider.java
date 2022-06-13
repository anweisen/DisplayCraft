package net.anweisen.displaycraft.nms;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @see Reflect#newNmsProvider()
 * @see net.anweisen.displaycraft.api.DisplayProvider
 * @since 1.0
 */
public interface NmsProvider {

  Object getNmsPlayer(@Nonnull Player bukkitPlayer);

  Object getPlayerWorld(@Nonnull Object nmsPlayer);

  Object getPlayerConnection(@Nonnull Object nmsPlayer);

  Integer getEntityId(@Nonnull Object nmsEntity);

  Object getDataWatcher(@Nonnull Object nmsEntity);

  Object getItemFrameEntity(@Nonnull Object playerWorld, int posX, int posY, int posZ, @Nonnull String facing);

  Object getEntityDestroyPacket(int entityId);

  Object getEntitySpawnPacket(@Nonnull Object nmsItemFrameEntity);

  Object getEntityMetadataPacket(int entityId, @Nonnull Object dataWatcher);

  Object getMapUpdatePacket(int mapId, int offsetX, int offsetY, int width, int height, @Nonnull byte[] data);

  Object getNmsItem(@Nonnull ItemStack item);

  void setItemFrameItem(@Nonnull Object nmsEntity, @Nonnull Object nmsItem);

  void sendPacket(@Nonnull Object playerConnection, @Nonnull Object packet);

}
