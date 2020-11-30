package com.lupus.command.framework.commands;

import com.lupus.utils.ColorUtil;
import com.lupus.utils.Usage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;

public abstract class LupusCommand extends Command implements ILupusCommand {
	int argumentAmount;
	Set<String> permissions = new HashSet<>();
	protected HashMap<Integer, List<String>> autoComplete = new HashMap<>();
	static final String FAILED_PERMISSION_CHECK = colorText("&8[&6&lT&b&lO&f&l&8]&4&lBrak permisji do tej komendy");

	public LupusCommand(String name,String usage,String description,List<String> aliases,List<String> permissionNodes,int argumentAmount){
		super(name,description,usage,aliases);
		this.argumentAmount = argumentAmount;
		List<String> resultAliases = new ArrayList<>(aliases);
		resultAliases.add(name);
		this.setAliases(resultAliases);
		if (permissionNodes.size() > 0)
			super.setPermission(permissionNodes.get(0));
		permissions.addAll(permissionNodes);
		super.setPermissionMessage(FAILED_PERMISSION_CHECK);
	}
	public LupusCommand(String name,String usage,String desc,List<String> aliases,int argumentAmount){
		this(name,colorText(usage),colorText(desc),aliases,new ArrayList<>(),argumentAmount);
	}

	public LupusCommand(String name,String usage,String desc,int argumentAmount){
		this(name,colorText(usage),colorText(desc),new ArrayList<>(),new ArrayList<>(),argumentAmount);
	}
	public LupusCommand(String name,String usage,int argumentAmount){
		this(name,usage,colorText("&4&l-&r"),new ArrayList<>(),new ArrayList<>(),argumentAmount);
	}
	public LupusCommand(String name,int argumentAmount){
		this(name,colorText("&4&l-&r"),colorText("&4&l-&r"),new ArrayList<>(),new ArrayList<>(),argumentAmount);
	}

	@Override
	public boolean execute(CommandSender sender, String s, String[] strings) {
		if (!testPermission(sender)) return true;
		if(sender instanceof Player)
			if (CommandLimiter.INSTANCE.hasLimit((Player)sender,getName())) {
				sender.sendMessage(ChatColor.RED + "Masz nałożony limit czasowy na tą komende możesz użyć jej ponownie za " +
						ChatColor.YELLOW + CommandLimiter.INSTANCE.getTimeLeft((Player) sender, getName()) / 1000 + "s");
				return true;
			}
		execute(sender,strings);
		return true;
	}

	/**
	 * Executes command ASync
	 * @param sender command sender
	 * @param args arguments of command
	 * @param plugin plugin that sends ASync
	 */
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
		if (permissions.size() != 0) {
			boolean hasPermission = permissions.stream().anyMatch(sender::hasPermission);
			if (!hasPermission){
				sender.sendMessage(super.getPermissionMessage());
			}
		}
		if (!isArgumentAmountGood(sender,args)){
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
			sender.sendMessage(ColorUtil.text2Color("Prawidłowe użycie komendy: " + getUsage()));
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

	@Override
	public List<String> getAliases() {
		return super.getAliases();
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
		return ColorUtil.text2Color(this.usageMessage);
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
		return super.getName();
	}

	/**
	 * Checks if given string is matching command name
	 * @param cmd string to check
	 * @return boolean result of cmd.equals(name);
	 */
	@Override
	public boolean isMatch(String cmd) {
		return cmd.equals(this.getName());
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
	protected static String colorText(String txt){
		return ColorUtil.text2Color(txt);
	}
	private static Object getPrivateField(Object object, String field)throws SecurityException,
			NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Field objectField = clazz.getDeclaredField(field);
		objectField.setAccessible(true);
		Object result = objectField.get(object);
		objectField.setAccessible(false);
		return result;
	}
	@Override
	public void registerCommand(){
		try {
			Object result = getPrivateField(Bukkit.getServer().getPluginManager(), "commandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) result;
			commandMap.register(super.getName(),"LupusCommand",this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
