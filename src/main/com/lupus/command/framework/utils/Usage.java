package com.lupus.command.framework.utils;

import org.bukkit.ChatColor;

public class Usage {
	public static String usage(String cmd,String usage){
		return ChatColor.RED + cmd + " " + ChatColor.YELLOW + usage;
	}
	public static String usage(String cmd){
		return ChatColor.RED + cmd;
	}
}
