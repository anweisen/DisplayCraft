package net.anweisen.displaycraft.desktop.computer.cursor;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DesktopCursorClickListener {

  void handleClick(@Nonnull DesktopComputer computer, @Nonnull Player player, @Nonnull Cursor cursor, boolean right);

}
