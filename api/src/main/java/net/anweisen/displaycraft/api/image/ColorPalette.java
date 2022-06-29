package net.anweisen.displaycraft.api.image;

import org.bukkit.map.MapPalette;
import javax.annotation.Nonnull;
import java.awt.*;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ColorPalette {

  public static final byte
    TRANSPARENT = 0;

  private ColorPalette() {
  }

  /**
   * @return the distance between the given colors in minecraft terms
   * @author Bukkit
   */
  public static double getDistance(@Nonnull Color color1, @Nonnull Color color2) {
    double rmean = (color1.getRed() + color2.getRed()) / 2.0;
    double r = color1.getRed() - color2.getRed();
    double g = color1.getGreen() - color2.getGreen();
    int b = color1.getBlue() - color2.getBlue();
    double weightR = 2 + rmean / 256.0;
    double weightG = 4.0;
    double weightB = 2 + (255 - rmean) / 256.0;
    return weightR * r * r + weightG * g * g + weightB * b * b;
  }

  @SuppressWarnings("deprecation")
  public static byte matchColor(@Nonnull Color color) {
    return MapPalette.matchColor(color);
  }

  @SuppressWarnings("deprecation")
  public static byte matchColor(int r, int g, int b) {
    return MapPalette.matchColor(r, g, b);
  }

  @Nonnull
  @SuppressWarnings("deprecation")
  public static Color getColor(byte color) {
    return MapPalette.getColor(color);
  }

}
