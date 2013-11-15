package de.dauni.chunkcontract;

import de.dauni.chunkcontract.Managers.ConfigM;

public enum ConfigC {

	chat_tag ("["+ChunkContract.pdfFile.getName()+"] ", W.config),
	chat_normal ("&b", W.config),
	chat_warning ("&c", W.config),
	chat_error ("&c", W.config),
	chat_arg ("&e", W.config),
	chat_header ("&9", W.config),
	chat_headerhigh ("%H_______.[ %A%header%%H ]._______", W.config),

	commandEnabled_info (true, W.config),
	commandEnabled_help (true, W.config),
	commandEnabled_create (true, W.config),
	commandEnabled_list (true, W.config),
	commandEnabled_report (true, W.config),
	commandEnabled_set (true, W.config),
	commandEnabled_sign (true, W.config),
	commandEnabled_approve (true, W.config),
	commandEnabled_decline (true, W.config),
	commandEnabled_done (true, W.config),
	commandEnabled_cancel (true, W.config),
	commandEnabled_view (true, W.config),
	commandEnabled_comment (true, W.config),
	
	
	
	help_info ("%NDisplays the plugin's info.", W.messages),
	help_help ("%NShows a list of commands.", W.messages),
	help_create ("%NCreates a new Contract.", W.messages),
	help_list ("%NLists all <open|closed> Contracts.", W.messages),
	help_report ("%NReport a Contract to the Staff.", W.messages),
	help_set ("%NSets the configuration for a Contract.", W.messages),
	help_sign ("%NSign a Contract. It's active if you sign it.", W.messages),
	help_approve ("%NApproves an incoming Contract", W.messages),
	help_decline ("%NDeclines an incoming Contract", W.messages),

	help_done ("%NMark a Contract as Done. Reward will given.", W.messages),
	help_cancel ("%NCancels a Contract.", W.messages),
	help_view ("%NViews a Contract", W.messages),
	help_comment ("%NAdds Comments to the Contract", W.messages),
	
	log_disabledPlugin ("%TAG%N%name%&c&k - %N%version% is now Disabled. Made by %A%autors%%N.",
			W.messages),
	error_commandNotEnabled ("%TAG%EThis command has been disabled!",
			W.messages),
	error_noPermission ("%TAG%EYou don't have the permissions to do that!",
			W.messages),
	error_onlyOwner ("%TAG%EOnly the Owner of the Contract can do this.",
			W.messages),
	error_notEnoughArguments ("%TAG%EYou're missing arguments! Check /contract help.",
			W.messages),
	error_contractNotFound ("%TAG%EContract not found.",
			W.messages),
	error_contractSettingNotPossible ("%TAG%EYou can not set the Setting right now.",
			W.messages),
					
	error_onlyIngame ("%TAG%EThis is an only in-game command!", W.messages),
	error_wrongdateformat ("%TAG%EWrong Date format! Use hh:mm mm/dd/yy.", W.messages),
	error_commandNotFound ("%TAG%ECouldn't find the command. Try %A/"
			+ ChunkContract.pdfFile.getName() + " help %Efor more info.",
			W.messages),
	error_settingnotset ("%TAG%EOption '%opt%' not set.",
			W.messages),
	error_alreadysent ("%TAG%EContract already sent.",
			W.messages),
	error_partnerNotOnline ("%TAG%EYour Partner is not Online.",
			W.messages),
	error_notEnoughMoney ("%TAG%EYou do not have enough Bytes.",
			W.messages),
	error_itemNotInInventory ("%TAG%EYou do not have &e%item% %Ein your Inventory.",
			W.messages),

			normal_contractadded ("%TAG%NContract %id% created.", W.messages),
			normal_commandadded ("%TAG%NComment added.", W.messages),
	normal_settingsaved ("%TAG%NOption %H%option%%N set to %H%setto%%N.", W.messages),;
	
	public Object value;
	public ConfigM config;
	public String location;

	/**
	 * Makes an object from the list above.
	 * 
	 * @param value
	 *            Setting in the config file.
	 * @param config
	 *            The config file.
	 */
	private ConfigC (Object value, ConfigM config) {
		this.value = value;
		this.config = config;
		this.location = this.name().replaceAll("_", ".");
	}
}
