package net.anweisen.displaycraft.desktop.computer.overlay.app.windowed.apps;

import net.anweisen.displaycraft.api.Cursor;
import net.anweisen.displaycraft.api.image.Image;
import net.anweisen.displaycraft.api.image.ui.Sprite;
import net.anweisen.displaycraft.desktop.computer.cursor.DefaultCursors;
import net.anweisen.displaycraft.desktop.computer.overlay.app.windowed.AppWindowHandler;
import org.bukkit.entity.Player;
import org.bukkit.map.MapPalette;
import javax.annotation.Nonnull;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class TestApp implements AppWindowHandler {

  private final Font font = new Font("Arial", Font.PLAIN, 12);

  @Override
  public void render(@Nonnull Image image) {

    long t;
    t = System.nanoTime();

    FontRenderContext fontRenderContext = new FontRenderContext(AffineTransform.getScaleInstance(1, 1), true, true);
    Rectangle2D bounds = font.getStringBounds("kein jude. asd!123", fontRenderContext); // faster than using new TextLayout
//    Rectangle2D bounds = new TextLayout("kein jude. asd!123", font, fontRenderContext).getBounds();
    BufferedImage temp = new BufferedImage((int) bounds.getWidth(), (int) bounds.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics = temp.createGraphics();
    graphics.setFont(font);
    graphics.setColor(Color.white);
    graphics.drawString("kein fornite jj g I. asd!123", 0, (int) bounds.getHeight());
    graphics.dispose();
    System.err.println("1  " + (double) (System.nanoTime() - t) / 1_000_00d);
    t = System.nanoTime();

    image.setCurrentColor(MapPalette.LIGHT_GREEN);
    int[] pixels = new int[temp.getWidth() * temp.getHeight()];
    temp.getRGB(0, 0, temp.getWidth(), temp.getHeight(), pixels, 0, temp.getWidth());
    byte[] result = new byte[temp.getWidth() * temp.getHeight()];
    for (int i = 0; i < pixels.length; i++) {
      if (pixels[i] != 0)
        result[i] = image.getCurrentColor();
    }

    image.drawImage(100, 100, 0, 0, temp.getWidth(), temp.getHeight(), result, false);
    System.err.println("2  " + (double) (System.nanoTime() - t) / 1_000_00d);
    if (true) return;

//    TextLayout textLayout = new TextLayout("abc!123", font, fontRenderContext);

    char[] text = "abc!123".toCharArray();
//    GlyphVector vector = font.layoutGlyphVector(fontRenderContext, text, 0, text.length, 0);
    GlyphVector vector = font.createGlyphVector(fontRenderContext, "Hallo");

    for (int i = 0; i < vector.getNumGlyphs(); i++) {
//      Shape glyph = vector.getGlyphOutline(i);
      Shape glyph = vector.getOutline(100, 100);
      GeneralPath path = (GeneralPath) glyph;
      System.out.println();

      image.setCurrentColor(MapPalette.BLUE);

//      Rectangle2D bounds = glyph.getBounds2D();
      t = System.nanoTime();
      PathIterator iterator = new FlatteningPathIterator(path.getPathIterator(null), 16);
      handle(iterator, new PathWorker() {
        transient float x, y;

        @Override
        public void move(float x, float y) {
          image.setCurrentColor(MapPalette.RED);
          image.drawPixel((int) x, (int) y);
          this.x = x;
          this.y = y;
        }

        @Override
        public void connect(float x, float y) {
          image.setCurrentColor(MapPalette.BLUE);
          image.drawStroke((int) this.x, (int) this.y, (int) x, (int) y, 1);
          image.setCurrentColor(MapPalette.LIGHT_GREEN);
          image.drawPixel((int) x, (int) y);
          this.x = x;
          this.y = y;
        }

        @Override
        public void closePath() {
          this.x = 0;
          this.y = 0;
        }
      });
      System.err.println("2  " + (double) (System.nanoTime() - t) / 1_000_00d);
      System.out.println();
    }
  }

  public static void handle(PathIterator iterator, PathWorker consumer) {
    while (!iterator.isDone()) {
      float[] coords = new float[6];
      switch (iterator.currentSegment(coords)) {
        case PathIterator.SEG_MOVETO:
          consumer.move(coords[0], coords[1]);
          break;
        case PathIterator.SEG_LINETO:
          consumer.connect(coords[0], coords[1]);
          break;
        case PathIterator.SEG_CLOSE:
          consumer.closePath();
          break;
      }
      iterator.next();
    }
  }

  interface PathWorker {
    void move(float x, float y);
    void connect(float x, float y);
    void closePath();
  }

  @Override
  public void handleClick(@Nonnull Player player, @Nonnull Cursor cursor, boolean right) {
  }

  @Override
  public void handleMove(@Nonnull Player player, @Nonnull Cursor from, @Nonnull Cursor to) {
  }

  @Nonnull
  @Override
  public Sprite getCursorSprite(@Nonnull Player player, @Nonnull Cursor cursor) {
    return DefaultCursors.getCrosshair();
  }
}
