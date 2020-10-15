package com.lupus.command.framework.commands;

import com.lupus.utils.ColorUtil;
import com.lupus.utils.Usage;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class LupusCommand implements ILupusCommand {
	String name			;
	String usage		= "NULL";
	String description	= "NULL";
	int argumentAmount;
	List<String> permissions = new ArrayList<>();
	HashMap<Integer, List<String>> autoComplete = new HashMap<>();

	public LupusCommand(String name,String usage,String description,int argumentAmount){
		this.name = name;
		this.usage = usage;
		this.description = description;
		this.argumentAmount = argumentAmount;
	}
	public LupusCommand(String name,String usage,int argumentAmount){
		this.name = name;
		this.usage = usage;
		this.argumentAmount = argumentAmount;

	}
	public LupusCommand(String name,int argumentAmount){
		this.name = name;
		this.argumentAmount = argumentAmount;

	}
	public void executeAsync(CommandSender sender, String[] args, Plugin plugin){
		ASyncCommand command = new ASyncCommand(this,sender,args);
		command.runTaskAsynchronously(plugin);
	}

	/**
	 * Execute checks permissions and argument amount before running command
	 * @param sender
	 * @param args
	 */
	@Override
	public void execute(CommandSender sender,String[] args){
		boolean hasPermission = permissions.stream().anyMatch(sender::hasPermission);
		if (hasPermission && !isArgumentAmountGood(sender,args)){
			return;
		}
		run(sender, args);
	}
	/**
	 * Checks if argument length is minimal argumentAmount<br/>
	 * Sends to sender info about correct use of command
	 * @param sender sender
	 * @param args arguments
	 * @return whether argument length is less than minimal (false) if not then true
	 */
	public boolean isArgumentAmountGood(CommandSender sender, String[] args){
		if (args.length < argumentAmount){
			sender.sendMessage(ColorUtil.text2Color("Prawidłowe użycie komendy: " + usage));
			return false;
		}
		return true;
	}
	/**
	 *
	 * @param argIndex - Index of argument
	 * @param argumentAutoComplete - argument to add
	 */
	public void addAutoComplete(int argIndex,String argumentAutoComplete){
		if (!autoComplete.containsKey(argIndex)) {
			List<String> list = new ArrayList<>();
			list.add(argumentAutoComplete);
			autoComplete.put(argIndex,list);
		}else {
			autoComplete.get(argIndex).add(argumentAutoComplete);
		}
	}

	/**
	 * Gets minimum argument amount
	 * @return minimum argument amount
	 */
	public int getArgumentAmount(){return argumentAmount;}

	/**
	 * Get description of Command
	 * @return colored description of command
	 */
	public String getDescription(){
		return ColorUtil.text2Color(description);
	}

	/**
	 * Gets usage of command
	 * @return colored usage of command
	 */
	public String usage(){
		return ColorUtil.text2Color(usage);
	}

	/**
	 * Get usage and description of command separated by given colored string " &5 - &r"
	 * @return colored usage and description of command
	 */
	public String getUsageDesc() { return ColorUtil.text2Color(usage() +" &5- &r"+ getDescription());}

	/**
	 * Gets name of command
	 * @return name of command
	 */
	@Override
	public String getName(){
		return ColorUtil.text2Color(name);
	}

	/**
	 * Checks if given string is matching command name
	 * @param cmd string to check
	 * @return boolean result of cmd.equals(name);
	 */
	@Override
	public boolean isMatch(String cmd) {
		return cmd.equals(name);
	}

	/**
	 * Points to the Usage.usage function
	 */
	public static String usage(String usage){
		return Usage.usage(usage);
	}
	/**
	 * Points to the Usage.usage function
	 */
	public static String usage(String command,String usage){
		return Usage.usage(command,usage);
	}

	/**
	 * Gets final arguments from the string array
	 * @param args argument array to process
	 * @param amount amount of last args to return
	 * @return Last amount of string objects in given String array
	 */
	@Override
	public String[] getFinalArgs(@NotNull String[] args, int amount){
		String[] argsNew = new String[amount];
		if (-1 - amount >= 0)
			System.arraycopy(args, args.length - amount + 1, argsNew, args.length - amount + 1, -1 - amount);
		return argsNew;
	}

	/**
	 * Get first String objects in String array from the given index
	 * @param args String array
	 * @param from index to get from
	 * @return String array of String objects from given index
	 */
	@Override
	public String[] getArgs(@NotNull String[] args, int from){
		String[] argsNew = new String[args.length-from];
		if (args.length - from >= 0) System.arraycopy(args, from, argsNew, 0, args.length - from);
		return argsNew;
	}
}
