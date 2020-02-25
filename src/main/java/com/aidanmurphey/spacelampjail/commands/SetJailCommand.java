package com.aidanmurphey.spacelampjail.commands;

import com.aidanmurphey.spacelampjail.Jail;
import com.aidanmurphey.spacelampjail.Utilities;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetJailCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Utilities.sendMessage(sender, "You must be a player to use this command!");
			return true;
		}

		Player p = (Player) sender;
		Location l = p.getLocation();
		Jail.setJailLocation(l);

		Utilities.sendMessage(sender, "Successfully set the jail location!");

		return true;
	}

}
