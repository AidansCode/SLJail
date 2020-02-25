package com.aidanmurphey.spacelampjail;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Date;
import java.util.UUID;

public class Inmate {

	private UUID uuid;
	private Location location;
	private long inTime, duration;
	private int foodLevel;
	private float saturation;
	private double health;

	private BukkitTask task;

	public Inmate(Player p, long duration) {
		this.uuid = p.getUniqueId();
		this.location = p.getLocation();
		this.inTime = (new Date()).getTime() / 1000; //current unix timestamp in seconds
		this.foodLevel = p.getFoodLevel();
		this.saturation = p.getSaturation();
		this.health = p.getHealth();
		this.duration = duration;

		this.task = new BukkitRunnable() {
			@Override
			public void run() {
				Player p = SpaceLampJail.getPlugin().getServer().getOfflinePlayer(Inmate.this.uuid).getPlayer();
				if (p != null)
					Jail.getInstance().unjailPlayer(p);
			}
		}.runTaskLater(SpaceLampJail.getPlugin(), duration * 20);

	}

	public UUID getUuid() {
		return uuid;
	}

	public Location getLocation() {
		return location;
	}

	public long getInTime() {
		return inTime;
	}

	public long getDuration() {
		return duration;
	}

	public int getFoodLevel() {
		return foodLevel;
	}

	public float getSaturation() {
		return saturation;
	}

	public double getHealth() {
		return health;
	}

	public OfflinePlayer getOfflinePlayer() {
		return SpaceLampJail.getPlugin().getServer().getOfflinePlayer(uuid);
	}

	public boolean isPlayerOnline() {
		OfflinePlayer player = getOfflinePlayer();

		return player.isOnline();
	}

	public boolean heal() {
		if (!isPlayerOnline()) return false;

		Player p = getOfflinePlayer().getPlayer();
		p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());
		p.setFoodLevel(20);
		p.setSaturation(20);

		return true;
	}

	public boolean release() {
		task.cancel();
		if (!isPlayerOnline()) return false;

		Player p = getOfflinePlayer().getPlayer();
		p.setFoodLevel(foodLevel);
		p.setSaturation(saturation);
		p.setHealth(health);
		p.teleport(location);

		Utilities.sendUnjailedMessage(p);

		return true;
	}

}
