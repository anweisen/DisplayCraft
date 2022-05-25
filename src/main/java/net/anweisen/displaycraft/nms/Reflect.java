package net.anweisen.displaycraft.nms;

import net.anweisen.utility.common.misc.ReflectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class Reflect {

  public static final String CRAFTBUKKIT_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
  public static final String MINECRAFT_PREFIX = ReflectionUtils.invokeMethodOrNull(Bukkit.getServer(), "getServer").getClass().getPackage().getName();
  public static final String NMS_PREFIX = CRAFTBUKKIT_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
  public static final String VERSION = CRAFTBUKKIT_PREFIX.split("\\.")[3];

  private Reflect() {
  }

  @Nonnull
  public static NmsProvider newNmsProvider() {
    try {
      Class<?> providerClass = Class.forName("net.anweisen.displaycraft.nms.implementation." + VERSION.replace("v", "Nms"));
      Constructor<?> constructor = providerClass.getDeclaredConstructor();
      return (NmsProvider) constructor.newInstance();
    } catch (Exception ex) {
      throw new IllegalStateException("Cannot create nms provider for version '" + VERSION + "'", ex);
    }
  }

  @Nonnull
  @SuppressWarnings({"deprecation", "ConstantConditions"})
  public static ItemStack newMapItem(int mapId) {
    ItemStack item = new ItemStack(Material.FILLED_MAP);
    MapMeta meta = (MapMeta) item.getItemMeta();
    meta.setMapId(mapId);
    item.setItemMeta(meta);
    return item;
  }

  public static int newMapId() {
    return Math.abs(ThreadLocalRandom.current().nextInt());
  }

}
