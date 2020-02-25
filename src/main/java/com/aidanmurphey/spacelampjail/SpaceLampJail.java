package com.aidanmurphey.spacelampjail;

import com.aidanmurphey.spacelampjail.commands.JailCommand;
import com.aidanmurphey.spacelampjail.commands.SetJailCommand;
import com.aidanmurphey.spacelampjail.commands.UnjailCommand;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

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

		Jail.getInstance();
		JailListeners jailListeners = new JailListeners();
		getServer().getPluginManager().registerEvents(jailListeners, this);
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pluginDescriptionFile = getDescription();
		getLogger().info("Disabling " + pluginDescriptionFile.getName() + "...");
	}
}
