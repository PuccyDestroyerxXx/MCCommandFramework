package com.lupus.command.framework.utils;

import org.bukkit.ChatColor;

public class Usage {
	/**
	 * Creates simple string usage of command <br/>
	 * Syntax: <br/>
	 * <p color="red">command</p> <p color="yellow">usage of command</p>
	 * @param cmd command
	 * @param usage usage
	 * @return colored string with usage of command
	 */
	public static String usage(String cmd,String usage){
		return ChatColor.RED + cmd + " " + ChatColor.YELLOW + usage;
	}
	/**
	 * Creates simple string usage of command <br/>
	 * Syntax: <br/>
	 * <p color="red">command</p> <p color="yellow">usage of command</p>
	 * @param cmd command
	 * @param usage usage
	 * @return colored string with usage of command
	 */
	public static String usage(String cmd){
		return ChatColor.RED + cmd;
	}
}
