package de.dauni.chunkcontract.Managers;

import java.io.File;

import de.dauni.chunkcontract.ChunkContract;
import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.W;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigM {
	String fileName;
	File file;
	FileConfiguration fileC;
	ConfigurationSection fileCS;
	String fileLocation;

	/**
	 * Use this class to create an automated config file.
	 * 
	 * @param fileName
	 *            Name of the file.
	 */
	public ConfigM (String fileName) {
		this.fileName = fileName;
		this.file = new File("plugins/" + ChunkContract.pdfFile.getName(),
				fileName + ".yml");
		this.fileLocation = ChunkContract.pdfFile.getName();
		this.fileC = new YamlConfiguration();
		this.checkFile();
		this.fileCS = fileC.getConfigurationSection("");
		this.load();
	}

	/**
	 * Use this class to create an automated config file.
	 * 
	 * @param fileName
	 *            Name of the file.
	 * @param fileLocation
	 *            Sub-Location of the file.
	 */
	public ConfigM (String fileName, String fileLocation) {
		this.fileName = fileName;
		this.file = new File("plugins/" + ChunkContract.pdfFile.getName()
				+ "/" + fileLocation, fileName + ".yml");
		this.fileLocation = ChunkContract.pdfFile.getName() + "/"
				+ fileLocation;
		this.fileC = new YamlConfiguration();
		this.checkFile();
		this.fileCS = fileC.getConfigurationSection("");
		this.load();
	}

	/**
	 * Check if there are new files created if so, display a message to the
	 * console.
	 */
	public static void newFiles() {
		ConfigM.setDefaults();
		for (String fileName : W.newFiles) {
			MessageM.sendMessage(
					null,
					"%TAG%WCouldn't find '%A%fileName%.yml%W' creating new one.",
					"fileName-" + fileName);
		}

		W.newFiles.clear();
	}

	/**
	 * Add config settings to the files if they don't exist.
	 */
	public static void setDefaults() {
		for (ConfigC value : ConfigC.values()) {
			value.config.load();
			if (value.config.getFile().get(value.location) == null) {
				value.config.getFile().set(value.location, value.value);
				value.config.save();
			}
		}
	}

	/**
	 * Check if file exists, if not create one.
	 */
	public void checkFile() {
		if (!this.file.exists()) {
			try {
				this.file.getParentFile().mkdirs();
				this.file.createNewFile();
				if (this.fileLocation == ChunkContract.pdfFile.getName()) {
					W.newFiles.add(this.fileName);
				} else {
					W.newFiles.add(this.fileLocation + this.fileName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Save the file.
	 */
	public void save() {
		try {
			this.fileC.save(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load the file.
	 */
	public void load() {
		this.checkFile();
		if (this.file.exists()) {
			try {
				this.fileC.load(this.file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get the File. Very useful for just loading/saving.
	 */
	public FileConfiguration getFile() {
		return this.fileC;
	}

	/**
	 * Get object from a Config.
	 * 
	 * @param location
	 *            Config location.
	 * @return
	 */
	public Object get(ConfigC location) {
		return this.getFile().get(location.location);
	}
}