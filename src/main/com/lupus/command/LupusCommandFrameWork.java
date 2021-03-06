package com.lupus.command;

import com.lupus.command.framework.PlayerJoinListener;
import com.lupus.command.framework.cache.UserCache;
import com.lupus.command.framework.messages.Message;
import com.lupus.command.framework.runnables.ScanPluginsRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name="LupusCommandFramework", version="1.5.1")
@Description("Simple Command framework")
@Author("LupusVirtute")
@Website("github.com/PuccyDestroyerxXx")

public class LupusCommandFrameWork extends JavaPlugin {
	private static LupusCommandFrameWork mainPlugin;
	private static ScanPluginsRunnable scanPluginsRunnable;
	private static UserCache cache;
	public static LupusCommandFrameWork getInstance(){
		return mainPlugin;
	}
	public static UserCache getCache(){ return cache; }
	public static ScanPluginsRunnable getScanRunnable(){
		return scanPluginsRunnable;
	}
	@Override
	public void onEnable() {
		mainPlugin = this;

		cache = new UserCache();
		cache.load();
		Message.load();

		scanPluginsRunnable = new ScanPluginsRunnable();
		scanPluginsRunnable.runTaskLaterAsynchronously(this,1);
		var list = new PlayerJoinListener();
		Bukkit.getPluginManager().registerEvents(list,this);
	}

	@Override
	public void onDisable() {
		cache.save();
	}
}
