package com.lupus.command.framework.runnables;

import com.lupus.command.LupusCommandFrameWork;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScanPluginsRunnable extends BukkitRunnable {
	private List<Integer> doneTasksList = new CopyOnWriteArrayList<>();
	public void addTaskDone(int task){
		atomicTask.addAndGet(1);
	}
	public int getTaskSize(){
		return atomicTask.get();
	}
	private AtomicInteger atomicTask = new AtomicInteger(0);
	public void clearTask(){
		doneTasksList = null;
	}
	private int tasks = 0;
	@Override
	public void run() {
		for (org.bukkit.plugin.Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
			if (plugin == LupusCommandFrameWork.getInstance())
				continue;
			if (plugin == null)
				continue;
			if (!(plugin instanceof JavaPlugin))
				continue;
			JavaPlugin plug = (JavaPlugin) plugin;
			Class<?> clazz = plugin.getClass();
			if (clazz == null)
				continue;
			new RegisterRunnable(clazz,plug).runTaskAsynchronously(LupusCommandFrameWork.getInstance());
			tasks++;
		}
		new SyncTimerRegister(tasks).
				runTaskTimer(
						LupusCommandFrameWork.getInstance(),
						20L,
						20L
				);
	}
}
