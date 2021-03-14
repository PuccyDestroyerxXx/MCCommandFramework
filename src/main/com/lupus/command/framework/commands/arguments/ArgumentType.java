package com.lupus.command.framework.commands.arguments;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public enum ArgumentType {
	PLAYER(Player.class, (arg) -> {
		Player player = Bukkit.getPlayer(arg[0]);
		if (player == null)
			throw new Exception("&4Gracz jest offline");
		return player;
	}),
	INTEGER(int.class,(arg) -> Integer.parseInt(arg[0]) ),
	UINTEGER(UInteger.class,(arg) -> new UInteger(Integer.parseInt(arg[0]))),
	DOUBLE(double.class,(arg) -> Double.parseDouble(arg[0]) ),
	STRING(String.class, (arg) -> arg[0] ),
	OFFLINE_PLAYER(OfflinePlayer.class,(arg) -> Bukkit.getOfflinePlayer(arg[0]) ),
	;
	ArgumentType(Class<?> clazz, ArgumentRunner run) {
		runner = run;
		this.clazz = clazz;
	}

	private ArgumentRunner runner;
	private Class<?> clazz;

	public boolean checkIfComplies(Class<?> clazz) {
		return this.clazz.isAssignableFrom(clazz);
	}
	public Object getObject(String... arguments) throws Exception {
		return runner.run(arguments);
	}
}
