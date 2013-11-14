package de.dauni.chunkcontract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import de.dauni.chunkcontract.Managers.CommandM;
import de.dauni.chunkcontract.Managers.ConfigM;
import de.dauni.chunkcontract.Contract;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class W {
	/*
	 * Standard stuff.
	 */
	public static ArrayList<String> newFiles = new ArrayList<String>();
	public static ArrayList<CommandM> commands = new ArrayList<CommandM>();

	/*
	 * If you want another file to be created. Copy and paste this line.
	 */
	public static ConfigM config = new ConfigM("config");
	public static ConfigM messages = new ConfigM("messages");
	public static ConfigM contracts = new ConfigM("contracts");

	/*
	 * Add any variable you need in different classes here:
	 */

	public static Random random = new Random();
	public static ArrayList<Contract> contractList = new ArrayList<Contract>();
}
