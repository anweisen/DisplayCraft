package net.anweisen.displaycraft.api.multipart;

import net.anweisen.displaycraft.api.image.Image;
import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * @author anweisen | https://github.com/anweisen
 * @see MultipartScreen#render(Collection, Image, RenderFilter)
 * @see #shouldRender(Image, Image, int, int)
 * @since 1.0
 */
@FunctionalInterface
public interface RenderFilter {

  RenderFilter ALL = (fullImage, clip, x, y) -> true;

  /**
   * @param fullImage the full image on the whole screen
   * @param clip      the content of the current part/tile
   * @param x         the x position of the tile inside the screen
   * @param y         the y position of the tile inside the screen
   * @return whether the current clip/part/tile should be rendered
   * @see MultipartScreen#render(Collection, Image, RenderFilter)
   */
  boolean shouldRender(@Nonnull Image fullImage, @Nonnull Image clip, int x, int y);

}
