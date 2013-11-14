package de.dauni.chunkcontract.Commands;


import java.util.ArrayList;
import java.util.Date;

import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.Contract;
import de.dauni.chunkcontract.W;
import de.dauni.chunkcontract.Managers.MessageM;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDcreate extends DefaultCMD {
	@Override
	public boolean exectue(Player player, Command cmd, String label, String[] args) {
		if (player != null) {
			Integer id = W.contractList.size()+1;
			Contract contract = new Contract(id, new Date(), player.getName(), "", null, "", "BYTES", "", false, null, null, null, null, null, 0, false, false, 1, "", new ArrayList<String>());	
			W.contracts.getFile().set(String.valueOf(id)+".contract", contract);
			W.contracts.save();
			W.contractList.add(contract);
			MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
			MessageM.sendMessage(player, "&9[Contract]&5|&aContract Settings");
			MessageM.sendMessage(player, "&9[Contract]&5|&aSubject: &e /contract set "+contract.id+" subject <value>");
			MessageM.sendMessage(player, "&9[Contract]&5|&aPartner: &e /contract set "+contract.id+" partner <value>");
			MessageM.sendMessage(player, "&9[Contract]&5|&aDue: &e /contract set "+contract.id+" due <hh:mm dd/mm/yyyy>");
			MessageM.sendMessage(player, "&9[Contract]&5|&aType(Service): &e /contract set "+contract.id+" type 1");
			MessageM.sendMessage(player, "&9[Contract]&5|&aType(Borrowing): &e /contract set "+contract.id+" type 2");
			MessageM.sendMessage(player, "&9[Contract]&5|&aType(Promise): &e /contract set "+contract.id+" type 3");
			MessageM.sendMessage(player, "&9[Contract]&5|&aInsurance: &e /contract set "+contract.id+" insurance <value>");
			MessageM.sendMessage(player, "&9[Contract]&5|&aReward(Bytes): &e /contract set "+contract.id+" reward b:<amount> ");
			MessageM.sendMessage(player, "&9[Contract]&5|&aReward(Item): &e /contract set "+contract.id+" reward i:<itemid> ");
			MessageM.sendMessage(player, "&9[Contract]&5+---------------------------------------------------+");
			MessageM.sendFMessage(player, ConfigC.normal_contractadded, "id-"
					+ String.valueOf(W.contractList.size()));
		} else {
			MessageM.sendFMessage(player, ConfigC.error_onlyIngame);
		}
		return true;
	}
}
