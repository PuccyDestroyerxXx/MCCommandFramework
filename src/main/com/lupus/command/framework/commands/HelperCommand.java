package com.lupus.command.framework.commands;

import com.lupus.command.framework.commands.arguments.ArgumentList;
import com.lupus.command.framework.messages.Message;
import com.lupus.command.framework.messages.MessageReplaceQuery;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

public class HelperCommand extends LupusCommand {
	static CommandMeta meta = new CommandMeta().
			setName("help").
			addAlias("pomoc").
			setDescription("pomoc").
			setArgumentAmount(0).
			setUsage(usage("/%komenda%","pomoc"));
	private final SupCommand command;

	public HelperCommand(SupCommand command) {
		super(meta);
		this.command = command;
		this.usageMessage = this.usageMessage.replace("%komenda%",command.getName());
	}

	@Override
	public void run(CommandSender sender, ArgumentList args) throws Exception {
		var strBuilder = new StringBuilder();
		Set<LupusCommand> subCommands = new HashSet<>();
		for (LupusCommand subCommand : command.getSubCommands()) {
			if (subCommands.contains(subCommand))
				continue;
			subCommands.add(subCommand);
			strBuilder.append(colorText(subCommand.getUsageDesc())).append('\n').append("&r");
		}

		var mrq = new MessageReplaceQuery().add("message",strBuilder.toString());

		sender.sendMessage(Message.HELPER_COMMAND.toString(mrq));
	}
}
