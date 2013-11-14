package de.dauni.chunkcontract.Commands;

import de.dauni.chunkcontract.ChunkContract;
import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.Contract;
import de.dauni.chunkcontract.PermissionsC.Permissions;
import de.dauni.chunkcontract.W;
import de.dauni.chunkcontract.Managers.MessageM;
import de.dauni.chunkcontract.Managers.PermissionsM;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDview extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		if (player != null) {
			if (args.length >= 1) {
					if(W.contracts.getFile().get(args[1]+".contract") != null) {
						Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
						if(String.valueOf(contract.owner).equals(String.valueOf(player.getName())) || String.valueOf(contract.partner).equals(String.valueOf(player.getName())) || PermissionsM.hasPerm(player, Permissions.view_all, true)) {
							MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
							MessageM.sendMessage(player, "&9[Contract]&5|&aContract "+contract.id+" Information");
							MessageM.sendMessage(player, "&9[Contract]&5|&aOwner: &e"+contract.owner);
							MessageM.sendMessage(player, "&9[Contract]&5|&aSubject: &e"+contract.subject);
							if(contract.type == 1) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aType: &eService");
							} else if(contract.type == 2) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aType: &eBorrowing");
							} else if(contract.type == 3) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aType: &ePromise");
							}
							if(contract.status == 1) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aStatus: &2Active");
							} else if(contract.status == 2) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aStatus: &cCanceled");
							} else if(contract.status == 3) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aStatus: &7Done");
							} else if(contract.status == 4) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aStatus: &4Reported");
							} else if(contract.status == 5) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aStatus: &8Declined");
							} else if(contract.status == 6) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aStatus: &cCancel Request from Owner");
							} else if(contract.status == 7) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aStatus: &cCancel Request from Partner");
							} else if(contract.waitingforapproval == true) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aStatus: &eSended");
							} else {
								MessageM.sendMessage(player, "&9[Contract]&5|&aStatus: &eCreated");
							}
							MessageM.sendMessage(player, "&9[Contract]&5|&aDue to: &e"+contract.due);
							if(contract.type != 3) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aReward: &e"+contract.reward+"&a(&e"+contract.reward_type+"&a)");
							}
							MessageM.sendMessage(player, "&9[Contract]&5|&aCreated: &e"+contract.created);
							MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
							MessageM.sendMessage(player, "&9[Contract]&5|&aPartner: &e"+contract.partner);
							try {
							if(contract.partner_status == true) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aPartner Accepted: &e"+contract.partner_status_at);
							} else if(contract.partner_status == false) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aPartner Declined: &e"+contract.partner_status_at);
							}
							} catch(Exception eq) {
							}
							MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
							if(contract.status == 2) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aCanceled: &e"+contract.closed_at);
								MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
							}
							if(contract.reported == true) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aReported: &e"+contract.report_at);
								MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
							}
							if(contract.closed == true && contract.status != 2) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aClosed: &e"+contract.closed_at);
								MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
							}
							
							
						} else {
							MessageM.sendFMessage(player, ConfigC.error_noPermission);	
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
