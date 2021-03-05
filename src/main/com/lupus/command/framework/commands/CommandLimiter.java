package com.lupus.command.framework.commands;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandLimiter {
	/**
	 * Contains player UUID that contains hashmap that contains String - Command Long - Time Left to unblock
	 */
	HashMap<UUID, HashMap<String,Long>> playerActionLimiter = new HashMap<>();
	public static CommandLimiter INSTANCE = new CommandLimiter();
	CommandLimiter(){
	}
	public void addLimit(UUID p,String command,long time){
		time += System.currentTimeMillis();
		if (playerActionLimiter.containsKey(p))
			playerActionLimiter.get(p).put(command.toLowerCase(),time);
		else{
			HashMap<String,Long> hashMap = new HashMap<>();
			hashMap.put(command.toLowerCase(),time);
			playerActionLimiter.put(p,hashMap);
		}

	}
	public void addLimit(Player p, String command, long time){
		addLimit(p.getUniqueId(),command,time);
	}
	public void removeLimit(Player p,String command){
		removeLimit(p.getUniqueId(),command);
	}
	public void removeLimit(UUID p,String command){
		if (playerActionLimiter.containsKey(p))
			playerActionLimiter.get(p).remove(command);
	}
	public boolean hasLimit(Player p,String command){
		return hasLimit(p.getUniqueId(),command);
	}
	public boolean hasLimit(UUID p,String command){
		command = command.toLowerCase();
		if (playerActionLimiter.containsKey(p)) {
			if (playerActionLimiter.get(p).containsKey(command)) {
				return playerActionLimiter.get(p).get(command) > System.currentTimeMillis();
			}
		}
		return false;
	}
	public long getTimeLeft(Player p,String command){
		return getTimeLeft(p.getUniqueId(),command);
	}
	public long getTimeLeft(UUID p,String command){
		if (playerActionLimiter.containsKey(p))
			if (playerActionLimiter.get(p).containsKey(command))
				return playerActionLimiter.get(p).get(command) - System.currentTimeMillis();
		return -1;
	}
}
