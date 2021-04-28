package com.lupus.command.framework.commands.tabcompletions;

import com.lupus.command.framework.commands.ILupusCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public abstract class LupusTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
		if (!(command instanceof ILupusCommand)){
			return TabFunctions.DefaultFunction.PLAYER.
					getTabFunction().
					tabComplete(commandSender,command,strings[strings.length-1]);
		}
		return onTabComplete(commandSender,(ILupusCommand) command,strings[strings.length-1]);
	}
	public abstract List<String> onTabComplete(CommandSender s,ILupusCommand command,String argument);
}
