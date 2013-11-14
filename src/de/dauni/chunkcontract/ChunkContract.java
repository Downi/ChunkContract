package de.dauni.chunkcontract;

import java.util.ArrayList;
import java.util.List;

import de.dauni.chunkcontract.PermissionsC.Permissions;
import de.dauni.chunkcontract.Commands.CMDnotfound;
import de.dauni.chunkcontract.Commands.CMDcreate;
import de.dauni.chunkcontract.Commands.CMDset;
import de.dauni.chunkcontract.Commands.CMDsign;
import de.dauni.chunkcontract.Commands.CMDapprove;
import de.dauni.chunkcontract.Commands.CMDdecline;
import de.dauni.chunkcontract.Commands.CMDdone;
import de.dauni.chunkcontract.Commands.CMDcancel;
import de.dauni.chunkcontract.Commands.CMDreport;
import de.dauni.chunkcontract.Commands.CMDlist;
import de.dauni.chunkcontract.Commands.CMDview;
import de.dauni.chunkcontract.Commands.CMDinfo;
import de.dauni.chunkcontract.Commands.CMDhelp;
import de.dauni.chunkcontract.Managers.CommandM;
import de.dauni.chunkcontract.Managers.ConfigM;
import de.dauni.chunkcontract.Managers.MessageM;
import de.dauni.chunkcontract.Managers.PermissionsM;
import de.dauni.chunkcontract.ContractHandler;
import de.dauni.chunkcontract.Contract;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ChunkContract extends JavaPlugin implements Listener {

	public static PluginDescriptionFile pdfFile;
	public static ChunkContract plugin;

	@SuppressWarnings("serial")
	public static List<String> ChunkContractCMD = new ArrayList<String>() {
		{
			add("create");
			add("set");
			add("sign");
			add("approve");
			add("decline");
			add("done");
			add("cancel");
			add("report");
			add("list");
			add("view");
			add("info");
			add("help");
		}
	};

	public static CommandM CMD;
	public static CommandM CMDcreate;
	public static CommandM CMDset;
	public static CommandM CMDsign;
	public static CommandM CMDapprove;
	public static CommandM CMDdecline;
	public static CommandM CMDdone;
	public static CommandM CMDcancel;
	public static CommandM CMDreport;
	public static CommandM CMDlist;
	public static CommandM CMDview;
	public static CommandM CMDinfo;
	public static CommandM CMDhelp;

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		ConfigurationSerialization.registerClass(Contract.class, "ContractItem");
		pdfFile = getDescription();
		plugin = this;

		ConfigM.newFiles();

		CMD = new CommandM("Contract", "contract", null, null,
				Permissions.info, ConfigC.help_info,
				(Boolean) W.config.get(ConfigC.commandEnabled_info),
				ChunkContractCMD, new CMDhelp(), null);
		CMDcreate = new CommandM("Contract HELP", "contract", "create", "c",
				Permissions.create, ConfigC.help_create,
				(Boolean) W.config.get(ConfigC.commandEnabled_create),
				ChunkContractCMD, new CMDcreate(),
				"/contract <create|c>");
		CMDset = new CommandM("Contract HELP", "contract", "set", "se",
				Permissions.help, ConfigC.help_set,
				(Boolean) W.config.get(ConfigC.commandEnabled_set),
				ChunkContractCMD, new CMDset(),
				"/contract set <id> <subject|due|reward|partner> <value>");
		CMDsign = new CommandM("Contract HELP", "contract", "sign", "si",
				Permissions.sign, ConfigC.help_sign,
				(Boolean) W.config.get(ConfigC.commandEnabled_sign),
				ChunkContractCMD, new CMDsign(),
				"/contract sign <id>");
		CMDapprove = new CommandM("Contract APPROVE", "contract", "approve", "ap",
				Permissions.approve, ConfigC.help_approve,
				(Boolean) W.config.get(ConfigC.commandEnabled_approve),
				ChunkContractCMD, new CMDapprove(),
				"/contract approve <id>");
		CMDdecline = new CommandM("Contract DECLINE", "contract", "decline", "de",
				Permissions.decline, ConfigC.help_decline,
				(Boolean) W.config.get(ConfigC.commandEnabled_decline),
				ChunkContractCMD, new CMDdecline(),
				"/contract decline <id>");
		CMDdone = new CommandM("Contract DONE", "contract", "done", "done",
				Permissions.done, ConfigC.help_done,
				(Boolean) W.config.get(ConfigC.commandEnabled_done),
				ChunkContractCMD, new CMDdone(),
				"/contract done <id>");
		CMDcancel = new CommandM("Contract CANCEL", "contract", "cancel", "cancel",
				Permissions.cancel, ConfigC.help_cancel,
				(Boolean) W.config.get(ConfigC.commandEnabled_cancel),
				ChunkContractCMD, new CMDcancel(),
				"/contract cancel <id>");
		CMDreport = new CommandM("Contract REPORT", "contract", "report", "r",
				Permissions.report, ConfigC.help_report,
				(Boolean) W.config.get(ConfigC.commandEnabled_report),
				ChunkContractCMD, new CMDreport(),
				"/contract <report|r>  <id>");
		
		CMDlist = new CommandM("Contract HELP", "contract", "list", "l",
				Permissions.list, ConfigC.help_list,
				(Boolean) W.config.get(ConfigC.commandEnabled_list),
				ChunkContractCMD, new CMDlist(),
				"/contract <list|l> <open|closed>");
		CMDview = new CommandM("Contract VIEW", "contract", "view", "v",
				Permissions.view, ConfigC.help_view,
				(Boolean) W.config.get(ConfigC.commandEnabled_view),
				ChunkContractCMD, new CMDview(),
				"/contract <view|v> <id>");
		
		
		CMDinfo = new CommandM("Contract INFO", "contract", "info", "i",
				Permissions.info, ConfigC.help_info,
				(Boolean) W.config.get(ConfigC.commandEnabled_info),
				ChunkContractCMD, new CMDinfo(), "/contract [info|i]");
		CMDhelp = new CommandM("Contract HELP", "contract", "help", "h",
				Permissions.help, ConfigC.help_help,
				(Boolean) W.config.get(ConfigC.commandEnabled_help),
				ChunkContractCMD, new CMDhelp(),
				"/contract <help|h> [page number]");
		ContractHandler.loadContracts();

	}

	public void onDisable() {
		MessageM.sendFMessage(null, ConfigC.log_disabledPlugin, "name-"
				+ ChunkContract.pdfFile.getName(),
				"version-" + ChunkContract.pdfFile.getVersion(), "autors-"
						+ ChunkContract.pdfFile.getAuthors().get(0));
	}

	/**
	 * Args to String. Makes 1 string.
	 * 
	 * @param input
	 *            String list which should be converted to a string.
	 * @param startArg
	 *            Start on this length.
	 * 
	 * @return The converted string.
	 */
	public static String stringBuilder(String[] input, int startArg) {
		if (input.length - startArg <= 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder(input[startArg]);
		for (int i = ++startArg; i < input.length; i++) {
			sb.append(' ').append(input[i]);
		}
		return sb.toString();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		for (CommandM command : W.commands) {
			String[] argsSplit = null;
			String[] argsSplitAlias = null;

			if (command.args != null && command.argsalias != null) {
				argsSplit = command.args.split("/");
				argsSplitAlias = command.argsalias.split("/");
			}

			if (cmd.getName().equalsIgnoreCase(command.label)) {
				boolean equals = true;

				if (argsSplit == null) {
					if (args.length == 0) {
						equals = true;
					} else {
						equals = false;
					}
				} else {
					if (args.length >= argsSplit.length) {
						for (int i2 = argsSplit.length - 1; i2 >= 0; i2 = i2 - 1) {
							int loc = argsSplit.length - i2 - 1;
							if (!argsSplit[loc].equalsIgnoreCase(args[loc])
									&& !argsSplitAlias[loc]
											.equalsIgnoreCase(args[loc])) {
								equals = false;
							}
						}
					} else {
						equals = false;
					}
				}

				if (equals) {
					if (PermissionsM.hasPerm(player, command.permission, true)) {
						if (command.enabled) {
							command.CMD.exectue(player, cmd, label, args);
						} else {
							MessageM.sendFMessage(player,
									ConfigC.error_commandNotEnabled);
						}
					}

					return true;
				}
			}
		}

		CMDnotfound.exectue(player, cmd, label, args);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String label, String[] args) {

		for (CommandM command : W.commands) {
			if (cmd.getName().equalsIgnoreCase(command.label)) {
				if (args.length == 1) {
					return command.mainTABlist;
				}
			}
		}

		return null;
	}

	/**
	 * Short a String for like the Scoreboard title.
	 * 
	 * @param string
	 *            String to be shorten.
	 * @param maxLenght
	 *            Max lenght of the characters.
	 * @return Shorten string, else normal string.
	 */
	public static String cutString(String string, int maxLenght) {
		if (string.length() > maxLenght) {
			string = string.substring(0, maxLenght);
		}
		return string;
	}
}
