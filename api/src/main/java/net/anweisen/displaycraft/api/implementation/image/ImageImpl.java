package net.anweisen.displaycraft.api.implementation.image;

import net.anweisen.displaycraft.api.image.ColorPalette;
import net.anweisen.displaycraft.api.image.Dimensions;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.Images;
import org.bukkit.map.MapPalette;
import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ImageImpl implements Image {

  private final int width, height;
  private final byte[] content;

  private byte color;

  public ImageImpl(int width, int height) {
    Images.checkSizes(width, height);

    this.width = width;
    this.height = height;
    this.content = new byte[width * height];
  }

  public ImageImpl(int width, int height, byte[] content) {
    Images.checkSizes(width, height);
    Images.checkResolution(width, height, content.length);

    this.width = width;
    this.height = height;
    this.content = content;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public byte getCurrentColor() {
    return color;
  }

  @Override
  public void setCurrentColor(byte color) {
    this.color = color;
  }

  @Override
  public void drawPixel(int x, int y) {
    content[y * width + x] = color;
  }

  @Override
  public void fillRect(int x, int y, int width, int height) {
    Images.checkBounds(x, width, this.width);
    Images.checkBounds(y, height, this.height);

    int dX = x + width;
    int dY = y + height;
    for (int i = y; i < dY; i++) {
      for (int j = x; j < dX; j++) {
        content[i * this.width + j] = color;
      }
    }
  }

  @Override
  public void fillRect(@Nonnull Dimensions dimensions) {
    fillRect(dimensions.getX(), dimensions.getY(), dimensions.getWidth(), dimensions.getHeight());
  }

  @Override
  public void drawStroke(int startX, int startY, int destinationX, int destinationY, int size) {
    int diffX = destinationX - startX;
    int diffY = destinationY - startY;

    int runs = Math.max(Math.abs(diffX), 1);
    float fraction = (float) diffY / (float) runs;
    int height = (int) Math.max(Math.ceil(Math.abs(fraction)), 1);
    for (int i = 0; i < runs; i++) {
      int x = diffX < 0 ? -i : i;
      int y = (int) (i * fraction);

      fillRect(startX + x, fraction < 0 ? startY + y - height : startY + y, size, height + size);
    }
  }

  @Override
  public void drawImage(int x, int y, @Nonnull Image image) {
    drawImagePart(x, y, Dimensions.fromFull(image), image);
  }

  @Override
  public void drawImage(int destinationX, int destinationY, int sourceX, int sourceY, int width, int height, @Nonnull byte[] image, boolean overwriteAsTransparent) {
    Images.checkResolution(width, height, image.length);
    Images.checkBounds(destinationX, width, this.width);
    Images.checkBounds(destinationY, height, this.height);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        byte pixel = image[sourceY + i * width + sourceX + j];
        if (!overwriteAsTransparent && pixel == ColorPalette.TRANSPARENT) continue;
        content[(i + destinationY) * this.width + destinationX + j] = pixel;
      }
    }
  }

  @Override
  public void drawImagePart(int destinationX, int destinationY, int sourceX, int sourceY, int width, int height, @Nonnull Image image, boolean overwriteAsTransparent) {
    Images.checkBounds(sourceX, width, image.getWidth());
    Images.checkBounds(sourceY, height, image.getHeight());
    Images.checkBounds(destinationX, width, this.width);
    Images.checkBounds(destinationY, height, this.height);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        byte pixel = image.getPixel(sourceX + j, sourceY + i);
        if (!overwriteAsTransparent && pixel == ColorPalette.TRANSPARENT) continue;
        content[(i + destinationY) * this.width + destinationX + j] = pixel;
      }
    }
  }

  @Override
  public void drawImagePart(int destinationX, int destinationY, @Nonnull Dimensions dimensions, @Nonnull Image image) {
    drawImagePart(destinationX, destinationY, dimensions.getX(), dimensions.getY(), dimensions.getWidth(), dimensions.getHeight(), image, false);
  }

  @Override
  public void drawString(int x, int y, @Nonnull Font font, @Nonnull String text) {
    FontRenderContext fontRenderContext = new FontRenderContext(AffineTransform.getScaleInstance(1, 1), true, true);
    Rectangle2D bounds = font.getStringBounds(text, fontRenderContext); // faster than using new TextLayout()
    BufferedImage temp = new BufferedImage((int) bounds.getWidth(), (int) bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = temp.createGraphics();
    graphics.setFont(font);
    graphics.setColor(Color.white);
    graphics.drawString(text, 0, (int) bounds.getHeight());
    graphics.dispose();

    int[] pixels = new int[temp.getWidth() * temp.getHeight()];
    temp.getRGB(0, 0, temp.getWidth(), temp.getHeight(), pixels, 0, temp.getWidth());

    // TODO write directly to buffer
    byte[] result = new byte[temp.getWidth() * temp.getHeight()];
    for (int i = 0; i < pixels.length; i++) {
      if (pixels[i] != 0)
        result[i] = color;
    }

    this.drawImage(x, y, 0, 0, temp.getWidth(), temp.getHeight(), result, false);
  }

  @Nonnull
  @Override
  public Image clipImage(int x, int y, int width, int height) {
    return Images.clipImage(x, y, width, height, this.width, content);
  }

  @Nonnull
  @Override
  public Image clipImage(@Nonnull Dimensions dimensions) {
    return clipImage(dimensions.getX(), dimensions.getY(), dimensions.getWidth(), dimensions.getHeight());
  }

  @Nonnull
  @Override
  public Image copy() {
    return Images.copyImage(this);
  }

  @Override
  protected Image clone() {
    return this.copy();
  }

  @Override
  public boolean contains(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  @Override
  public byte getPixel(int x, int y) {
    Images.checkPosition(x, y, width, height);
    return content[y * width + x];
  }

  @Nonnull
  @Override
  public byte[] getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "Image[" + width + "x" + height + "]";
  }
}
