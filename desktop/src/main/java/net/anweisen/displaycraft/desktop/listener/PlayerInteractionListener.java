package net.anweisen.displaycraft.desktop.listener;

import net.anweisen.displaycraft.DisplayCraft;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class PlayerInteractionListener implements Listener {

  @EventHandler
  public void onPlayerInteract(@Nonnull PlayerInteractEvent event) {
    if (event.getAction() == Action.PHYSICAL) return;

    boolean right = switch (event.getAction()) {
      case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> true;
      default -> false;
    };

    DisplayCraft.getInstance().getExecutorService().execute(() -> {
      for (DesktopComputer computer : DesktopComputer.getInstances()) {
        computer.handleCursorClick(event.getPlayer(), right);
      }
    });
  }

}
