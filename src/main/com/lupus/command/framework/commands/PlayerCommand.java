package com.lupus.command.framework.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PlayerCommand extends LupusCommand {
	HashMap<Integer, List<String>> autoComplete = new HashMap<>();

	public PlayerCommand(String name, String usage, String description, int argumentAmount) {
		super(name, usage, description, argumentAmount);
	}

	public PlayerCommand(String name, String usage, int argumentAmount) {
		super(name, usage, argumentAmount);
	}

	public PlayerCommand(String name, int argumentAmount) {
		super(name, argumentAmount);
	}

	@Override
	public void run(CommandSender sender, String[] args){
		if(sender instanceof Player){
			if (!CommandLimiter.INSTANCE.hasLimit((Player) sender,name))
				run((Player)sender,args);
			else
				sender.sendMessage(ChatColor.RED + "Masz nałożony limit czasowy na tą komende możesz użyć jej ponownie za " + CommandLimiter.INSTANCE.getTimeLeft((Player) sender,name));
		}
		else
			sender.sendMessage(ChatColor.RED+ "Komenda tylko dla gracza");
	}

	/**
	 *
	 * @param argIndex - Index of argument
	 * @param argumentAutoComplete - argument to add
	 */
	public void addAutoComplete(int argIndex,String argumentAutoComplete){
		if (!autoComplete.containsKey(argIndex)) {
			List<String> list = new ArrayList<>();
			list.add(argumentAutoComplete);
			autoComplete.put(argIndex,list);
		}else {
			autoComplete.get(argIndex).add(argumentAutoComplete);
		}
	}
	protected abstract void run(Player executor, String[] args);
}
