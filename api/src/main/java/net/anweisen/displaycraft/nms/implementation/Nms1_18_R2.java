package net.anweisen.displaycraft.nms.implementation;

import net.anweisen.displaycraft.nms.NmsProvider;
import net.anweisen.utility.common.misc.ReflectionUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityMetadata;
import net.minecraft.network.protocol.game.PacketPlayOutMap;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.entity.decoration.EntityItemFrame;
import net.minecraft.world.level.World;
import net.minecraft.world.level.saveddata.maps.WorldMap;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import javax.annotation.Nonnull;
import java.util.Collections;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class Nms1_18_R2 implements NmsProvider {

  @Override
  public Object getNmsPlayer(@Nonnull Player bukkitPlayer) {
    return ReflectionUtils.invokeMethodOrNull(bukkitPlayer, "getHandle");
  }

  @Override
  public Object getPlayerWorld(@Nonnull Object nmsPlayer) {
    return ReflectionUtils.getFieldValueOrNull(nmsPlayer, "s");
  }

  @Override
  public Object getPlayerConnection(@Nonnull Object nmsPlayer) {
    return ReflectionUtils.getFieldValueOrNull(nmsPlayer, "b");
  }

  @Override
  public Integer getEntityId(@Nonnull Object nmsEntity) {
    return ReflectionUtils.invokeMethodOrNull(nmsEntity, "ae");
  }

  @Override
  public Object getDataWatcher(@Nonnull Object nmsEntity) {
    return ReflectionUtils.invokeMethodOrNull(nmsEntity, "ai");
  }

  @Override
  public Object getItemFrameEntity(@Nonnull Object playerWorld, int posX, int posY, int posZ, @Nonnull String facing) {
    return new EntityItemFrame((World) playerWorld, new BlockPosition(posX, posY, posZ), EnumDirection.valueOf(facing));
  }

  @Override
  public Object getEntityDestroyPacket(int entityId) {
    return new PacketPlayOutEntityDestroy(entityId);
  }

  @Override
  public Object getEntitySpawnPacket(@Nonnull Object nmsItemFrameEntity) {
    return ReflectionUtils.invokeMethodOrNull(nmsItemFrameEntity, "S");
  }

  @Override
  public Object getEntityMetadataPacket(int entityId, @Nonnull Object dataWatcher) {
    return new PacketPlayOutEntityMetadata(entityId, (DataWatcher) dataWatcher, false);
  }

  @Override
  public Object getMapUpdatePacket(int mapId, int offsetX, int offsetY, int width, int height, @Nonnull byte[] data) {
    return new PacketPlayOutMap(mapId, (byte) 3, false, Collections.emptyList(), new WorldMap.b(offsetX, offsetY, width, height, data));
  }

  @Override
  public Object getNmsItem(@Nonnull ItemStack item) {
    return CraftItemStack.asNMSCopy(item);
  }

  @Override
  public void setItemFrameItem(@Nonnull Object nmsEntity, @Nonnull Object nmsItem) {
    ((EntityItemFrame) nmsEntity).setItem((net.minecraft.world.item.ItemStack) nmsItem, true, false);
  }

  @Override
  public void sendPacket(@Nonnull Object playerConnection, @Nonnull Object packet) {
    ((PlayerConnection) playerConnection).a((Packet<?>) packet);
  }
}
