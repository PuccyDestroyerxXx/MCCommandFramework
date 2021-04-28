package com.lupus.command.framework.commands.tabcompletions;

import com.lupus.command.framework.commands.ILupusCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface LupusTabFunction {
	List<String> tabComplete(CommandSender s, Command command, String argument);
}
