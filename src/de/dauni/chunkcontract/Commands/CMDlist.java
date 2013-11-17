package de.dauni.chunkcontract.Commands;

import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.ChunkContract;
import de.dauni.chunkcontract.Contract;
import de.dauni.chunkcontract.ContractHandler;
import de.dauni.chunkcontract.PermissionsC;
import de.dauni.chunkcontract.W;
import de.dauni.chunkcontract.Managers.CommandM;
import de.dauni.chunkcontract.Managers.MessageM;
import de.dauni.chunkcontract.Managers.PermissionsM;
import de.dauni.chunkcontract.PermissionsC.Permissions;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CMDlist extends DefaultCMD {
	@Override
	public boolean exectue(Player player, Command cmd, String label, String[] args) {
		if (player != null) {
			if (args.length >= 2) {
				try {
				if(args[1].equalsIgnoreCase("own") == true) {
					ContractHandler.list("own", player);
				} else if(args[1].equalsIgnoreCase("open") == true && PermissionsM.hasPerm(player, Permissions.list_open, false)) {
					ContractHandler.list("open", player);
				} else if(args[1].equalsIgnoreCase("closed") == true && PermissionsM.hasPerm(player, Permissions.list_closed, false)) {
					ContractHandler.list("closed", player);
				} else {
					ContractHandler.list("own", player);
				}
				} catch(Exception e1) {
					ContractHandler.list("own", player);
				}
			} else {
				ContractHandler.list("own", player);
			}
		} else {
			MessageM.sendFMessage(player, ConfigC.error_onlyIngame);
		}
		return true;
	}
}
