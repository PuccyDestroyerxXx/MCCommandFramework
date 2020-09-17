package com.lupus.command.framework.commands;

import com.lupus.command.framework.utils.ColorUtil;
import com.lupus.command.framework.utils.Usage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class LupusCommand implements ILupusCommand {
	String name;
	String usage;
	String description;
	int argumentAmount;
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
		this.usage = "NULL";
		this.description = "NULL";
		this.argumentAmount = argumentAmount;

	}
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
	public int getArgumentAmount(){return argumentAmount;}
	public String getDescription(){
		return ColorUtil.text2Color(description);
	}
	public String usage(){
		return ColorUtil.text2Color(usage);
	}
	public String getUsageDesc() { return ColorUtil.text2Color(usage() +" &5- &r"+ getDescription());}
	@Override
	public String getName(){
		return ColorUtil.text2Color(name);
	}
	@Override
	public boolean isMatch(String cmd) {
		return cmd.equals(name);
	}
	public static String usage(String usage){
		return Usage.usage(usage);
	}
	public static String usage(String command,String usage){
		return Usage.usage(command,usage);
	}
	@Override
	public String[] getFinalArgs(@NotNull String[] args, int amount){
		String[] argsNew = new String[amount];
		if (-1 - amount >= 0)
			System.arraycopy(args, args.length - amount + 1, argsNew, args.length - amount + 1, -1 - amount);
		return argsNew;
	}
	@Override
	public String[] getArgs(@NotNull String[] args, int from){
		String[] argsNew = new String[args.length-from];
		if (args.length - from >= 0) System.arraycopy(args, from, argsNew, 0, args.length - from);
		return argsNew;
	}
}
