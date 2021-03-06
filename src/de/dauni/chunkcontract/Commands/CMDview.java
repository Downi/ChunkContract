package de.dauni.chunkcontract.Commands;

import java.text.SimpleDateFormat;

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
			if (args.length > 1) {
					if(W.contracts.getFile().get(args[1]+".contract") != null) {
						Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
						if(String.valueOf(contract.owner).equals(String.valueOf(player.getName())) || String.valueOf(contract.partner).equals(String.valueOf(player.getName())) || PermissionsM.hasPerm(player, Permissions.view_all, true)) {
							MessageM.sendMessage(player, "&9[Contract]&5+---------------&aContract "+contract.id+" Information&5---------------+");
							MessageM.sendMessage(player, "&9[Contract]&5|&aContract "+contract.id+" Information");
							MessageM.sendMessage(player, "&9[Contract]&5|&aOwner: &e"+contract.owner);
							MessageM.sendMessage(player, "&9[Contract]&5|&aSubject: &e"+contract.subject);
							if(contract.type == 1) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aType: &eService");
							} else if(contract.type == 2) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aType: &eBorrow");
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
							if(contract.due != null) {
							MessageM.sendMessage(player, "&9[Contract]&5|&aDue to: &e"+new SimpleDateFormat("HH:mm MM/dd/yyyy").format(contract.due));
							} else {
								MessageM.sendMessage(player, "&9[Contract]&5|&aDue to: &e");
							}
							MessageM.sendMessage(player, "&9[Contract]&5|&aInsurance: &e"+contract.insurance);
							if(contract.type != 3) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aReward: &e"+contract.reward+"&a(&e"+contract.reward_type+"&a)");
							}
							if(contract.created != null) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aCreated: &e"+new SimpleDateFormat("HH:mm MM/dd/yyyy").format(contract.created));
							} else {
								MessageM.sendMessage(player, "&9[Contract]&5|&aCreated: &e");
							}
							MessageM.sendMessage(player, "&9[Contract]&5+-----------------&aPartner Information&5-----------------+");
							MessageM.sendMessage(player, "&9[Contract]&5|&aPartner: &e"+contract.partner);
							try {
							if(contract.partner_status == true) {
								if(contract.due != null) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aPartner Accepted: &e"+new SimpleDateFormat("HH:mm MM/dd/yyyy").format(contract.partner_status_at));
								} else {
									MessageM.sendMessage(player, "&9[Contract]&5|&aPartner Accepted: &e");
								}
							} else if(contract.partner_status == false) {
								if(contract.due != null) {
									MessageM.sendMessage(player, "&9[Contract]&5|&aPartner Declined: &e"+new SimpleDateFormat("HH:mm MM/dd/yyyy").format(contract.partner_status_at));
								} else {
									MessageM.sendMessage(player, "&9[Contract]&5|&aPartner Declined: &e");
								}
							}
							} catch(Exception eq) {
							}
							MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
							if(contract.comments.size() > 0) {

								MessageM.sendMessage(player, "&9[Contract]&5+---------------------&aComments&5---------------------+");
								for(String comment : contract.comments){
									MessageM.sendMessage(player, "&9[Contract]&5|&e"+comment);
								}
								MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
							}
							if(contract.status == 2) {
								MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
								if(contract.due != null) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aCanceled: &e"+new SimpleDateFormat("HH:mm MM/dd/yyyy").format(contract.closed_at));
								} else {
									MessageM.sendMessage(player, "&9[Contract]&5|&aCanceled: &e");
								}
								MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
							}
							if(contract.reported == true) {
								MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
								if(contract.due != null) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aReported: &e"+new SimpleDateFormat("HH:mm MM/dd/yyyy").format(contract.report_at));
								} else {
									MessageM.sendMessage(player, "&9[Contract]&5|&aReported: &e");
								}
								MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
							}
							if(contract.closed == true && contract.status != 2) {
								MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
								if(contract.due != null) {
								MessageM.sendMessage(player, "&9[Contract]&5|&aClosed: &e"+new SimpleDateFormat("HH:mm MM/dd/yyyy").format(contract.closed_at));
								} else {
									MessageM.sendMessage(player, "&9[Contract]&5|&aClosed: &e");
								}
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
