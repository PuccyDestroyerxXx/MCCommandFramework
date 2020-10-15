package com.lupus.command.framework.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class ASyncCommand extends BukkitRunnable {
	private final String[] args;
	private final LupusCommand command;
	private final CommandSender sender;
	public ASyncCommand(LupusCommand command, CommandSender sender,String[] args){
		this.command = command;
		this.sender = sender;
		this.args = args;
	}
	@Override
	public void run() {
		command.execute(sender,args);
	}
}
