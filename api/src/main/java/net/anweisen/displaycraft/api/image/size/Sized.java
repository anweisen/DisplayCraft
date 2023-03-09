package net.anweisen.displaycraft.api.image.size;

import javax.annotation.Nonnegative;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface Sized {

  @Nonnegative
  int getWidth();

  @Nonnegative
  int getHeight();

  record Simple(int x, int y) implements Sized {

    @Override
    public int getWidth() {
      return x;
    }

    @Override
    public int getHeight() {
      return y;
    }
  }

}
