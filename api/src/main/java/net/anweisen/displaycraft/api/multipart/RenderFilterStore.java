package net.anweisen.displaycraft.api.multipart;

import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.utility.common.collection.pair.Tuple;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author anweisen | https://github.com/anweisen
 * @see RenderFilter
 * @since 1.0
 */
public class RenderFilterStore implements RenderFilter {

  protected final Map<Tuple<Integer, Integer>, byte[]> tileTracker = new ConcurrentHashMap<>();
  protected final AtomicReference<Image> lastImage = new AtomicReference<>();

  @Override
  public boolean shouldRender(@Nonnull Image fullImage, @Nonnull Image clip, int x, int y) {
    lastImage.set(fullImage);

    byte[] rendered = tileTracker.get(Tuple.of(x, y));
    if (rendered == null) {
      storeRenderedTile(clip.getContent(), x, y);
      return true;
    }
    boolean equals = Arrays.equals(rendered, clip.getContent());
    if (!equals) storeRenderedTile(clip.getContent(), x, y);
    return !equals;
  }

  public void storeRenderedTile(@Nonnull byte[] image, int x, int y) {
    byte[] content = new byte[image.length];
    System.arraycopy(image, 0, content, 0, content.length);
    tileTracker.put(Tuple.of(x, y), content);
  }

  @Nullable
  public Image getLastImage() {
    return lastImage.get();
  }
}
