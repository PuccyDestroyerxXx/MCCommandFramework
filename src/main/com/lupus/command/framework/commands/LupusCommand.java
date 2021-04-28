package com.lupus.command.framework.commands;

import com.lupus.command.framework.commands.arguments.ArgumentList;
import com.lupus.command.framework.messages.Message;
import com.lupus.command.framework.messages.MessageReplaceQuery;
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
	protected int argumentAmount;
	protected Set<String> permissions = new HashSet<>();
	protected CommandMeta meta;
	public LupusCommand(String name,String usage,String description,List<String> aliases,List<String> permissionNodes,int argumentAmount){
		this(new CommandMeta().
				setName(name).
				setUsage(usage).
				setDescription(description).
				setArgumentAmount(argumentAmount).
				addAliases(aliases).
				addPermissions(permissionNodes).
				setPermissionDeniedMessage(Message.NO_PERMISSION.toString())
		);
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
	public LupusCommand(CommandMeta meta){
		super(meta.getName(),meta.getDescription(),meta.getUsage(),meta.getAliases());
		this.meta = meta;
		setupMeta();
	}
	private void setupMeta(){
		this.argumentAmount = meta.getArgumentAmount();
		List<String> resultAliases = new ArrayList<>(meta.getAliases());
		resultAliases.add(meta.getName());
		this.setAliases(resultAliases);

		if (meta.getPermissions().size() > 0) {
			StringBuilder permissionBuilder = new StringBuilder();
			for (String permission : meta.getPermissions()) {
				permissionBuilder.append(permission).append(';');
			}
			super.setPermission(permissionBuilder.toString());
		}

		permissions.addAll(meta.getPermissions());

		super.setPermissionMessage(meta.getPermissionDenied());
	}
	@Override
	public CommandMeta getMeta(){
		return meta;
	}
	@Override
	public void updateMeta(CommandMeta meta){
		this.meta = meta;
		setupMeta();
	}
	@Override
	public boolean execute(CommandSender sender, String s, String[] strings) {
		if (!testPermission(sender)) return true;
		if(sender instanceof Player)
			if (CommandLimiter.INSTANCE.hasLimit((Player)sender,getName())) {
				var mrq = new MessageReplaceQuery().
						add("time",(CommandLimiter.INSTANCE.getTimeLeft((Player) sender, getName()) / 1000) + "");
				sender.sendMessage(Message.TIME_LIMIT.toString(mrq));
				return true;
			}
		execute(sender, strings);
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
		ArgumentList arguments = new ArgumentList();
		if (args.length > 0)
			arguments.addAll(Arrays.asList(args));
		if (permissions.size() != 0) {
			boolean hasPermission = permissions.stream().anyMatch(sender::hasPermission);
			if (!hasPermission){
				sender.sendMessage(super.getPermissionMessage());
			}
		}
		if (!isArgumentAmountGood(sender,arguments)){
			return;
		}

		try {
			run(sender, arguments);
		} catch (Exception exception) {
			if (exception instanceof NullPointerException) {
				exception.printStackTrace();
				return;
			}
			sender.sendMessage(colorText(exception.getMessage()));
		}
	}
	/**
	 * Checks if argument length is minimal argumentAmount<br/>
	 * Sends to sender info about correct use of command
	 * @param sender sender
	 * @param args arguments
	 * @return whether argument length is less than minimal (false) if not then true
	 */
	public boolean isArgumentAmountGood(CommandSender sender, ArgumentList args){
		if (args.size() < argumentAmount){
			sender.sendMessage(colorText("Prawidłowe użycie komendy: " + getUsage()));
			return false;
		}
		return true;
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
		return colorText(description);
	}

	/**
	 * Gets usage of command
	 * @return colored usage of command
	 */
	public String usage(){
		return colorText(this.usageMessage);
	}

	/**
	 * Get usage and description of command separated by given colored string " &5 - &r"
	 * @return colored usage and description of command
	 */
	public String getUsageDesc() {
		var mrq = new MessageReplaceQuery().
				add("usage",usage()).
				add("desc",getDescription());
		return colorText(Message.USAGE_DESC_TEMPLATE.toString(mrq));}

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
		var mrq = new MessageReplaceQuery().
				add("command",usage);

		return Message.COMMAND_SHOW_PATTERN.toString(mrq);
	}
	/**
	 * Points to the Usage.usage function
	 */
	public static String usage(String command,String usage) {
		var mrq = new MessageReplaceQuery().
				add("command",usage(command)).
				add("usage",usage);

		return Message.USAGE_PATTERN.toString(mrq);
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
	@Override
	public String[] getArgs(@NotNull String[] args, int from){
		String[] argsNew = new String[args.length-from];
		if (args.length - from >= 0) System.arraycopy(args, from, argsNew, 0, args.length - from);
		return argsNew;
	}
	/**
	 * Get first String objects in String array from the given index
	 * @param args String array
	 * @param from index to get from
	 * @return String array of String objects from given index
	 */
	@Override
	public ArgumentList getArgs(@NotNull ArgumentList args, int from){
		ArgumentList argsNew = new ArgumentList();
		if (args.size() - from >= 0) {
			argsNew.addAll(args.subList(from,args.size()));
		}
		return argsNew;
	}
	protected static String colorText(String txt){
		return ChatColor.translateAlternateColorCodes('&',txt);
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

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			var func = meta.getTabFunction(i);
			if (func != null)
				func.tabComplete(sender,this,arg);

		}
		return super.tabComplete(sender, alias, args);
	}
}
