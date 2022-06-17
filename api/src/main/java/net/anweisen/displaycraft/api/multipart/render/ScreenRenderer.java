package net.anweisen.displaycraft.api.multipart.render;

import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.desktop.computer.DesktopComputer;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface ScreenRenderer {

  void render(@Nonnull Image image);

}
