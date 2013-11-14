package de.dauni.chunkcontract.Commands;

import de.dauni.chunkcontract.Managers.MessageM;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class DefaultCMD {

	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		MessageM.sendMessage(player, "%TAG%NExample of a Command!");
		// TODO Place the command stuff here.
		return true;
	}
}
