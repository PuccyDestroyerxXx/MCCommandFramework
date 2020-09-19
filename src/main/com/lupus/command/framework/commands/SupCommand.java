package com.lupus.command.framework.commands;

import org.bukkit.command.CommandSender;

import java.util.HashMap;

public abstract class SupCommand extends LupusCommand {
	private final HashMap<String, LupusCommand> subCommands = new HashMap<>();
	public SupCommand(String name,String usage,String description,int argumentAmount, LupusCommand[] subCommands) {
		super(name,usage,description,argumentAmount);
		addBulkCommands(subCommands);

	}
	public SupCommand(String name,String usage,int argumentAmount, LupusCommand[] subCommands) {
		super(name,usage,argumentAmount);
		addBulkCommands(subCommands);

	}
	public SupCommand(String name,int argumentAmount, LupusCommand[] subCommands){
		super(name,argumentAmount);
		addBulkCommands(subCommands);
	}
	public LupusCommand[] getSubCommands(){
		LupusCommand[] subCmds = new LupusCommand[subCommands.values().size()];
		subCommands.values().toArray(subCmds);
		return subCmds;
	}

	/**
	 * Adds array of Sub Commands to Sub Commands list
	 * @param subCommands
	 */
	public void addBulkCommands(LupusCommand[] subCommands){
		for (int i = 0; i < subCommands.length; i++) {
			this.subCommands.put(subCommands[i].getName().toLowerCase(),subCommands[i]);
		}
	}
	/**
	 * Executes automatically subCommands
	 * @param sender CommandSender
	 * @param args Arguments send by sender
	 * @return success of command
	 */
	@Override
	public void run(CommandSender sender, String[] args) {
		if (!isArgumentAmountGood(sender,args)) {
			return;
		}
		String subCommand = args[0].toLowerCase();
		LupusCommand command = subCommands.get(subCommand);
		boolean ignoreRest = optionalOperations(sender,args);
		if (ignoreRest)
			return;
		getArgs(args,1);
		if (command != null)
			command.run(sender,args);
		else
			usage();
	}

	/**
	 * Override this only if you're dissatisfied with auto subCommand executor
	 * @param sender Sender sending the command
	 * @param args arguments send by sender
	 * @return true skips run auto subCommand selection
	 */
	protected boolean optionalOperations(CommandSender sender,String[] args){
		return false;
	}
}
