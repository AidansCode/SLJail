package com.aidanmurphey.spacelampjail;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utilities {

	public static String translateColorCodes(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public static String getPluginPrefix() {
		String prefix = SpaceLampJail.getPlugin().getConfig().getString("messagePrefix");

		return prefix + "&r";
	}

	public static String getJailedMessage() {
		String message = SpaceLampJail.getPlugin().getConfig().getString("jailedMessage");

		return message;
	}

	public static String getAdminJailedMessage() {
		String message = SpaceLampJail.getPlugin().getConfig().getString("adminJailedMessage");

		return message;
	}

	public static void sendJailedMessages(CommandSender inmate, CommandSender admin, String inmateName, String adminName, long duration) {
		String inmateMessage = getJailedMessage(),
				adminMessage = getAdminJailedMessage();

		inmateMessage = inmateMessage
				.replaceAll("%ADMIN%", adminName)
				.replaceAll("%INMATE%", inmateName)
				.replaceAll("%DURATION%", Long.toString(duration));
		adminMessage = adminMessage
				.replaceAll("%ADMIN%", adminName)
				.replaceAll("%INMATE%", inmateName)
				.replaceAll("%DURATION%", Long.toString(duration));

		sendMessage(inmate, inmateMessage);
		sendMessage(admin, adminMessage);
	}

	public static String getUnjailedMessage() {
		String message = SpaceLampJail.getPlugin().getConfig().getString("unjailMessage");

		return message;
	}

	public static void sendUnjailedMessage(CommandSender sender) {
		sendMessage(sender, getUnjailedMessage());
	}

	public static void sendMessage(CommandSender sender, String message) {
		message = getPluginPrefix() + " " + message;

		sender.sendMessage(translateColorCodes(message));
	}

	public static boolean isValidTime(String timeStr) {
		try {
			int time = Integer.parseInt(timeStr);
			if (time < 1) return false;
		} catch(Exception e) {
			return false;
		}

		return true;
	}

}
