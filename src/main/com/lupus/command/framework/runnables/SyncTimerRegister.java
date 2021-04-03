package com.lupus.command.framework.runnables;

import com.lupus.command.LupusCommandFrameWork;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SyncTimerRegister extends BukkitRunnable {
	private int tasks;
	private long time;

	public SyncTimerRegister(int tasks) {
		this.tasks = tasks;
		time = System.currentTimeMillis();
	}
	@Override
	public void run(){
		var scanRunnable = LupusCommandFrameWork.getScanRunnable();
		if (scanRunnable.getTaskSize() != tasks)
			return;

		try {
			registerCommandAliasesInServer();
			scanRunnable.clearTask();
			System.out.println("TIME TO BE DONE :");
			System.out.println(time - System.currentTimeMillis());
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
