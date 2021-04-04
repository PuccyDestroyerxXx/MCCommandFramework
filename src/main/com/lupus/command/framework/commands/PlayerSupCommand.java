package com.lupus.command.framework.commands;

import com.lupus.command.framework.commands.arguments.ArgumentList;
import com.lupus.command.framework.messages.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerSupCommand extends SupCommand {
	public PlayerSupCommand(String name, String usage, String description, int argumentAmount, LupusCommand[] subCommands) {
		super(name, usage, description, argumentAmount, subCommands);
	}

	public PlayerSupCommand(String name, String usage, int argumentAmount, LupusCommand[] subCommands) {
		super(name, usage, argumentAmount, subCommands);
	}

	public PlayerSupCommand(String name, int argumentAmount, LupusCommand[] subCommands) {
		super(name, argumentAmount, subCommands);
	}
	public PlayerSupCommand(CommandMeta meta,LupusCommand[] subCommands){
		this(meta.getName(), meta.getUsage(), meta.getDescription(), meta.getArgumentAmount(),subCommands);
	}
	@Override
	public void run(CommandSender sender, ArgumentList args) throws Exception {
		if(sender instanceof Player){
			super.run(sender,args);
		}
		else
		sender.sendMessage(Message.PLAYER_ONLY.toString());

	}
}
