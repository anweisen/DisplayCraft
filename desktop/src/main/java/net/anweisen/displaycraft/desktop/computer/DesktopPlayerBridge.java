package net.anweisen.displaycraft.desktop.computer;

import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DesktopPlayerBridge {

  private final DesktopComputer computer;
  private final Map<Player, PlayerState> players = new ConcurrentHashMap<>();

  public DesktopPlayerBridge(@Nonnull DesktopComputer computer) {
    this.computer = computer;
  }

  public boolean hasPlayer(@Nonnull Player player) {
    return players.containsKey(player);
  }

  @Nullable
  public PlayerState getState(@Nonnull Player player) {
    return players.get(player);
  }

  @Nonnull
  public Collection<Player> getPlayers() {
    return players.keySet();
  }

  @Nonnull
  public Set<Map.Entry<Player, PlayerState>> getPlayerStates() {
    return players.entrySet();
  }

  public void updateState(@Nonnull Player player, @Nonnull PlayerState state) {
    players.put(player, state);
  }

  public void removeState(@Nonnull Player player) {
    players.remove(player);
  }

  public void createScreen(@Nonnull Player player) {
    int[][] ids = computer.getScreen().create(player);
    updateState(player, new PlayerState(ids));
  }

  public boolean destroyScreen(@Nonnull Player player) {
    PlayerState state = getState(player);
    if (state == null) return false;
    computer.getScreen().destroy(player, state.getMapEntityIds());
    removeState(player);
    return true;
  }

  public static class PlayerState {
    private final int[][] mapEntityIds;

    public PlayerState(@Nonnull int[][] mapEntityIds) {
      this.mapEntityIds = mapEntityIds;
    }

    @Nonnull
    public int[][] getMapEntityIds() {
      return mapEntityIds;
    }
  }
}
