package com.lupus.command.framework.commands;

import com.lupus.command.framework.commands.arguments.ArgumentList;
import com.lupus.command.framework.messages.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
	public PlayerCommand(CommandMeta meta){
		this(meta.getName(), meta.getUsage(), meta.getDescription(), meta.getAliases(), meta.getPermissions(), meta.getArgumentAmount());
	}
	@Override
	public void run(CommandSender sender, ArgumentList args) throws Exception{
		if(sender instanceof Player){
			Player player = (Player)sender;
			run(player,args);
		}
		else
			sender.sendMessage(Message.PLAYER_ONLY.toString());
	}

	protected abstract void run(Player executor, ArgumentList args) throws Exception;
}
