package com.lupus.command.framework.runnables;

import com.lupus.command.LupusCommandFrameWork;
import com.lupus.command.framework.commands.ILupusCommand;
import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.command.framework.commands.SupCommand;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Level;

public class RegisterRunnable extends BukkitRunnable {
	Class<?> clazz;
	private JavaPlugin plugin;

	public RegisterRunnable(Class<?> clazz, JavaPlugin plugin){
		this.clazz = clazz;
		this.plugin = plugin;
	}
	@Override
	public void run() {
		try {
			ClassLoader classLoader = clazz.getClassLoader();
			if (classLoader != null)
				registerAllCommands(plugin);
		}
		catch (Exception ignored){}

		LupusCommandFrameWork.getScanRunnable().addTaskDone(
				LupusCommandFrameWork.getScanRunnable().getTaskSize()
		);
	}
	public void registerAllCommands(Object caller){
		if (!(caller instanceof JavaPlugin))
			return;
		String pckgName = caller.getClass().getPackage().getName();
		Set<Class<? extends ILupusCommand>> commands;
		Set<Class<? extends SupCommand>> supCommands;
		Set<Class<? extends ILupusCommand>> subCommands = new HashSet<>();
		try(ScanResult scanResult = new ClassGraph().
				verbose(false).
				acceptPackages(pckgName).
				scan()
		){
			ClassInfoList classList = scanResult.getAllClasses();
			commands = new HashSet<>(classList.loadClasses(ILupusCommand.class));
			supCommands = new HashSet<>(classList.loadClasses(SupCommand.class));
		}

		if (supCommands.size() != 0)
			constructSupCommands(supCommands,subCommands);
		commands.removeAll(subCommands);
		if (commands.size() != 0)
			registerRestOfCommands(commands);
	}
	private void registerRestOfCommands(Set<Class<? extends ILupusCommand>> commands){
		for (Class<? extends ILupusCommand> aClass : commands) {
			ILupusCommand command = constructWithNoArgs(aClass);
			if (command != null) {
				Bukkit.getScheduler().runTask(LupusCommandFrameWork.getInstance(), command::registerCommand);
			}
		}
	}
	private void constructSupCommands(
			Set<Class<? extends SupCommand>> supCommands,
			Set<Class<? extends ILupusCommand>> subCommandRegisterSet
	) {
		for (Class<? extends SupCommand> aClass : supCommands){
			ILupusCommand command = constructWithNoArgs(aClass);
			if (command == null)
				continue;
			SupCommand supCommand = (SupCommand) command;
			registerSubCommands(supCommand,subCommandRegisterSet);
		}
	}
	private void registerSubCommands(
			SupCommand supCommand,
			Set<Class<? extends ILupusCommand>> subCommands
	){
		LupusCommand[] subCmds = supCommand.getSubCommands();
		for (LupusCommand subCmd : subCmds) {
			subCommands.add(subCmd.getClass());
		}
	}

	public boolean doesClassHasNoArgConstructor(Class<?> aClass){
		for (Constructor<?> constructor : aClass.getConstructors()) {
			// In Java 7-, use getParameterTypes and check the length of the array returned
			if (constructor.getParameterCount() == 0) {
				return true;
			}
		}
		return false;
	}
	private ILupusCommand constructWithNoArgs(Class<? extends ILupusCommand> aClass){
		int modifiers = aClass.getModifiers();
		if(Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers) || !doesClassHasNoArgConstructor(aClass)){
			return null;
		}
		try {
			Constructor<? extends ILupusCommand> constr = aClass.getConstructor();
			ILupusCommand instance = constr.newInstance();
			return instance;
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ignored) {

		}
		return null;
	}
}
