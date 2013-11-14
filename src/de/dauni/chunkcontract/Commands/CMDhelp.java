package de.dauni.chunkcontract.Commands;

import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.ChunkContract;
import de.dauni.chunkcontract.W;
import de.dauni.chunkcontract.Managers.CommandM;
import de.dauni.chunkcontract.Managers.MessageM;
import de.dauni.chunkcontract.Managers.PermissionsM;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

public class CMDhelp extends DefaultCMD {
	@Override
	public boolean exectue(Player player, Command cmd, String label,
			String[] args) {
		int amountCommands = 0;
		for (CommandM command : W.commands) {
			if (command.usage != null) {
				amountCommands = amountCommands + 1;
			}
		}

		int maxPages = Math.round(amountCommands / 3);
		if (maxPages <= 0) {
			maxPages = 1;
		}

		if (args.length <= 1) {
			int page = 1;
			MessageM.sendFMessage(player, ConfigC.chat_headerhigh, "header-"
					+ ChunkContract.pdfFile.getName()+ " by Daunilein" + " %Nhelp page %A"
					+ page + "%N/%A" + maxPages);
			int i = 1;
			for (CommandM command : W.commands) {
				if (i <= 4) {
					if (command.usage != null) {
						if (PermissionsM.hasPerm(player, command.permission,
								false)) {
							MessageM.sendMessage(
									player,
									"%A"
											+ command.usage
											+ "%N - "
											+ W.messages.getFile().get(
													command.help.location));
						} else {
							MessageM.sendMessage(
									player,
									"%W"
											+ command.usage
											+ "%N - "
											+ W.messages.getFile().get(
													command.help.location));
						}
						i = i + 1;
					}
				}
			}

			MessageM.sendFMessage(player, ConfigC.chat_headerhigh,
					"header-&oHelp Page");
		} else {
			int page = 1;
			try {
				page = Integer.valueOf(args[1]);
			} catch (NumberFormatException e) {
				page = 1;
			}

			if (maxPages < page) {
				maxPages = page;
			}

			MessageM.sendFMessage(player, ConfigC.chat_headerhigh, "header-"
					+ ChunkContract.pdfFile.getName() + " %Nhelp page %A"
					+ page + "%N/%A" + maxPages);

			int i = 1;
			for (CommandM command : W.commands) {
				if (i <= (page * 4) + 4) {
					if (command.usage != null) {
						if (i >= ((page - 1) * 4) + 1
								&& i <= ((page - 1) * 4) + 4) {
							if (PermissionsM.hasPerm(player,
									command.permission, false)) {
								MessageM.sendMessage(
										player,
										"%A"
												+ command.usage
												+ "%N - "
												+ W.messages.getFile().get(
														command.help.location));
							} else {
								MessageM.sendMessage(
										player,
										"%W"
												+ command.usage
												+ "%N - "
												+ W.messages.getFile().get(
														command.help.location));
							}
						}
						i = i + 1;
					}
				}
			}
			MessageM.sendFMessage(player, ConfigC.chat_headerhigh,
					"header-&oHelp Page");
		}
		return true;
	}
}
