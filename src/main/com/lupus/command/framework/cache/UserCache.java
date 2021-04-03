package com.lupus.command.framework.cache;

import com.lupus.command.LupusCommandFrameWork;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class UserCache extends HashMap<String,UUID> {
	public Player getPlayer(String playerNickName) {
		return Bukkit.getPlayer(get(playerNickName));
	}
	public OfflinePlayer getOfflinePlayer(String playerNickName){
		return Bukkit.getOfflinePlayer(get(playerNickName));
	}
	File cacheFile = new File(LupusCommandFrameWork.getInstance().getDataFolder(),"cache.yml");
	public void load(){
		FileConfiguration config = getConfig();
		if (config != null)
			for (String key : config.getKeys(false)) {
				String uuidString = config.getString(key);
				UUID uuid = UUID.fromString(uuidString);
				this.put(key,uuid);
			}
	}
	private FileConfiguration getConfig(){
		exists();
		return YamlConfiguration.loadConfiguration(cacheFile);
	}
	private void exists(){
		if (!cacheFile.exists()) {
			try {
				cacheFile.createNewFile();
			} catch (IOException ignored) {
			}
		}
	}
	public void save(){
		var config = getConfig();
		for (Entry<String, UUID> stringUUIDEntry : this.entrySet()) {
			config.set(stringUUIDEntry.getKey(),stringUUIDEntry.getValue().toString());
		}
		try {
			config.save(cacheFile);
		} catch (IOException ignored) {

		}

	}
}
