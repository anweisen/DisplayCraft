package net.anweisen.displaycraft.desktop;

import net.anweisen.displaycraft.DisplayCraft;
import net.anweisen.displaycraft.api.Direction;
import net.anweisen.displaycraft.api.Position;
import net.anweisen.displaycraft.api.image.Images;
import net.anweisen.displaycraft.api.multipart.MultipartScreen;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import net.anweisen.displaycraft.desktop.computer.DesktopInteractionCursor;
import net.anweisen.displaycraft.desktop.computer.overlay.app.windowed.DesktopAppWindowed;
import net.anweisen.displaycraft.desktop.computer.overlay.app.windowed.apps.PaintApp;
import net.anweisen.displaycraft.desktop.listener.PlayerInteractionListener;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.concurrent.TimeUnit;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DisplayCraftDesktop extends JavaPlugin {

  private DesktopComputer computer;

  @Override
  public void onLoad() {
  }

  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new PlayerInteractionListener(), this);

    computer = new DesktopComputer(
      MultipartScreen.withCenter(Images.RESOLUTION, 7, 3, Position.from(1, 65, 0, Direction.WEST)),
      new DesktopInteractionCursor(10, 1000 / 40)
    );
    computer.startTasks();
    computer.getOverlayHandler().registerApps(new DesktopAppWindowed(new PaintApp()));

    DisplayCraft.getInstance().getExecutorService().scheduleAtFixedRate(() -> {
      long start = System.nanoTime();
      computer.render();
//      System.out.println((System.nanoTime() - start) / 1_000_000f + "");
    }, 100, 1000 / 20, TimeUnit.MILLISECONDS);

    DisplayCraft.getInstance().getCommand("display-craft").setExecutor((sender, command, label, args) -> {
      Player player = (Player) sender;
      player.setAllowFlight(true);
      computer.createScreen(player);
      return true;
    });
  }

  @Override
  public void onDisable() {
    computer.destroy();
  }

}
