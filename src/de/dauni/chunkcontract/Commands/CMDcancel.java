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

public class CMDcancel extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (player != null) {
			if (args.length >= 1) {
					if(W.contracts.getFile().get(args[1]+".contract") != null) {
						Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
						if(String.valueOf(contract.partner).equals(String.valueOf(player.getName())) || String.valueOf(contract.owner).equals(String.valueOf(player.getName())) || PermissionsM.hasPerm(player, Permissions.allcommands, true)) {
							if(contract.status == 1) {
								if(String.valueOf(contract.owner).equals(String.valueOf(player.getName()))) {
									contract.status = 6;
									W.contracts.getFile().set(contract.id+".contract", contract);
									W.contracts.save();
									MessageM.sendMessage(player, "&9[Contract]&2Cancel request sent.");	
									MessageM.sendMessage(player, "&9[Contract]&2If the Partner is not Online, you have to tell");	
									MessageM.sendMessage(player, "&9[Contract]&2him/her about the request.");	
									Player partner = Bukkit.getPlayer(contract.partner);
									if(partner!=null) {
										MessageM.sendMessage(partner, "&e[Contract]&2Cancel request for Contract "+contract.id+".");	
										MessageM.sendMessage(partner, "&e[Contract]&2Type /contract "+contract.id+" cancel for accepting");	
									}
								}
								if(String.valueOf(contract.partner).equals(String.valueOf(player.getName()))) {
									contract.status = 7;
									W.contracts.getFile().set(contract.id+".contract", contract);
									W.contracts.save();
									MessageM.sendMessage(player, "&9[Contract]&2Cancel request sent.");	
									MessageM.sendMessage(player, "&9[Contract]&2If the Owner is not Online, you have to tell");	
									MessageM.sendMessage(player, "&9[Contract]&2him/her about the request.");
									Player owner = Bukkit.getPlayer(contract.owner);
									if(owner!=null) {
										MessageM.sendMessage(owner, "&e[Contract]&eCancel request for Contract "+contract.id+".");	
										MessageM.sendMessage(owner, "&e[Contract]&eType /contract "+contract.id+" cancel for accepting");	
									}		
								}
							} else if(contract.status == 6) {
								if(String.valueOf(contract.partner).equals(String.valueOf(player.getName()))) {
									contract.status = 2;
									W.contracts.getFile().set(contract.id+".contract", contract);
									W.contracts.save();
									MessageM.sendMessage(player, "&9[Contract]&2Contract "+contract.id+" canceled.");	
									Player owner = Bukkit.getPlayer(contract.owner);
									if(owner!=null) {
										MessageM.sendMessage(owner, "&9[Contract]&2Contract "+contract.id+" canceled.");
									}		
								}
							} else if(contract.status == 7) {
								if(String.valueOf(contract.owner).equals(String.valueOf(player.getName()))) {
									ContractHandler.setCancel(contract);
									MessageM.sendMessage(player, "&9[Contract]&2Contract "+contract.id+" canceled.");	
									Player partner = Bukkit.getPlayer(contract.partner);
									if(partner!=null) {
										MessageM.sendMessage(partner, "&9[Contract]&2Contract "+contract.id+" canceled.");
									}		
								}
							} else {
								MessageM.sendFMessage(player, ConfigC.error_noPermission);	
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
