package de.dauni.chunkcontract.Commands;

import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.Contract;
import de.dauni.chunkcontract.ContractHandler;
import de.dauni.chunkcontract.W;
import de.dauni.chunkcontract.Managers.MessageM;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDcomment extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (player != null) {
			if (args.length >= 2) {
					if(W.contracts.getFile().get(args[1]+".contract") != null) {
						Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
						if(contract.status == 0 || contract.status == 5) {
							String message = "";
				            for (int i = 2;i < args.length;i++) {
				                message = message + args[i] + " ";
				            }
							ContractHandler.addComment(contract, message);
							MessageM.sendFMessage(player, ConfigC.normal_commandadded);	
						} else {
							MessageM.sendFMessage(player,
									ConfigC.error_contractSettingNotPossible);
						}
					} else {
						MessageM.sendFMessage(player, ConfigC.error_noPermission);	
					}
			} else {
				MessageM.sendFMessage(player, ConfigC.error_notEnoughArguments);	
			}
		}
		return true;
	}
}
