package com.lupus.command.framework.runnables;

import com.lupus.command.LupusCommandFrameWork;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SyncTimerRegister extends BukkitRunnable {
	private int tasks;

	public SyncTimerRegister(int tasks) {
		this.tasks = tasks;
	}
	@Override
	public void run(){
		LupusCommandFrameWork instance = LupusCommandFrameWork.getInstance();
		if (instance.getScanRunnable().getTaskSize() != tasks)
			return;

		try {
			registerCommandAliasesInServer();
			instance.getScanRunnable().clearTask();
			cancel();
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
		}
	}
	private static void registerCommandAliasesInServer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Class<? extends Server> clazz = Bukkit.getServer().getClass();
		Method m = clazz.getDeclaredMethod("syncCommands");
		m.invoke(Bukkit.getServer());
	}
}
