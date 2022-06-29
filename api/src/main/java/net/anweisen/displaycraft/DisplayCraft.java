package net.anweisen.displaycraft;

import net.anweisen.displaycraft.api.DisplayProvider;
import net.anweisen.displaycraft.api.ScreenTracer;
import net.anweisen.displaycraft.api.implementation.DisplayProviderImpl;
import net.anweisen.displaycraft.api.implementation.ScreenTracerImpl;
import net.anweisen.displaycraft.nms.NmsProvider;
import net.anweisen.displaycraft.nms.Reflect;
import org.bukkit.plugin.java.JavaPlugin;
import javax.annotation.Nonnull;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DisplayCraft extends JavaPlugin {

  private static DisplayCraft instance;
  private ScheduledExecutorService executorService;
  private NmsProvider nmsProvider;
  private DisplayProvider displayProvider;
  private ScreenTracer screenTracer;

  @Nonnull
  public static DisplayCraft getInstance() {
    return instance;
  }

  @Override
  public void onLoad() {
    instance = this;

    getLogger().info("Detected server version " + Reflect.VERSION + " at " + Reflect.CRAFTBUKKIT_PREFIX + "/" + Reflect.MINECRAFT_PREFIX);

    executorService = Executors.newScheduledThreadPool(3);
    nmsProvider     = Reflect.newNmsProvider();
    displayProvider = new DisplayProviderImpl(nmsProvider);
    screenTracer    = new ScreenTracerImpl();

    getLogger().info("Provided scheduled thread pool executor with 3 threads");
    getLogger().info("Using nms provider " + nmsProvider.getClass().getName() + "..");
    getLogger().info("Finished loading!");
  }

  @Override
  public void onEnable() {
  }

  @Override
  public void onDisable() {
    executorService.shutdown();
  }

  @Nonnull
  public ScheduledExecutorService getExecutorService() {
    return executorService;
  }

  @Nonnull
  public NmsProvider getNmsProvider() {
    return nmsProvider;
  }

  @Nonnull
  public DisplayProvider getDisplayProvider() {
    return displayProvider;
  }

  @Nonnull
  public ScreenTracer getScreenTracer() {
    return screenTracer;
  }

}
