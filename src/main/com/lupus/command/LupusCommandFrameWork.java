package com.lupus.command;

import com.lupus.command.framework.commands.ILupusCommand;
import com.lupus.command.framework.commands.LupusCommand;
import com.lupus.command.framework.commands.SupCommand;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.dependency.DependsOn;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.reflections.util.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Plugin(name="LupusCommandFramework", version="1.4")
@Description("Simple Command framework")
@Author("LupusVirtute")
@Website("github.com/PuccyDestroyerxXx")

public class LupusCommandFrameWork extends JavaPlugin {
	private static LupusCommandFrameWork mainPlugin;
	public static LupusCommandFrameWork getInstance(){
		return mainPlugin;
	}
	private List<Integer> doneTasksList = new CopyOnWriteArrayList<>();
	private int tasks = 0;
	@Override
	public void onEnable() {
		mainPlugin = this;
		new BukkitRunnable(){
			@Override
			public void run() {
				for (org.bukkit.plugin.Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
					if (plugin == mainPlugin)
						continue;
					if (plugin == null)
						continue;
					if (!(plugin instanceof JavaPlugin))
						continue;
					JavaPlugin plug = (JavaPlugin) plugin;
					Class<?> clazz = plugin.getClass();
					if (clazz == null)
						continue;
					Bukkit.getScheduler().runTaskAsynchronously(mainPlugin,() -> {
						ClassLoader classLoader = clazz.getClassLoader();
						if (classLoader != null)
							registerAllCommands(plug,classLoader);
						doneTasksList.add(doneTasksList.size());
					});
					tasks++;
				}
				new BukkitRunnable(){
					public void run(){

						if (doneTasksList.size() != tasks)
							return;

						try {
							registerCommandAliasesInServer();
						} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
						}
					}
				}.runTaskTimer(mainPlugin,20,20);
			}
		}.runTaskLaterAsynchronously(this,1);
	}
	public static void registerAllCommands(Object caller,ClassLoader classLoader){
		if (!(caller instanceof JavaPlugin))
			return;
		String pckgName = caller.getClass().getPackage().getName();
		List<ClassLoader> classLoadersList = new LinkedList<>();
		classLoadersList.add(classLoader);
		Reflections reflections = null;
		reflections = new Reflections(new ConfigurationBuilder()
				.setScanners(new SubTypesScanner(false), new ResourcesScanner())
				.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pckgName))));

		Set<Class<? extends ILupusCommand>> clazzSet = reflections.getSubTypesOf(ILupusCommand.class);
		Set<Class<? extends SupCommand>> supCommands = reflections.getSubTypesOf(SupCommand.class);
		Set<Class<? extends ILupusCommand>> subCommands = new HashSet<>();

		if (supCommands.size() != 0)
			for (Class<? extends SupCommand> aClass : supCommands){
				ILupusCommand command = constructWithNoArgs(aClass);
				if (command == null)
					continue;
				SupCommand supCommand = (SupCommand) command;

				LupusCommand[] subCmds = supCommand.getSubCommands();
				for (LupusCommand subCmd : subCmds) {
					subCommands.add(subCmd.getClass());
				}
			}
		if (clazzSet.size() != 0)
			for (Class<? extends ILupusCommand> aClass : clazzSet) {
				if (subCommands.size() != 0)
					if (subCommands.contains(aClass))
						continue;

				ILupusCommand command = constructWithNoArgs(aClass);
				if (command != null) {
					Bukkit.getScheduler().runTask(mainPlugin, command::registerCommand);
				}
			}
	}
	private static void registerCommandAliasesInServer() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Class<? extends Server> clazz = Bukkit.getServer().getClass();
		Method m = clazz.getDeclaredMethod("syncCommands");
		m.invoke(Bukkit.getServer());
	}
	public static boolean doesClassHasNoArgConstruct(Class<?> aClass){
		for (Constructor<?> constructor : aClass.getConstructors()) {
			// In Java 7-, use getParameterTypes and check the length of the array returned
			if (constructor.getParameterCount() == 0) {
				return true;
			}
		}
		return false;
	}
	private static ILupusCommand constructWithNoArgs(Class<? extends ILupusCommand> aClass){
		int modifiers = aClass.getModifiers();
		if(Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers) || !doesClassHasNoArgConstruct(aClass)){
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
