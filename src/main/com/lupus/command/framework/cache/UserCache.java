package com.lupus.command.framework.cache;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class UserCache extends HashMap<String,UUID> {
	public Player getPlayer(String playerNickName) {
		return Bukkit.getPlayer(get(playerNickName));
	}
	public OfflinePlayer getOfflinePlayer(String playerNickName){
		return Bukkit.getOfflinePlayer(get(playerNickName));
	}
}
