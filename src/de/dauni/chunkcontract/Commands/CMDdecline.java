package de.dauni.chunkcontract.Commands;

import java.util.Date;

import de.dauni.chunkcontract.ChunkContract;
import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.Contract;
import de.dauni.chunkcontract.W;
import de.dauni.chunkcontract.Managers.MessageM;
import de.dauni.chunkcontract.Managers.PermissionsM;
import de.dauni.chunkcontract.PermissionsC.Permissions;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDdecline extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (player != null) {
			if (args.length >= 1) {
					if(W.contracts.getFile().get(args[1]+".contract") != null) {
						Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
						if(String.valueOf(contract.partner).equals(String.valueOf(player.getName()))) {
							if(contract.waitingforapproval == true) {
								contract.status = 5;
								contract.partner_status = false;
								contract.partner_status_at = new Date();
								contract.waitingforapproval = false;
								W.contracts.getFile().set(contract.id+".contract", contract);
								W.contracts.save();
								MessageM.sendMessage(player, "&2Contract "+String.valueOf(contract.id)+" declined.");	
								Player owner = Bukkit.getPlayer(contract.owner);
								if(owner!=null) {
									MessageM.sendMessage(owner, "&2Contract "+String.valueOf(contract.id)+" declined.");	
								}
							}
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
