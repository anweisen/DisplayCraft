package net.anweisen.displaycraft;

import net.anweisen.displaycraft.nms.NmsProvider;
import net.anweisen.displaycraft.nms.Reflect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.map.MapPalette;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DisplayCraft extends JavaPlugin implements Listener {

	int ind;
	boolean dir = true;
	private int id;

	@Override
	public void onEnable() {
		getCommand("display-craft").setExecutor(this);
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
		Player player = (Player) sender;
		player.sendMessage("asd");

		NmsProvider provider = Reflect.newNmsProvider();
		Object nmsPlayer = provider.getNmsPlayer(player);
		Object playerConnection = provider.getPlayerConnection(nmsPlayer);

		int mapId = Reflect.newMapId();
		Object nmsEntity = provider.getItemFrameEntity(provider.getPlayerWorld(nmsPlayer), 0, 67, 0, "NORTH");
		provider.setItemFrameItem(nmsEntity, provider.getNmsItem(Reflect.newMapItem(mapId)));
		provider.sendPacket(playerConnection, provider.getEntitySpawnPacket(nmsEntity));
		provider.sendPacket(playerConnection, provider.getEntityMetadataPacket(provider.getEntityId(nmsEntity), provider.getDataWatcher(nmsEntity)));

		DecimalFormat format = new DecimalFormat("0.00000");
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
//			provider.sendPacket(playerConnection, provider.getMapUpdatePacket(mapId, 0, 0, 128, 128, this.img()));

			Location location = player.getEyeLocation();
//			location.setPitch(0);#
			Vector direction = location.getDirection().normalize();
			Location screenBlock = location.getWorld().getBlockAt(0, 67, 0).getLocation();
			Location screenFace = screenBlock.clone().add(.5, .5, 1);

			int maxDistance = 10;
			float fraction = 1f / 128;
			Vector fractionVector = direction.clone().multiply(fraction);
			Location position = location.clone();
			double lastDistance = maxDistance;
			for (float f = 0; f < maxDistance; f += fraction) {
				Location next = position.clone().add(fractionVector);

//				double distance = position.distance(screen);
				double distance = Math.abs(screenFace.getZ() - next.getZ());
				if (distance > lastDistance) {
//					player.spawnParticle(Particle.VILLAGER_HAPPY, position.clone().subtract(0, 0, .1), 1);
					Location relative = position.clone().subtract(screenBlock);
					float relX = (float) relative.getX();
					float relY = (float) relative.getY();
					if (relX > 1 || relY > 1 || relX < 0 || relY < 0) break;

					int absX = 127 - (int) (127f * relX);
					int absY = 127 - (int) (127f * relY);

					player.sendMessage(format.format(distance) + " " + format.format(lastDistance) + "  " + absX + " | " + absY + "   " + format.format(relative.getX()) +" | " + format.format(relative.getY()));
					provider.sendPacket(playerConnection, provider.getMapUpdatePacket(mapId, 0, 0, 128, 128, this.img(absX, absY)));

					break;
				}

				lastDistance = distance;
				position = next;
//				player.sendMessage(format.format(f) + " -> " + format.format(distance) + "   " + format.format(position.getX()) + " " + format.format(position.getY()) + " " + format.format(position.getZ()));
			}

			Vector distancedDirection = direction.clone().multiply(maxDistance);


//			Location destiny = location.clone().add(distancedDirection.getX(), distancedDirection.getY(), distancedDirection.getZ());


			{
//				player.sendMessage(
//					(direction.getX() >= 0 ? "+" : "") + format.format(direction.getX()) + " " +
//						(direction.getY() >= 0 ? "+" : "") + format.format(direction.getY()) + " " +
//						(direction.getZ() >= 0 ? "+" : "") + format.format(direction.getZ())
//				);
//				player.sendMessage(
//					(destiny.getX() >= 0 ? "+" : "") + format.format(destiny.getX()) + " " +
//						(destiny.getY() >= 0 ? "+" : "") + format.format(destiny.getY()) + " " +
//						(destiny.getZ() >= 0 ? "+" : "") + format.format(destiny.getZ())
//				);
//				Bukkit.getScheduler().runTask(this, () -> {
//					Location placerLocation = player.getLocation();
//					placerLocation.setPitch(0);
//					player.teleport(placerLocation);
//				});

			}


//			player.sendMessage("asdaaarayTraceBlocks");
//			RayTraceResult trace = player.getWorld().rayTraceBlocks(player.getEyeLocation(), direction, 10, FluidCollisionMode.NEVER, false);
//			player.sendMessage(String.valueOf(trace));

		}, 1, 1000 / 100, TimeUnit.MILLISECONDS);

		return true;
	}

	@EventHandler
	public void onMove(@Nonnull PlayerMoveEvent event) {
//		event.getPlayer().sendMessage(event.getTo().getDirection().toString());
	}

	private byte[] img(int x, int y) {
//		BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
//		Graphics graphics = image.createGraphics();
//		graphics.setFont(new Font("Arial", Font.BOLD, 50));
//		graphics.drawString(ind + "", 50, 50);
//		graphics.dispose();

//		return MapPalette.imageToBytes(image);

		if (dir) ind++;
		else ind--;
		if (ind > 70 || ind <= 0) dir = !dir;

		byte[] data = new byte[128 * 128];
//		a(data, 16 * 0 + ind, MapPalette.matchColor(0, 0, 0));
//		a(data, 16 * 1 + ind, MapPalette.matchColor(20, 20, 20));
//		a(data, 16 * 2 + ind, MapPalette.matchColor(40, 40, 40));
//		a(data, 16 * 3 + ind, MapPalette.matchColor(60, 60, 60));
//		a(data, 16 * 4 + ind, MapPalette.matchColor(80, 80, 80));
//		a(data, 16 * 5 + ind, MapPalette.matchColor(100, 100, 100));
		Arrays.fill(data, MapPalette.WHITE);
		data[y * 128 + x] = MapPalette.matchColor(0, 0, 0);
		return data;
	}

	private void a(byte[] data, int a, byte color) {
		for (int i = a; i < 128 - a; i++) {
			for (int j = a; j < 128 - a; j++) {
				data[i * 128 + j] = color;
			}
		}
	}
}
