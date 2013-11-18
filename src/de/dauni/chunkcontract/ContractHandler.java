package de.dauni.chunkcontract;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.dauni.chunkcontract.PermissionsC.Permissions;
import de.dauni.chunkcontract.Managers.MessageM;
import de.dauni.chunkcontract.Managers.PermissionsM;
import de.dauni.chunkcontract.W;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

@SuppressWarnings("deprecation")
public class ContractHandler {
	public static void loadContracts() {
		W.contractList.clear();
		for (String contractName : W.contracts.getFile().getKeys(false)) {
			W.contractList.add((Contract) W.contracts.getFile().get(contractName+".contract"));
		}
	}
	public static void update(Contract contract, Player player, String fld, String val, String type) {
		val = val.trim();
		if(fld.contains("subject") == true) {
			contract.subject = val;
		} else if(fld.contains("reward") == true) {
				contract.reward_type = type;
			contract.reward = val;
		} else if(fld.contains("partner") == true) {
			contract.partner = val;
		} else if(fld.contains("insurance") == true) {
			contract.insurance = val;
		} else if(fld.contains("type") == true && (val.equalsIgnoreCase("borrow") == true || val.equalsIgnoreCase("service") == true || val.equalsIgnoreCase("promise") == true)) {
			if(val.equalsIgnoreCase("borrow") == true) {
				contract.type = 2;
			}
			if(val.equalsIgnoreCase("service") == true) {
				contract.type = 1;
			}
			if(val.equalsIgnoreCase("promise") == true) {
				contract.type = 3;
			}
		} else if(fld.contains("due") == true) {
			try {
				Date today = new Date();
				DateFormat formatter = new SimpleDateFormat("HH:mm MM/dd/yy");
				formatter.setLenient(false);
				Date due = formatter.parse(val);
				switch (today.compareTo(due)) {
				    case -1:  contract.due = due;  break;
				    case 0:   MessageM.sendFMessage(player, ConfigC.error_wrongdateformat); return;  
				    case 1:   MessageM.sendFMessage(player, ConfigC.error_wrongdateformat); return; 
				    default:  MessageM.sendFMessage(player, ConfigC.error_wrongdateformat); return; 
				}
			} catch (ParseException e) {
				MessageM.sendFMessage(player, ConfigC.error_wrongdateformat);
				return;
			}
		} else 	{
			return;
		}	
		W.contracts.getFile().set(contract.id+".contract", contract);
		W.contracts.save();
		MessageM.sendFMessage(player, ConfigC.normal_settingsaved, "option-"
				+ fld,
				"setto-" + val);
		
	}
	public static void sign(Contract contract, Player player) {
		if(contract.waitingforapproval == true) {
			MessageM.sendFMessage(player, ConfigC.error_alreadysent);
			return;
		}
		if(contract.subject == "") {
			MessageM.sendFMessage(player, ConfigC.error_settingnotset, "opt-"
					+ "subject");
			return;
		}
		if(contract.partner == "") {
			MessageM.sendFMessage(player, ConfigC.error_settingnotset, "opt-"
					+ "partner");
			return;
		}
		if(contract.due == null) {
			MessageM.sendFMessage(player, ConfigC.error_settingnotset, "opt-"
					+ "due");
			return;
		}
		if(contract.type == null) {
			MessageM.sendFMessage(player, ConfigC.error_settingnotset, "opt-"
					+ "type");
			return;
		}
		if(contract.insurance == "") {
			MessageM.sendFMessage(player, ConfigC.error_settingnotset, "opt-"
					+ "insurance");
			return;
		}
		if(contract.reward == null && contract.type != 3) {
			MessageM.sendFMessage(player, ConfigC.error_settingnotset, "opt-"
					+ "reward");
			return;
		}
		if(contract.reward_type == null && contract.type != 3) {
			MessageM.sendFMessage(player, ConfigC.error_settingnotset, "opt-"
					+ "reward");
			return;
		}
		MessageM.sendMessage(player, "&9[Contract]&5+--------------&aContract "+contract.id+" Information--------------+");
		
		MessageM.sendMessage(player, "&9[Contract]&5|&aSubject: &e"+contract.subject);
		if(contract.type.equals(1)) {
			MessageM.sendMessage(player, "&9[Contract]&5|&aType: &eService");
		} else if(contract.type.equals(2)) {
			MessageM.sendMessage(player, "&9[Contract]&5|&aType: &eBorrow");
		} else if(contract.type.equals(3)) {
			MessageM.sendMessage(player, "&9[Contract]&5|&aType: &ePromise");
		}

		MessageM.sendMessage(player, "&9[Contract]&5|&aPartner: &e"+contract.partner);
		MessageM.sendMessage(player, "&9[Contract]&5|&aInsurance: &e"+contract.insurance);
		if(contract.due != null) {
		MessageM.sendMessage(player, "&9[Contract]&5|&aDue to: &e"+new SimpleDateFormat("HH:mm MM/dd/yyyy").format(contract.due));
		} else {
			MessageM.sendMessage(player, "&9[Contract]&5|&aDue to: &e");
		}
		if(contract.type != 3) {
			MessageM.sendMessage(player, "&9[Contract]&5|&aReward: &e"+contract.reward+"&a(&e"+contract.reward_type+"&a)");
		}
		MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
		if(contract.comments.size() > 0) {

			MessageM.sendMessage(player, "&9[Contract]&5+---------------------Comments---------------------+");
			for(String comment : contract.comments){
				MessageM.sendMessage(player, "&9[Contract]&5|&e"+comment);
			}
			MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
		}
		MessageM.sendMessage(player, "&9[Contract]&2If you really want to sign this Conctract, write ");
		MessageM.sendMessage(player, "&9[Contract]&2/contract sign "+contract.id+" approve");
		contract.waitingforapproval = true;
		W.contracts.getFile().set(contract.id+".contract", contract);
		W.contracts.save();
		return;
	}
	public static void sendToPartner(Contract contract, Player player) {
		if(contract.sended_at == null) {
		Player partner = Bukkit.getPlayer(contract.partner);
		if(partner!=null) {
			MessageM.sendMessage(partner, "&9[Contract]&5+--------------&aContract "+contract.id+" Information--------------+");
			MessageM.sendMessage(partner, "&9[Contract]&5|&aSubject: &e"+contract.subject);
			if(contract.type == 1) {
				MessageM.sendMessage(partner, "&9[Contract]&5|&aType: &eService");
			} else if(contract.type == 2) {
				MessageM.sendMessage(partner, "&9[Contract]&5|&aType: &eBorrow");
			} else if(contract.type == 3) {
				MessageM.sendMessage(partner, "&9[Contract]&5|&aType: &ePromise");
			}
			MessageM.sendMessage(partner, "&9[Contract]&5|&aPartner: &e"+contract.owner);
			MessageM.sendMessage(partner, "&9[Contract]&5|&aInsurance: &e"+contract.insurance);
			if(contract.due != null) {
				MessageM.sendMessage(partner, "&9[Contract]&5|&aDue to: &e"+new SimpleDateFormat("HH:mm MM/dd/yyyy").format(contract.due));
			} else {
				MessageM.sendMessage(partner, "&9[Contract]&5|&aDue to: &e");
			}
			if(contract.type != 3) {
				MessageM.sendMessage(partner, "&9[Contract]&5|&aReward: &e"+contract.reward+"&a(&e"+contract.reward_type+"&a)");
			}
			MessageM.sendMessage(partner, "&9[Contract]&5+---------------------------------------------------+");
			if(contract.comments.size() > 0) {

				MessageM.sendMessage(partner, "&9[Contract]&5+---------------------Comments---------------------+");
				for(String comment : contract.comments){
					MessageM.sendMessage(partner, "&9[Contract]&5|&e"+comment);
				}
				MessageM.sendMessage(partner, "&9[Contract]&5+---------------------------------------------------+");
			}
			MessageM.sendMessage(partner, "&9[Contract]&2If you really want to sign this Conctract, write ");
			MessageM.sendMessage(partner, "&9[Contract]&2/contract approve "+contract.id);
			MessageM.sendMessage(partner, "&9[Contract]&2If you want to decline this Conctract, write ");
			MessageM.sendMessage(partner, "&9[Contract]&2/contract decline "+contract.id);
			contract.sended_at = new Date();
			W.contracts.getFile().set(contract.id+".contract", contract);
			W.contracts.save();
			MessageM.sendMessage(player, "&9[Contract]&2Contract "+String.valueOf(contract.id)+" sent to Partner.");	
			MessageM.sendMessage(player, "&9[Contract]&2Waiting for Approval.");	
		} else {
			MessageM.sendFMessage(player, ConfigC.error_partnerNotOnline);
		}
		} else {
			MessageM.sendFMessage(player, ConfigC.error_alreadysent);
		}
		return;
	}
	public static void setActivate(Contract contract) {
		contract.status = 1;
		contract.partner_status = true;
		contract.partner_status_at = new Date();
		contract.waitingforapproval = false;
		W.contracts.getFile().set(contract.id+".contract", contract);
		W.contracts.save();
	}
	public static void setCancel(Contract contract) {
		contract.status = 2;
		contract.closed_at = new Date();
		W.contracts.getFile().set(contract.id+".contract", contract);
		W.contracts.save();
	}
	public static void setClose(Contract contract) {
		contract.status = 3;
		contract.closed_at = new Date();
		W.contracts.getFile().set(contract.id+".contract", contract);
		W.contracts.save();
		
	}
	public static void addComment(Contract contract, String comment) {
		contract.comments.add(comment);
		W.contracts.getFile().set(contract.id+".contract", contract);
		W.contracts.save();
		
	}
	public static void setCreate(Contract contract) {
		contract.status = 0;
		contract.partner_status = false;
		contract.partner_status_at = null;
		contract.waitingforapproval = false;
		W.contracts.getFile().set(contract.id+".contract", contract);
		W.contracts.save();
	}
	public static void setReported(Contract contract) {
		contract.status = 4;
		contract.reported = true;
		contract.report_at = new Date();
		W.contracts.getFile().set(contract.id+".contract", contract);
		W.contracts.save();
	}
	public static void setDone(Contract contract, Player player) {
		Player partner = Bukkit.getPlayer(contract.partner);
		if(partner == null) {
			MessageM.sendFMessage(player, ConfigC.error_partnerNotOnline);
			return;
		}
		if(contract.type.equals(2)) {
			MessageM.sendMessage(player, "&9[Contract]&eContract "+contract.id+": &2"+contract.subject+" successfull!");
			MessageM.sendMessage(player, "&9[Contract]&eYour Partner may give the Item now back.");
			if(partner != null) {
				MessageM.sendMessage(partner, "&9[Contract]&eContract "+contract.id+": &2"+contract.subject+" successfull!");
				MessageM.sendMessage(partner, "&9[Contract]&eYou may give the Item now back.");
				MessageM.sendMessage(partner, "&9[Contract]&eIf you want to be sure, use a Chest.");
			}
			ContractHandler.setClose(contract);
		} else if(contract.type.equals(3)) {
				MessageM.sendMessage(player, "&9[Contract]&eContract "+contract.id+": &2"+contract.subject+" successfull!");
				if(partner != null) {
					MessageM.sendMessage(partner, "&9[Contract]&eContract "+contract.id+": &2"+contract.subject+" successfull!");
				}
				ContractHandler.setClose(contract);
			} else {
			if(contract.reward_type.equalsIgnoreCase("BYTES")) {
				try {
					if(Economy.hasMore(player.getName(), Double.parseDouble(contract.reward))) {
						Economy.subtract(player.getName(), Double.parseDouble(contract.reward));
						Economy.add(partner.getName(), Double.parseDouble(contract.reward));
						MessageM.sendMessage(player, "&9[Contract]&eContract "+contract.id+": &2"+contract.subject+" successfull!");
						MessageM.sendMessage(player, "&9[Contract]&2"+contract.reward+" Bytes withdrawed.");
						MessageM.sendMessage(partner, "&9[Contract]&eContract "+contract.id+": &2"+contract.subject+" successfull!");
						MessageM.sendMessage(partner, "&9[Contract]&2 You got "+contract.reward+" Bytes.");
						ContractHandler.setClose(contract);
					} else {
						MessageM.sendFMessage(player, ConfigC.error_notEnoughMoney);
					}
				} catch (NumberFormatException | UserDoesNotExistException e) {
					MessageM.sendFMessage(player, ConfigC.error_notEnoughMoney);
					return;
				} catch (NoLoanPermittedException e) {
					MessageM.sendFMessage(player, ConfigC.error_notEnoughMoney);
					return;
				}
			} else {
				ItemStack item = ContractHandler.parseItemString(contract.reward);
				Inventory iventory = player.getInventory();
				try {
		          for(ItemStack inven : iventory.getContents()){
		              if(inven.getType().equals(item.getType())){
		            	  partner.getInventory().addItem(inven);
		            	  player.getInventory().removeItem(inven);
							MessageM.sendMessage(player, "&9[Contract]&eContract "+contract.id+": &2"+contract.subject+" successfull!");
							MessageM.sendMessage(player, "&9[Contract]&2Item "+contract.reward+" withdrawed.");
							MessageM.sendMessage(partner, "&9[Contract]&eContract "+contract.id+": &2"+contract.subject+" successfull!");
							MessageM.sendMessage(partner, "&9[Contract]&2You got the Item "+contract.reward+"!");
							ContractHandler.setClose(contract);
							return;
		              }
		          }
				} catch(Exception ex) {
					MessageM.sendFMessage(player, ConfigC.error_itemNotInInventory, "item-"+contract.reward);
					return;
				}
				MessageM.sendFMessage(player, ConfigC.error_itemNotInInventory, "item-"+contract.reward);
				return;
			}
		}
	}
	public static ItemStack parseItemString(String itemId) {
	    String[] parts = itemId.split(":");
	    int matId = Integer.parseInt(parts[0]);
	    if (parts.length == 2) {
	        short data = Short.parseShort(parts[1]);
	        return new ItemStack(Material.getMaterial(matId), 1, data);
	    }
	    return new ItemStack(Material.getMaterial(matId));
	}
	public static void list(String mode, Player player) {
		if(mode == "own") { 
			MessageM.sendMessage(player, "&9[Contract]&e______Own Contracts______");
			for(Contract contract : W.contractList) {
				if(contract.owner.equals(player.getName()) || contract.partner.equals(player.getName())) {
					if(contract.status.equals(1)) {
						MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&2[Active]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
					} else if(contract.status.equals(2)) {
						MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&c[Canceled]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
					} else if(contract.status.equals(3)) {
						MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&7[Done]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
					} else if(contract.status.equals(4)) {
						MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&4[Reported]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
					} else if(contract.status.equals(5)) {
						MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&8[Declined]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
					} else if(contract.status.equals(6)) {
						MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&c[RFO]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
					} else if(contract.status.equals(7)) {
						MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&c[RFP]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
					} else {
						MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&f[Created]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
					}
				}
			}
		} else if(mode == "closed") {
			MessageM.sendMessage(player, "&9[Contract]______Closed Contracts______");
			for(Contract contract : W.contractList) {
				if(contract.status.equals(2)) {
					MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&c[Canceled]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
				} else if(contract.status.equals(3)) {
					MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&7[Done]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
				} else if(contract.status.equals(5)) {
					MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&8[Declined]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
				}
				
			}
		} else {
			MessageM.sendMessage(player, "&9[Contract]______Open Contracts______");
			for(Contract contract : W.contractList) {
				if(contract.status.equals(1)) {
					MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&2[Active]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
				} else if(contract.status.equals(4)) {
					MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&4[Reported]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
				} else if(contract.status.equals(6)) {
					MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&c[RFO]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
				} else if(contract.status.equals(7)) {
					MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&c[RFP]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
				} else if(contract.status.equals(0)) {
					MessageM.sendMessage(player, "&9[Contract]#"+contract.id+"&f[Created]&5&e"+contract.owner+" &a->&e "+contract.partner+": &f"+contract.subject);
				}
			}
		}
	}
}
