package com.lupus.command.framework.commands.tabcompletions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.HashMap;

public class TabFunctions {
	public enum DefaultFunction {
		PLAYER("PLAYER",
		(commandSender,command,argument)->{
			var onl = Bukkit.getOnlinePlayers();
			var list = new ArrayList<String>();
			for (Player player : onl) {
				if (!isVanished(player))
					list.add(player.getName());
			}
			list.removeIf(o->!o.startsWith(argument));
			return list;
		});

		private String id;
		DefaultFunction(String id,LupusTabFunction lupusTabFunction){
			this.id = id;
			inst().addFunction(id,lupusTabFunction);
		}
		public LupusTabFunction getTabFunction(){
			return inst().getTabFunction(id);
		}
	}
	private static boolean isVanished(Player player) {
		for (MetadataValue meta : player.getMetadata("vanished")) {
			if (meta.asBoolean()) return true;
		}
		return false;
	}
	private HashMap<String,LupusTabFunction> functions = new HashMap<>();
	private static TabFunctions INSTANCE = new TabFunctions();
	public static TabFunctions inst(){
		return INSTANCE;
	}
	private TabFunctions(){

	}
	public LupusTabFunction getTabFunction(String id){
		return this.functions.get(id);
	}
	public void addFunction(String id,LupusTabFunction function){
		this.functions.put(id,function);
	}

}
