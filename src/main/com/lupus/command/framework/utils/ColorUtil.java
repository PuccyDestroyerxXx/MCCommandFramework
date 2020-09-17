package com.lupus.command.framework.utils;

import org.bukkit.ChatColor;

public class ColorUtil {
	public static String text2Color(String txt){
		return ChatColor.translateAlternateColorCodes('&',txt);
	}
	public static String[] text2Color(String[] txt){
		for (int i = 0; i < txt.length; i++) {
			txt[i] = ChatColor.translateAlternateColorCodes('&',txt[i]);
		}
		return txt;
	}
	public static String strip(String txt){
		return ChatColor.stripColor(txt);
	}
}
