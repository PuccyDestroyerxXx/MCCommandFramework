package com.lupus.command;

import com.lupus.command.framework.commands.ILupusCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.LoadOn;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.scheduler.BukkitRunnable;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Plugin(name="LupusCommandFramework", version="1.2-SNAPSHOT")
@Description(desc = "Simple case opener")
@Author(name = "LupusVirtute")
@Website(url="github.com/PuccyDestroyerxXx")

public class LupusCommandFrameWork extends JavaPlugin {
	static LupusCommandFrameWork mainPlugin;
	public static LupusCommandFrameWork getInstance(){
		return mainPlugin;
	}
	@Override
	public void onEnable() {
		mainPlugin = this;
		new BukkitRunnable(){
			@Override
			public void run() {
				for (org.bukkit.plugin.Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins()) {
					if (plugin == null)
						continue;
					if (!(plugin instanceof JavaPlugin))
						continue;
					JavaPlugin plug = (JavaPlugin) plugin;
					Class<?> clazz = plugin.getClass();
					if (clazz == null)
						continue;
					ClassLoader classLoader = clazz.getClassLoader();
					if (classLoader != null)
						registerAllCommands(plug,classLoader);
				}
			}
		}.runTaskLater(this,200);
	}
	public static void registerAllCommands(Object caller,ClassLoader classLoader){
		if (!(caller instanceof JavaPlugin))
			return;
		JavaPlugin callerPlugin = (JavaPlugin)caller;
		String pckgName = caller.getClass().getPackage().getName();
		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(classLoader);
		Reflections reflections = null;
		try {
			reflections = new Reflections(new ConfigurationBuilder()
					.setScanners(new SubTypesScanner(false /* don't exclude Object.class */), new ResourcesScanner())
					.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
					.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(pckgName))));
		}
		catch (Exception ex){
			return;
		}
		Set<Class<? extends ILupusCommand>> clazzSet = reflections.getSubTypesOf(ILupusCommand.class);


		for (Class<? extends ILupusCommand> aClass : clazzSet) {
			int modifiers = aClass.getModifiers();
			if(Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)){
				continue;
			}
			try {
				Constructor<? extends ILupusCommand> constr = aClass.getConstructor();
				ILupusCommand instance = constr.newInstance();
				instance.registerCommand();

			} catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
