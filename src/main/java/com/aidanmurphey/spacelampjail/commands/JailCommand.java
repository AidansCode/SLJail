package com.aidanmurphey.spacelampjail.commands;

import com.aidanmurphey.spacelampjail.Jail;
import com.aidanmurphey.spacelampjail.SpaceLampJail;
import com.aidanmurphey.spacelampjail.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JailCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String adminName = "SERVER";
		if (sender instanceof Player) {
			Player admin = (Player) sender;
			adminName = admin.getName();
		}

		if (args.length != 2) {
			Utilities.sendMessage(sender, "Incorrect format! Use: /jail <player> <duration (minutes)>");
			return true;
		}

		Player p = SpaceLampJail.getPlugin().getServer().getPlayer(args[0]);
		if (p == null) {
			Utilities.sendMessage(sender, "Player not found!");
			return true;
		}

		Jail jail = Jail.getInstance();
		if (jail.isPlayerJailed(p.getUniqueId())) {
			Utilities.sendMessage(sender, "Player is already in jail! Use /unjail <player> to release them.");
			return true;
		}

		if (!jail.isJailSet()) {
			Utilities.sendMessage(sender, "The jail must be set before players can be jailed!");
			return true;
		}

		if (!Utilities.isValidTime(args[1])) {
			Utilities.sendMessage(sender, "Invalid jail duration! Number should be in minutes and greater than 0");
			return true;
		}

		long duration = Long.parseLong(args[1]) * 60;
		jail.jailPlayer(p, duration);

		Utilities.sendJailedMessages(p, sender, p.getName(), adminName, duration);

		return true;
	}

}
