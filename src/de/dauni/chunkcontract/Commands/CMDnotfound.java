package de.dauni.chunkcontract.Commands;

import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.Managers.MessageM;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDnotfound {

	public static boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		MessageM.sendFMessage(player, ConfigC.error_commandNotFound);
		return true;
	}
}
