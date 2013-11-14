package de.dauni.chunkcontract.Managers;

import java.util.List;

import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.PermissionsC.Permissions;
import de.dauni.chunkcontract.W;
import de.dauni.chunkcontract.Commands.DefaultCMD;

public class CommandM {
	public String name;
	public String label;
	public String args;
	public String argsalias;
	public Permissions permission;
	public ConfigC help;
	public boolean enabled;
	public List<String> mainTABlist;
	public DefaultCMD CMD;
	public String usage;

	public CommandM (String name, String label, String args, String argsalias,
			Permissions permission, ConfigC help, Boolean enabled,
			List<String> mainTABlist, DefaultCMD CMD, String usage) {
		this.name = name;
		this.label = label;
		this.args = args;
		this.argsalias = argsalias;
		this.permission = permission;
		this.help = help;
		this.enabled = enabled;
		this.mainTABlist = mainTABlist;
		this.CMD = CMD;
		this.usage = usage;

		W.commands.add(this);
	}
}
