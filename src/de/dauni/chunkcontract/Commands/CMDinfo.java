package de.dauni.chunkcontract.Commands;

import de.dauni.chunkcontract.ChunkContract;
import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.Managers.MessageM;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDinfo extends DefaultCMD {

	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		MessageM.sendFMessage(player, ConfigC.chat_headerhigh, "header-"
				+ ChunkContract.pdfFile.getName());
		MessageM.sendMessage(player, "%A%name%%N made by %A%autors%%N.",
				"name-" + "ChunkContract", "autors-"
						+ ChunkContract.pdfFile.getAuthors().get(0));
		MessageM.sendMessage(player, "%NVersion: %A%version%%N.", "version-"
				+ ChunkContract.pdfFile.getVersion());
		MessageM.sendMessage(player, "%NType %A%helpusage% %Nfor help.",
				"helpusage-" + ChunkContract.CMDhelp.usage);
		MessageM.sendFMessage(player, ConfigC.chat_headerhigh,
				"header-&oInfo Page");
		return true;
	}
}
