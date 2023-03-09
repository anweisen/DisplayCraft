package net.anweisen.displaycraft.api.implementation.image;

import net.anweisen.displaycraft.api.image.ColorPalette;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.Images;
import net.anweisen.displaycraft.api.image.size.Dimensions;
import net.anweisen.displaycraft.api.image.size.DrawOrigin;
import net.anweisen.displaycraft.api.image.size.Scaling;
import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ImageImpl implements Image {

  private final int width, height;
  private final byte[] content;

  private byte currentColor;

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
    return currentColor;
  }

  @Override
  public void setCurrentColor(byte color) {
    this.currentColor = color;
  }

  @Override
  public void drawPixel(int x, int y) {
    Images.checkPosition(x, y, width, height);
    content[y * width + x] = currentColor;
  }

  @Override
  public void fillRect(int x, int y, int width, int height) {
    Images.checkBounds(x, width, this.width);
    Images.checkBounds(y, height, this.height);

    int dX = x + width;
    int dY = y + height;
    for (int i = y; i < dY; i++) {
      for (int j = x; j < dX; j++) {
        content[i * this.width + j] = currentColor;
      }
    }
  }

  @Override
  public void fillRect(@Nonnull Dimensions dimensions) {
    fillRect(dimensions.getX(), dimensions.getY(), dimensions.getWidth(), dimensions.getHeight());
  }

  @Override
  public void fillRect(@Nonnull DrawOrigin origin, @Nonnull Scaling x, @Nonnull Scaling y, @Nonnull Scaling width, @Nonnull Scaling height) {
    fillRect(
      origin.offsetX(x.resolve(this), width.resolve(this)),
      origin.offsetY(y.resolve(this), height.resolve(this)),
      width.resolve(this),
      height.resolve(this)
    );
  }

  @Override
  public void drawStroke(int startX, int startY, int destinationX, int destinationY, int size) {
    int diffX = destinationX - startX;
    int diffY = destinationY - startY;

    int runs = Math.max(Math.abs(diffX), 1);
    float fraction = (float) diffY / (float) runs;
    int height = (int) Math.max(Math.ceil(Math.abs(fraction)), 1);
    for (int i = 0; i < runs; i++) {
      int x = diffX < 0 ? -i : i; // TODO Math.abs?
      int y = (int) (i * fraction);

      fillRect(startX + x, fraction < 0 ? startY + y - height : startY + y, size, height + size);
    }
  }

  @Override
  public void drawStroke(@Nonnull Scaling startX, @Nonnull Scaling startY, @Nonnull Scaling destinationX, @Nonnull Scaling destinationY, @Nonnull Scaling size) {
    this.drawStroke(startX.resolve(this), startY.resolve(this), destinationX.resolve(this), destinationY.resolve(this), size.resolve(this));
  }

  @Override
  public void drawCircle(int x, int y, int radius) {
    drawCircle(x, y, radius, 1);
  }

  @Override
  public void drawCircle(int x, int y, int radius, int thickness) {
    makeCircle(x, y, radius, false, thickness);
  }

  @Override
  public void drawCircle(@Nonnull DrawOrigin origin, @Nonnull Scaling x, @Nonnull Scaling y, @Nonnull Scaling radius, @Nonnull Scaling thickness) {
    this.drawCircle(
      origin.offsetX(x.resolve(this), radius.resolve(this)),
      origin.offsetY(y.resolve(this), radius.resolve(this)),
      radius.resolve(this), thickness.resolve(this)
    );
  }

  @Override
  public void fillCircle(int x, int y, int radius) {
    makeCircle(x, y, radius, true, 1);
  }

  @Override
  public void fillCircle(@Nonnull DrawOrigin origin, @Nonnull Scaling x, @Nonnull Scaling y, @Nonnull Scaling radius) {
    this.fillCircle(
      origin.offsetX(x.resolve(this), radius.resolve(this)),
      origin.offsetY(y.resolve(this), radius.resolve(this)),
      radius.resolve(this)
    );
  }

  public void makeCircle(int x, int y, int radius, boolean fill, int outlineThickness) {
    Images.checkBounds(x, radius + outlineThickness / 2, width);
    Images.checkBounds(y, radius + outlineThickness / 2, height);

    int iterations = Math.max(radius, 90); // at least 90 to draw full circle with no holes
    for (int i = -iterations; i <= iterations; i++) {
      double quadrant = (double) (iterations - i) / iterations; // 180°=2.0 90°=1.0 0°=0.0
      double angle = quadrant / 2d * Math.PI; // (radians): pi/2 = 90°
      double sin = Math.sin(angle) * radius;
      double cos = Math.cos(angle) * radius;

      if (fill) {
        fillRect(x + (int) Math.round(cos), y - (int) Math.round(sin), 1, (int) Math.round(sin) * 2);
      } else {
        fillRect(x + (int) Math.round(cos), y - (int) Math.round(sin), outlineThickness, outlineThickness);
        fillRect(x + (int) Math.round(cos), y + (int) Math.round(sin), outlineThickness, outlineThickness);
      }
    }
  }

  @Override
  public void drawRoundRect(int x, int y, int width, int height, int arcRadius) {
    drawRoundRect(x, y, width, height, arcRadius, 1);
  }

  @Override
  public void drawRoundRect(int x, int y, int width, int height, int arcRadius, int thickness) {
    makeRoundRect(x, y, width, height, arcRadius, false, thickness);
  }

  @Override
  public void drawRoundRect(@Nonnull DrawOrigin origin, @Nonnull Scaling x, @Nonnull Scaling y, @Nonnull Scaling width, @Nonnull Scaling height, @Nonnull Scaling arcRadius, @Nonnull Scaling thickness) {
    this.drawRoundRect(
      origin.offsetX(x.resolve(this), width.resolve(this)),
      origin.offsetY(y.resolve(this), height.resolve(this)),
      width.resolve(this),
      height.resolve(this),
      arcRadius.resolve(this),
      thickness.resolve(this));
  }

  @Override
  public void fillRoundRect(int x, int y, int width, int height, int arcRadius) {
    makeRoundRect(x, y, width, height, arcRadius, true, 1);
  }

  @Override
  public void fillRoundRect(@Nonnull DrawOrigin origin, @Nonnull Scaling x, @Nonnull Scaling y, @Nonnull Scaling width, @Nonnull Scaling height, @Nonnull Scaling arcRadius) {
    this.fillRoundRect(
      origin.offsetX(x.resolve(this), width.resolve(this)),
      origin.offsetY(y.resolve(this), height.resolve(this)),
      width.resolve(this),
      height.resolve(this),
      arcRadius.resolve(this)
    );
  }

  public void makeRoundRect(int x, int y, int width, int height, int arcRadius, boolean fill, int outlineThickness) {
    int iterations = Math.max(arcRadius, 90); // at least 90 to draw arc with no holes
    for (int i = 0; i <= iterations; i++) {
      double quadrant = (double) (iterations - i) / iterations; // 90°=1.0 0°=0.0
      double angle = quadrant / 2d * Math.PI; // (radians): pi/2 = 90°
      double cos = Math.cos(angle) * arcRadius;
      double sin = Math.sin(angle) * arcRadius;
      int rsin = (int) Math.min(Math.round(sin), arcRadius);
      int rcos = (int) Math.min(Math.round(cos), arcRadius);
      int by = y + height - arcRadius + (fill ? 0 : rsin);

      fillRect(x + width - arcRadius + rcos, y + arcRadius - rsin, outlineThickness, fill ? rsin : outlineThickness); // q I: top right
      fillRect(x + arcRadius - rcos, y + arcRadius - rsin, outlineThickness, fill ? rsin : outlineThickness); // q II: top left
      fillRect(x + arcRadius - rcos, by, outlineThickness, fill ? rsin : outlineThickness); // q III: bottom left
      fillRect(x + width - arcRadius + rcos, by, outlineThickness, fill ? rsin : outlineThickness); // q IV: bottom right
    }

    if (fill) {
      fillRect(x, y + arcRadius, width + 1, height - arcRadius * 2);
      fillRect(x + arcRadius, y, width - arcRadius * 2, height);
    } else {
      fillRect(x + arcRadius, y, width - arcRadius * 2, outlineThickness);
      fillRect(x + arcRadius, y + height, width - arcRadius * 2, outlineThickness);
      fillRect(x, y + arcRadius, outlineThickness, height - arcRadius * 2);
      fillRect(x + width, y + arcRadius, outlineThickness, height - arcRadius * 2);
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
    drawString(x, y, DrawOrigin.LEFT_TOP, font, text);
  }

  @Override
  public void drawString(@Nonnull Scaling x, @Nonnull Scaling y, @Nonnull DrawOrigin origin, @Nonnull Font font, @Nonnull String text) {
    this.drawString(x.resolve(this), y.resolve(this), origin, font, text);
  }

  @Override
  public void drawString(int destinationX, int destinationY, @Nonnull DrawOrigin origin, @Nonnull Font font, @Nonnull String text) {
    // render text to BufferedImage, get text bounds
    FontRenderContext fontRenderContext = new FontRenderContext(AffineTransform.getScaleInstance(1, 1), true, true);
    Rectangle bounds = font.getStringBounds(text, fontRenderContext).getBounds(); // faster than using new TextLayout()
    BufferedImage temp = new BufferedImage((int) bounds.getWidth(), (int) bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = temp.createGraphics();
    graphics.setFont(font);
    graphics.setColor(Color.white);
    graphics.drawString(text, 0, (int) bounds.getHeight());
    graphics.dispose();

    int[] pixels = new int[temp.getWidth() * temp.getHeight()];
    temp.getRGB(0, 0, temp.getWidth(), temp.getHeight(), pixels, 0, temp.getWidth());

    // transfer text onto image in currentColor
    // https://github.com/anweisen/DisplayCraft/commit/1def8e892fb8c4fc940afb24cd9f72b887ea4164
    byte[] result = new byte[temp.getWidth() * temp.getHeight()];
    for (int i = 0; i < pixels.length; i++) {
      if (pixels[i] != 0)
        result[i] = currentColor;
    }

    this.drawImage(origin.offsetX(destinationX, temp.getWidth()), origin.offsetY(destinationY, temp.getHeight()), 0, 0, temp.getWidth(), temp.getHeight(), result, false);
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
