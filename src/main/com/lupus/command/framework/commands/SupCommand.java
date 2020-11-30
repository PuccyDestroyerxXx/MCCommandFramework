package com.lupus.command.framework.commands;

import org.bukkit.command.CommandSender;

import java.util.*;

public abstract class SupCommand extends LupusCommand {
	private final HashMap<String, LupusCommand> subCommands = new HashMap<>();
	public SupCommand(String name,String usage,String description,List<String> aliases,List<String> permissionNodes,int argumentAmount, LupusCommand[] subCommands) {
		super(name,usage,description,aliases,permissionNodes,argumentAmount);
		addBulkCommands(subCommands);
	}
	public SupCommand(String name,String usage,String description,int argumentAmount, LupusCommand[] subCommands) {
		this(name,usage,description,new ArrayList<>(),new ArrayList<>(),argumentAmount,subCommands);
	}
	public SupCommand(String name,String usage,int argumentAmount, LupusCommand[] subCommands) {
		this(name,usage,"",argumentAmount,subCommands);

	}
	public SupCommand(String name,int argumentAmount, LupusCommand[] subCommands){
		this(name,"",argumentAmount,subCommands);
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
		List<String> commandList = new ArrayList<>();
		for (int i = 0; i < subCommands.length; i++) {
			LupusCommand subCommand = subCommands[i];
			this.subCommands.put(subCommand.getName().toLowerCase(),subCommand);
			commandList.add(subCommands[i].getName());
			for (String alias : subCommand.getAliases()) {
				this.subCommands.put(alias,subCommand);
			}
		}
		autoComplete.put(0,commandList);
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
		LupusCommand command = null;
		if (args.length >= 1){
			String subCommand = args[0].toLowerCase();
			command = subCommands.get(subCommand);
		}
		boolean ignoreRest = optionalOperations(sender,args);
		if (ignoreRest)
			return;
		args = getArgs(args,1);
		if (command != null)
			command.run(sender,args);
		else
			usage();
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length < 1)
			return super.tabComplete(sender,alias, args);
		List<String> answer = new ArrayList<>();
		String lastWord = args[args.length - 1];
		if (args.length == 1){
			Set<String> values = subCommands.keySet();
			for (String value : values){
				if (value.startsWith(lastWord)){
					answer.add(value);
				}
			}
		}
		else {
			String[] betterArgs = getArgs(args,1);
			if (subCommands != null) {
				LupusCommand command = subCommands.get(args[0]);
				if (command != null)
					answer = command.tabComplete(sender, args[0], betterArgs);
			}
			if (answer == null)
				answer = super.tabComplete(sender, alias, args);
		}

		return answer;
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
