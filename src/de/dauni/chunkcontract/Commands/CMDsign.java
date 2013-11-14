package de.dauni.chunkcontract.Commands;

import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.Contract;
import de.dauni.chunkcontract.ContractHandler;
import de.dauni.chunkcontract.W;
import de.dauni.chunkcontract.Managers.MessageM;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDsign extends DefaultCMD {
	@Override
	public boolean exectue(Player player, Command cmd, String label, String[] args) {
		if (player != null) {
			if (args.length >= 2) {
				if(W.contracts.getFile().get(args[1]+".contract") != null) {
						Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
						if(String.valueOf(contract.owner).equals(String.valueOf(player.getName()))) {
							try {
								if(args[2].contains("approve") && contract.waitingforapproval == true) {
									ContractHandler.sendToPartner(contract, player);
								} else {
								}
							} catch(Exception e1) {
								ContractHandler.sign(contract, player);
							}
						} else {
							MessageM.sendMessage(null, String.valueOf(contract.owner));
							MessageM.sendMessage(null, String.valueOf(player.getName()));
							MessageM.sendMessage(null, String.valueOf(contract.owner.length()));
							MessageM.sendMessage(null, String.valueOf(player.getName().length()));
							MessageM.sendFMessage(player,
									ConfigC.error_contractNotFound);
						}
					} else {
						MessageM.sendFMessage(player,
								ConfigC.error_contractNotFound);
					}
			} else {
				MessageM.sendFMessage(player,
						ConfigC.error_notEnoughArguments);
			}
		} else {
			MessageM.sendFMessage(player, ConfigC.error_onlyIngame);
		}
		return true;
	}
}
