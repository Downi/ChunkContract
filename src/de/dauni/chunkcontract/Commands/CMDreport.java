package de.dauni.chunkcontract.Commands;

import java.util.Date;

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

public class CMDreport extends DefaultCMD {
	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (player != null) {
			if (args.length >= 1) {
					if(W.contracts.getFile().get(args[1]+".contract") != null) {
						Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
						if(String.valueOf(contract.partner).equals(String.valueOf(player.getName())) || String.valueOf(contract.owner).equals(String.valueOf(player.getName())) || PermissionsM.hasPerm(player, Permissions.allcommands, true)) {
							if(contract.reported == false && (contract.status.equals(1) || contract.status.equals(2) || contract.status.equals(6) || contract.status.equals(7))) {
								if(String.valueOf(contract.partner).equals(String.valueOf(player.getName()))) {
									Bukkit.getServer().dispatchCommand(player, "pe open [Contract "+contract.id+"] Report by Partner "+contract.partner);
								}
								if(String.valueOf(contract.owner).equals(String.valueOf(player.getName()))) {
									Bukkit.getServer().dispatchCommand(player, "pe open [Contract "+contract.id+"] Report by Owner "+contract.owner);	
								}
								ContractHandler.setReported(contract);
							} else {
								MessageM.sendMessage(null, "11111");
								MessageM.sendFMessage(player, ConfigC.error_noPermission);	
							}
						} else {
							MessageM.sendMessage(null, "2222222");
							MessageM.sendFMessage(player, ConfigC.error_noPermission);	
						}
					}
			} else {
				MessageM.sendFMessage(player, ConfigC.error_notEnoughArguments);	
			}
		}
		return true;
	}
}
