package com.aidanmurphey.spacelampjail.commands;

import com.aidanmurphey.spacelampjail.Inmate;
import com.aidanmurphey.spacelampjail.Jail;
import com.aidanmurphey.spacelampjail.Utilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Date;

public class JailTimeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Utilities.sendMessage(sender, "You must be a player to use this command!");
			return true;
		}

		Player p = (Player) sender;
		Inmate inmate = Jail.getInstance().getInmate(p.getUniqueId());

		if (inmate == null) {
			Utilities.sendMessage(sender, "You aren't in jail!");
		} else {
			long now = (new Date()).getTime() / 1000,
				outTime = inmate.getInTime() + inmate.getDuration();

			int timeLeftSeconds = (int) (outTime - now);
			float timeLeftMinutes = timeLeftSeconds / 60.0f;
			DecimalFormat decimalFormat = new DecimalFormat("0.0");

			Utilities.sendMessage(sender, "You have " + timeLeftSeconds + " seconds (" + decimalFormat.format(timeLeftMinutes) + " minutes) left in prison!");
		}

		return true;
	}

}
