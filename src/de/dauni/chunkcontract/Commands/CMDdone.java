package de.dauni.chunkcontract.Commands;

import de.dauni.chunkcontract.ChunkContract;
import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.Contract;
import de.dauni.chunkcontract.ContractHandler;
import de.dauni.chunkcontract.W;
import de.dauni.chunkcontract.Managers.MessageM;
import de.dauni.chunkcontract.Managers.PermissionsM;
import de.dauni.chunkcontract.PermissionsC.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDdone extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (player != null) {
			if (args.length >= 1) {
					if(W.contracts.getFile().get(args[1]+".contract") != null) {
						Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
						if(String.valueOf(contract.owner).equals(String.valueOf(player.getName()))) {
							if(contract.status.equals(1)) {
								ContractHandler.setDone(contract, player);
							} else {
								MessageM.sendFMessage(player, ConfigC.error_noPermission);	
							}
						} else if(String.valueOf(contract.partner).equals(String.valueOf(player.getName()))) {
							MessageM.sendFMessage(player, ConfigC.error_onlyOwner);	
						} else {
							MessageM.sendFMessage(player, ConfigC.error_noPermission);	
						}
					} else {
						MessageM.sendFMessage(player, ConfigC.error_contractNotFound);	
					}
			} else {
				MessageM.sendFMessage(player, ConfigC.error_notEnoughArguments);	
			}
		}
		return true;
	}
}
