package net.anweisen.displaycraft.api.image.ui;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.DrawHelper;
import net.anweisen.displaycraft.api.image.Image;
import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class Sprite {

  private final int hotspotX, hotspotY;
  private final Image image;

  public Sprite(int hotspotX, int hotspotY, @Nonnull Image image) {
    this.hotspotX = hotspotX;
    this.hotspotY = hotspotY;
    this.image = image;
  }

  public int getHotspotX() {
    return hotspotX;
  }

  public int getHotspotY() {
    return hotspotY;
  }

  @Nonnull
  public Image getImage() {
    return image;
  }

  public void draw(@Nonnull Image target, @Nonnull Cursor cursor) {
    DrawHelper.drawImageEdge(image, cursor.getAbsoluteX(), cursor.getAbsoluteY(), hotspotX, hotspotY, target);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Sprite sprite = (Sprite) o;
    return hotspotX == sprite.hotspotX && hotspotY == sprite.hotspotY && Objects.equals(image, sprite.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hotspotX, hotspotY, image);
  }

  @Override
  public String toString() {
    return "Sprite[" + image.getWidth() + "x" + image.getHeight() + " @ " + hotspotX + "," + hotspotY + "]";
  }
}
