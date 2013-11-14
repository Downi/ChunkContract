package de.dauni.chunkcontract.Commands;

import java.util.Date;

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

public class CMDapprove extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (player != null) {
			if (args.length >= 1) {
					if(W.contracts.getFile().get(args[1]+".contract") != null) {
						Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
						if(String.valueOf(contract.partner).equals(String.valueOf(player.getName()))) {
							if(contract.waitingforapproval == true) {
								ContractHandler.setActivate(contract);
								MessageM.sendMessage(player, "&9[Contract]&2Contract "+String.valueOf(contract.id)+" signed.");	
								MessageM.sendMessage(player, "&9[Contract]&2This Contract is now active.");	
								Player owner = Bukkit.getPlayer(contract.owner);
								if(owner!=null) {
									MessageM.sendMessage(owner, "&9[Contract]&2Contract "+String.valueOf(contract.id)+" approved.");	
									MessageM.sendMessage(owner, "&9[Contract]&2This Contract is now active.");	
								}
							}
						} else {
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
