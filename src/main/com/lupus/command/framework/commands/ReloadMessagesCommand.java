package com.lupus.command.framework.commands;

import com.lupus.command.framework.commands.arguments.ArgumentList;
import com.lupus.command.framework.messages.Message;
import org.bukkit.command.CommandSender;

public class ReloadMessagesCommand extends LupusCommand {
	static CommandMeta meta = new CommandMeta().
			setName("reloadmsg").
			addPermission("lupus.command.reload");

	public ReloadMessagesCommand() {
		super(meta);
	}

	@Override
	public void run(CommandSender sender, ArgumentList args) throws Exception {
		Message.load();
	}
}
