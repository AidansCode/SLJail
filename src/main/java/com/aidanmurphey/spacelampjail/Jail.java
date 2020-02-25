package com.aidanmurphey.spacelampjail;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Jail {

	/*
	STATIC LOGIC
	*/
	private static Jail instance;

	public static Jail getInstance() {
		if (instance == null) {
			instance = new Jail();
		}

		return instance;
	}

	public static void setJailLocation(Location jailLocation) {
		FileConfiguration fileConfiguration = SpaceLampJail.getPlugin().getConfig();
		fileConfiguration.set("jail.world", jailLocation.getWorld().getName());
		fileConfiguration.set("jail.x", jailLocation.getX());
		fileConfiguration.set("jail.y", jailLocation.getY());
		fileConfiguration.set("jail.z", jailLocation.getZ());
		fileConfiguration.set("jail.yaw", jailLocation.getYaw());
		fileConfiguration.set("jail.pitch", jailLocation.getPitch());
		SpaceLampJail.getPlugin().saveConfig();

		getInstance().setSavedJailLocation(jailLocation);
	}

	/*
	INSTANCE LOGIC
	*/
	private Location jailLocation;
	private ArrayList<Inmate> inmates;

	private Jail() {
		this.jailLocation = getJailLocationFromConfig();
		this.inmates = new ArrayList<>(2);
	}

	public boolean isJailSet() {
		return jailLocation != null;
	}

	public Location getLocation() {
		return jailLocation;
	}

	protected void setSavedJailLocation(Location jailLocation) {
		this.jailLocation = jailLocation;
	}

	public boolean isPlayerJailed(UUID uuid) {
		Inmate inmate = getInmate(uuid);

		return inmate != null;
	}

	public void checkPlayerShouldBeJailed(UUID uuid) {
		Inmate inmate = getInmate(uuid);

		long now = (new Date()).getTime() / 1000;
		if (inmate.getInTime() + inmate.getDuration() < now) {
			unjailPlayer(inmate.getUuid());
		}
	}

	public boolean jailPlayer(Player p, long duration) {
		if (isPlayerJailed(p.getUniqueId())) return false;
		if (!isJailSet()) return false;

		Inmate inmate = new Inmate(p, duration);
		inmates.add(inmate);
		p.teleport(getLocation());

		return true;
	}

	public boolean unjailPlayer(UUID uuid) {
		Inmate inmate = getInmate(uuid);
		if (inmate == null) return false;

		inmate.release();
		inmates.remove(inmate);
		return true;
	}

	public boolean unjailPlayer(Player p) {
		return unjailPlayer(p.getUniqueId());
	}

	public Inmate getInmate(UUID uuid) {
		Inmate inmate = inmates.stream().filter(curInmate -> curInmate.getUuid().equals(uuid)).findAny().orElse(null);

		return inmate;
	}

	private Location getJailLocationFromConfig() {
		SpaceLampJail plugin = SpaceLampJail.getPlugin();
		FileConfiguration fileConfiguration = plugin.getConfig();
		Location l = null;

		String jailConfigKey = "jail";
		if (fileConfiguration.isConfigurationSection(jailConfigKey)) {
			try {
				String 	worldName = fileConfiguration.getString(jailConfigKey + ".world");
				double 	x = fileConfiguration.getDouble(jailConfigKey + ".x"),
						y= fileConfiguration.getDouble(jailConfigKey + ".y"),
						z = fileConfiguration.getDouble(jailConfigKey + ".z"),
						yaw = fileConfiguration.getDouble(jailConfigKey + ".yaw"),
						pitch = fileConfiguration.getDouble(jailConfigKey + ".pitch");

				World world = plugin.getServer().getWorld(worldName);
				l = new Location(world, x, y, z, (float) yaw, (float) pitch);
			} catch(Exception e) {}
			//Ignore exception, just return a null location
		}

		return l;
	}

}
