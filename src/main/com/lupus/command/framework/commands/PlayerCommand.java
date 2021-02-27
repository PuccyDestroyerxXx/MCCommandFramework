package com.lupus.command.framework.commands;

import com.lupus.command.framework.commands.arguments.ArgumentList;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PlayerCommand extends LupusCommand {

	public PlayerCommand(String name,String usage,String description,List<String> aliases,List<String> permissionNodes,int argumentAmount){
		super(name, usage, description, aliases, permissionNodes, argumentAmount);
	}
	public PlayerCommand(String name, String usage, String description, int argumentAmount) {
		this(name, usage, description,new ArrayList<>(),new ArrayList<>(), argumentAmount);
	}

	public PlayerCommand(String name, String usage, int argumentAmount) {
		this(name, usage,"", argumentAmount);
	}

	public PlayerCommand(String name, int argumentAmount) {
		this(name,"", argumentAmount);
	}

	@Override
	public void run(CommandSender sender, ArgumentList args) throws Exception{
		if(sender instanceof Player){
			run((Player)sender,args);
		}
		else
			sender.sendMessage(ChatColor.RED+ "Komenda tylko dla gracza");
	}

	protected abstract void run(Player executor, String[] args);
}
