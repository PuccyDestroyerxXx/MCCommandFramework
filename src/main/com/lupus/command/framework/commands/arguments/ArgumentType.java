package com.lupus.command.framework.commands.arguments;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class ArgumentType {
	private static HashSet<ArgumentType> values = new HashSet<>();
	static {
		ArgumentType[] arguments = {
			new ArgumentType(Player.class, (arg) -> {
				Player player = Bukkit.getPlayer(arg[0]);
				if (player == null)
					throw new Exception("&4Gracz jest offline");
				return player;
			}),
			new ArgumentType(int.class,(arg) -> Integer.parseInt(arg[0]) ),
			new ArgumentType(UInteger.class,(arg) -> new UInteger(Integer.parseInt(arg[0]))),
			new ArgumentType(double.class,(arg) -> Double.parseDouble(arg[0]) ),
			new ArgumentType(String.class, (arg) -> arg[0] ),
			new ArgumentType(OfflinePlayer.class,(arg) -> Bukkit.getOfflinePlayer(arg[0]) ),
		};
		values.addAll(Arrays.asList(arguments));
	}
	public static HashSet<ArgumentType> values(){
		return values;
	}
	public static void addArgumentTypeInterpreter(ArgumentType type){

		for (ArgumentType value : values()) {
			if (value.clazz.equals(type.clazz)) {
				return;
			}
		}
		values.add(type);
	}
	public static void replaceArgumentTypeInterpreter(ArgumentType type) {
		boolean removed = values.removeIf(o-> o.clazz.equals(type.clazz));
		if (removed)
			values.add(type);
	}

	public ArgumentType(Class<?> clazz, ArgumentRunner run) {
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
