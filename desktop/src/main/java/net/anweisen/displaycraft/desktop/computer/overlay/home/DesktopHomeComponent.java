package net.anweisen.displaycraft.desktop.computer.overlay.home;

import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.Images;
import org.bukkit.entity.Player;
import org.bukkit.map.MapPalette;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface DesktopHomeComponent {

  void render(@Nonnull Image image);

  @Nonnull
  DesktopCursorDisplay getCursorDisplay(@Nonnull Player player, @Nonnull Cursor cursor);

  class Default implements DesktopHomeComponent {

    private static final byte color = MapPalette.matchColor(Color.decode("#282C34"));
    private static final Image background;

    static {
      try {
        BufferedImage read = ImageIO.read(new URL("https://i.redd.it/wwk2t0dgfcm21.png"));
        BufferedImage image = new BufferedImage(Images.RESOLUTION * 7, Images.RESOLUTION * 3, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(read, 0, 0, image.getWidth(), image.getHeight(), null);
        graphics.dispose();

        background = Images.convertImage(image);
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }

    @Override
    public void render(@Nonnull Image image) {
      image.setCurrentColor(color);
      image.drawImage(0, 0, background);
      image.fillRect(0, image.getHeight() - 32, image.getWidth(), 32);
    }
  }

}
