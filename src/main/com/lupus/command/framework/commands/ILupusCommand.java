package com.lupus.command.framework.commands;

import com.lupus.command.framework.commands.arguments.ArgumentList;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ILupusCommand {
	String getName();
	boolean isMatch(String cmd);
	void run(CommandSender sender, ArgumentList args) throws Exception;
	String[] getFinalArgs(String[] args,int amount);
	ArgumentList getArgs(@NotNull ArgumentList args, int from);
	String[] getArgs(@NotNull String[] args, int from);
	List<String> getAliases();
	void execute(CommandSender sender,String[] args);
	void registerCommand();
}
