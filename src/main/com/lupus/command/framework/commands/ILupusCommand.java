package com.lupus.command.framework.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ILupusCommand {
	String getName();
	boolean isMatch(String cmd);
	void run(CommandSender sender, String[] args);
	String[] getFinalArgs(String[] args,int amount);
	String[] getArgs(String[] args,int from);
	List<String> getAliases();
	void execute(CommandSender sender,String[] args);
	void registerCommand();
}
