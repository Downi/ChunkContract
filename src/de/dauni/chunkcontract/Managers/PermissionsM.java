package de.dauni.chunkcontract.Managers;

import de.dauni.chunkcontract.ConfigC;
import de.dauni.chunkcontract.PermissionsC;
import de.dauni.chunkcontract.PermissionsC.PType;
import de.dauni.chunkcontract.PermissionsC.Permissions;

import org.bukkit.entity.Player;

public class PermissionsM {
	/**
	 * Check if an player has the permission. Also checks * related permissions.
	 * This permission DOES HAVE TO be registered.
	 * 
	 * @param player
	 *            The player who needs the permission.
	 * @param perm
	 *            The permission.
	 * @param message
	 *            Send a message to the player saying you don't have the
	 *            permission.
	 * @return True/False when either the player has the permission.
	 */
	public static boolean hasPerm(Player player, Permissions perm,
			Boolean message) {
		PType type = perm.type;
		if (player == null) {
			return true;
		}

		if (type == PType.ALL) {
			return true;
		} else if (type == PType.OP) {
			if (player.isOp()) {
				return true;
			}
		} else if (type == PType.ADMIN) {
			if (player.hasPermission(PermissionsC.main + "admin")) {
				return true;
			}
		} else if (type == PType.MODERATOR) {
			if (player.hasPermission(PermissionsC.main + "moderator")) {
				return true;
			}
		} else if (type == PType.PLAYER) {
			if (player.hasPermission(PermissionsC.main + "player")) {
				return true;
			}
		}

		if (player.hasPermission("*")) {
			return true;
		} else if (player.hasPermission(PermissionsC.main + "*")) {
			return true;
		} else if (player.hasPermission(PermissionsC.main + perm.perm)) {
			return true;
		} else if (player.hasPermission(PermissionsC.main + perm.perm + ".*")) {
			return true;
		} else {
			if (message) {
				MessageM.sendFMessage(player, ConfigC.error_noPermission);
			}
		}
		return false;
	}

	/**
	 * Check if an player has the permission. Also checks * related permissions.
	 * This permission does NOT have to be registered.
	 * 
	 * @param player
	 *            The player who needs the permission.
	 * @param type
	 *            Type permission.
	 * @param perm
	 *            The permission.
	 * @param message
	 *            Send a message to the player saying you don't have the
	 *            permission.
	 * @return True/False when either the player has the permission.
	 */
	public static boolean hasRawPerm(Player player, PType type, String perm,
			Boolean message) {
		if (player == null) {
			return true;
		}

		if (type == PType.ALL) {
			return true;
		} else if (type == PType.OP) {
			if (player.isOp()) {
				return true;
			}
		} else if (type == PType.ADMIN) {
			if (player.hasPermission(PermissionsC.main + "admin")) {
				return true;
			}
		} else if (type == PType.MODERATOR) {
			if (player.hasPermission(PermissionsC.main + "moderator")) {
				return true;
			}
		} else if (type == PType.PLAYER) {
			if (player.hasPermission(PermissionsC.main + "player")) {
				return true;
			}
		}

		if (player.hasPermission("*")) {
			return true;
		} else if (player.hasPermission(PermissionsC.main + "*")) {
			return true;
		} else if (player.hasPermission(perm)) {
			return true;
		} else if (player.hasPermission(perm)) {
			return true;
		} else {
			if (message) {
				MessageM.sendFMessage(player, ConfigC.error_noPermission);
			}
		}
		return false;
	}
}
