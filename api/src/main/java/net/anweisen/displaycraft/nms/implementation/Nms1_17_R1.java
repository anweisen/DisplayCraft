package net.anweisen.displaycraft.nms.implementation;

import net.anweisen.displaycraft.nms.NmsProvider;
import net.anweisen.utility.common.misc.ReflectionUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class Nms1_17_R1 implements NmsProvider {

  @Override
  public Object getNmsPlayer(@Nonnull Player bukkitPlayer) {
    return ReflectionUtils.invokeMethodOrNull(bukkitPlayer, "getHandle");
  }

  @Override
  public Object getPlayerWorld(@Nonnull Object nmsPlayer) {
    return ReflectionUtils.invokeMethodOrNull(nmsPlayer, "getWorld");
  }

  @Override
  public Object getPlayerConnection(@Nonnull Object nmsPlayer) {
    return ReflectionUtils.getFieldValueOrNull(nmsPlayer, "b");
  }

  @Override
  public Integer getEntityId(@Nonnull Object nmsEntity) {
    return ReflectionUtils.invokeMethodOrNull(nmsEntity, "getId");
  }

  @Override
  public Object getDataWatcher(@Nonnull Object nmsEntity) {
    return null;
  }

  @Override
  public Object getItemFrameEntity(@Nonnull Object playerWorld, int posX, int posY, int posZ, @Nonnull String facing) {
    return null;
  }

  @Override
  public Object getEntityDestroyPacket(int entityId) {
    return null;
  }

  @Override
  public Object getEntitySpawnPacket(@Nonnull Object nmsItemFrameEntity) {
    return null;
  }

  @Override
  public Object getEntityMetadataPacket(int entityId, @Nonnull Object dataWatcher) {
    return null;
  }

  @Override
  public Object getMapUpdatePacket(int mapId, int offsetX, int offsetY, int width, int height, @Nonnull byte[] data) {
    return null;
  }

  @Override
  public Object getNmsItem(@Nonnull ItemStack item) {
    return null;
  }

  @Override
  public void setItemFrameItem(@Nonnull Object nmsEntity, @Nonnull Object nmsItem) {
  }

  @Override
  public void sendPacket(@Nonnull Object playerConnection, @Nonnull Object packet) {
  }
}
