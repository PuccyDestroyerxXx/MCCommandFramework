package com.lupus.command.framework.commands;

import com.lupus.command.framework.commands.arguments.ArgumentList;
import org.bukkit.command.CommandSender;

public class HelperCommand extends LupusCommand {
	static CommandMeta meta = new CommandMeta().
			setName("help").
			addAlias("pomoc").
			setDescription("pomoc").
			setArgumentAmount(0).
			setUsage(usage("/[komenda]","pomoc"));
	private SupCommand command;

	public HelperCommand(SupCommand command) {
		super(meta);
		this.command = command;
		this.usageMessage = this.usageMessage.replace("[komenda]",command.getName());
	}

	@Override
	public void run(CommandSender sender, ArgumentList args) throws Exception {
		for (LupusCommand subCommand : command.getSubCommands())
			sender.sendMessage(colorText(subCommand.getUsageDesc()));
	}
}
