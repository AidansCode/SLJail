package com.aidanmurphey.spacelampjail;

import com.aidanmurphey.spacelampjail.commands.JailCommand;
import com.aidanmurphey.spacelampjail.commands.JailTimeCommand;
import com.aidanmurphey.spacelampjail.commands.SetJailCommand;
import com.aidanmurphey.spacelampjail.commands.UnjailCommand;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class SpaceLampJail extends JavaPlugin {

	private static SpaceLampJail plugin;

	public static SpaceLampJail getPlugin() {
		return plugin;
	}

	@Override
	public void onEnable() {
		PluginDescriptionFile pluginDescriptionFile = getDescription();
		getLogger().info("Enabling " + pluginDescriptionFile.getName() + "...");

		plugin = this;
		saveDefaultConfig();

		getCommand("jail").setExecutor(new JailCommand());
		getCommand("unjail").setExecutor(new UnjailCommand());
		getCommand("setjail").setExecutor(new SetJailCommand());
		getCommand("jailtime").setExecutor(new JailTimeCommand());

		Jail.getInstance();
		JailListeners jailListeners = new JailListeners();
		getServer().getPluginManager().registerEvents(jailListeners, this);
	}

	@Override
	public void onDisable() {
		getLogger().info("Saving current jailed players...");

		FileConfiguration fileConfiguration = getConfig();
		ArrayList<Inmate> inmates = Jail.getInstance().getInmates();
		fileConfiguration.set("jailedPlayers", "");
		inmates.forEach(inmate -> {
			System.out.println(inmate.getUuid().toString());
			String uuid = inmate.getUuid().toString();
			Location l = inmate.getLocation();

			ConfigurationSection configurationSection = fileConfiguration.createSection("jailedPlayers." + uuid);
			configurationSection.set("world", l.getWorld().getName());
			configurationSection.set("x", l.getX());
			configurationSection.set("y", l.getY());
			configurationSection.set("z", l.getZ());
			configurationSection.set("yaw", l.getYaw());
			configurationSection.set("pitch", l.getPitch());
			configurationSection.set("inTime", inmate.getInTime());
			configurationSection.set("foodLevel", inmate.getFoodLevel());
			configurationSection.set("saturation", inmate.getSaturation());
			configurationSection.set("health", inmate.getHealth());
			configurationSection.set("duration", inmate.getDuration());
		});
		saveConfig();
	}
}
