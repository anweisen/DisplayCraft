package net.anweisen.displaycraft.desktop.computer.render;

import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DesktopRenderer {

  void render(@Nonnull DesktopComputer computer, @Nonnull Image image);

}
