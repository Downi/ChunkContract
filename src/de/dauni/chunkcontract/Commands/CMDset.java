package de.dauni.chunkcontract.Commands;


import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.Contract;
import de.dauni.chunkcontract.ContractHandler;
import de.dauni.chunkcontract.PermissionsC;
import de.dauni.chunkcontract.PermissionsC.Permissions;
import de.dauni.chunkcontract.W;
import de.dauni.chunkcontract.Managers.MessageM;
import de.dauni.chunkcontract.Managers.PermissionsM;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;

public class CMDset extends DefaultCMD {
	@Override
	public boolean exectue(Player player, Command cmd, String label, String[] args) {
		if (player != null) {
			if (args.length >= 4) {
					if(args[2].equalsIgnoreCase("subject") == true || args[2].equalsIgnoreCase("type") == true || args[2].equalsIgnoreCase("due") == true || args[2].equalsIgnoreCase("reward") == true || args[2].equalsIgnoreCase("partner") == true) {
						if(W.contracts.getFile().get(args[1]+".contract") != null) {
							Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
							String message = "";
							if(String.valueOf(contract.owner).equals(String.valueOf(player.getName()))) {
								if(contract.status == 0 || contract.status == 5) {
									if(args[2].equalsIgnoreCase("reward")) {
										if(String.valueOf(args[3].charAt(0)).equalsIgnoreCase("i") && String.valueOf(args[3].charAt(1)).equalsIgnoreCase(":")) {
											String itemid = args[3].replace("i:", "");
											ItemStack item = ContractHandler.parseItemString(itemid);
											if(item==null) {
												MessageM.sendFMessage(player,
														ConfigC.error_notEnoughArguments);
												return false;
											} else {
												ContractHandler.update(contract, player, args[2], itemid, "ITEM");	
											}
										} else if(String.valueOf(args[3].charAt(0)).equalsIgnoreCase("b") && String.valueOf(args[3].charAt(1)).equalsIgnoreCase(":")) {
											Integer bytes = 0;
											try {
												bytes = Integer.parseInt(args[3].replace("b:", ""));
											} catch(Exception e1) {
												MessageM.sendFMessage(player,
														ConfigC.error_notEnoughArguments);
												return false;
											}
												ContractHandler.update(contract, player, args[2], String.valueOf(bytes), "BYTES");
										} else {
											MessageM.sendFMessage(player,
													ConfigC.error_notEnoughArguments);
											return false;
										}
									} else {
							            for (int i = 3;i < args.length;i++) {
							                message = message + args[i] + " ";
							            }
										ContractHandler.update(contract, player, args[2], message, null);
									}
								} else {
									MessageM.sendFMessage(player,
											ConfigC.error_contractSettingNotPossible);
								}
							} else {
								MessageM.sendFMessage(player,
										ConfigC.error_contractNotFound);
							}
						} else {
							MessageM.sendFMessage(player,
									ConfigC.error_contractNotFound);
						}
					} else if(args[2].equalsIgnoreCase("status") == true && PermissionsM.hasPerm(player, Permissions.set_status, false)) {
						if(W.contracts.getFile().get(args[1]+".contract") != null) {
							Contract contract = (Contract) W.contracts.getFile().get(args[1]+".contract");
							if(args[3].equalsIgnoreCase("active")) {
								ContractHandler.setActivate(contract);
							} else if(args[3].equalsIgnoreCase("cancel")) {
								ContractHandler.setCancel(contract);
							} else if(args[3].equalsIgnoreCase("close")) {
								ContractHandler.setClose(contract);
							} else if(args[3].equalsIgnoreCase("create")) {
								ContractHandler.setCreate(contract);
							} else {
								
							}
						} else {
							MessageM.sendFMessage(player,
									ConfigC.error_contractNotFound);
						}
					} else {
						MessageM.sendFMessage(player,
								ConfigC.error_contractSettingNotPossible);
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
