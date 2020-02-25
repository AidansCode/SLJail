package com.aidanmurphey.spacelampjail.commands;

import com.aidanmurphey.spacelampjail.Jail;
import com.aidanmurphey.spacelampjail.SpaceLampJail;
import com.aidanmurphey.spacelampjail.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnjailCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length != 1) {
			Utilities.sendMessage(sender, "Incorrect format! Use: /unjail <player>");
			return true;
		}

		Player p = SpaceLampJail.getPlugin().getServer().getPlayer(args[0]);
		if (p == null) {
			Utilities.sendMessage(sender, "Player not found!");
			return true;
		}

		Jail jail = Jail.getInstance();
		if (!jail.isPlayerJailed(p.getUniqueId())) {
			Utilities.sendMessage(sender, "Player is not in jail! Use /jail to lock 'em up");
			return true;
		}

		jail.unjailPlayer(p);

		Utilities.sendMessage(sender, "&cSuccessfully released " + p.getName() + " from jail!");
		return true;
	}

}
