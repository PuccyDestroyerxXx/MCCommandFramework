package com.lupus.command.framework.commands;

import org.bukkit.command.CommandSender;

public interface ILupusCommand {
	String getName();
	boolean isMatch(String cmd);
	void run(CommandSender sender, String[] args);
	String[] getFinalArgs(String[] args,int amount);
	String[] getArgs(String[] args,int from);
	void execute(CommandSender sender,String[] args);
}
