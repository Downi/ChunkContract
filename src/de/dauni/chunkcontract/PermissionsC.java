package de.dauni.chunkcontract;

public class PermissionsC {
	public static String main = ChunkContract.pdfFile.getName().toLowerCase() + ".";

	public enum PType {
		ALL, PLAYER, MODERATOR, ADMIN, OP;
	}

	public enum Permissions {
		info ("info", PType.ALL),
		help ("help", PType.ALL),
		create ("create", PType.ALL),
		report ("report", PType.ALL),
		set ("set", PType.ALL),
		set_status ("set_status", PType.MODERATOR),
		sign ("list", PType.ALL),
		approve ("approve", PType.ALL),
		decline ("decline", PType.ALL),
		list ("list", PType.ALL),
		list_open ("list_open", PType.MODERATOR),
		list_closed ("list_closed", PType.MODERATOR),
		cancel ("cancel", PType.ALL),
		done ("done", PType.ALL),
		view ("view", PType.ALL),
		comment ("comment", PType.ALL),
		view_all ("viewall", PType.MODERATOR),
		allcommands ("allcommands", PType.ADMIN);

		public String perm;
		public PType type;

		private Permissions (String perm, PType type) {
			this.perm = perm;
			this.type = type;
		}
	}
}
