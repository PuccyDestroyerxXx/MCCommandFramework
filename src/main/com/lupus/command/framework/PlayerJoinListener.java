package com.lupus.command.framework;

import com.lupus.command.LupusCommandFrameWork;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent ev){
		Player joined = ev.getPlayer();
		if (!LupusCommandFrameWork.getCache().containsKey(joined.getName()))
			LupusCommandFrameWork.getCache().put(joined.getName(),joined.getUniqueId());
	}
}
