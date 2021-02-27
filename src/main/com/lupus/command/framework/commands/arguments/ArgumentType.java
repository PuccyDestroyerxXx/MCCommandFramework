package com.lupus.command.framework.commands.arguments;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public enum ArgumentType {
	PLAYER(Player.class, (arg) -> Bukkit.getPlayer(arg[0]) ),
	INTEGER(int.class,(arg) -> Integer.parseInt(arg[0]) ),
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
		return this.clazz.isInstance(clazz);
	}
	public Object getObject(String... arguments){
		return runner.run(arguments);
	}
}
