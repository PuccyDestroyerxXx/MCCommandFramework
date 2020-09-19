package com.lupus.command.framework.utils;

import org.bukkit.ChatColor;

public class ColorUtil {
	/**
	 * Converts '&' char in txt string to corresponding <br/>
	 * Color characters
	 * @param txt text to alternate
	 * @return text with color chars
	 */
	public static String text2Color(String txt){
		return ChatColor.translateAlternateColorCodes('&',txt);
	}
	/**
	 * Converts '&' char in txt string to corresponding <br/>
	 * Color characters
	 * @param txt text to alternate
	 * @return text with color chars
	 */
	public static String[] text2Color(String[] txt){
		for (int i = 0; i < txt.length; i++) {
			txt[i] = ChatColor.translateAlternateColorCodes('&',txt[i]);
		}
		return txt;
	}

	/**
	 * Strips text of the colors
	 * @param txt text to strip of color chars
	 * @return stripped text
	 */
	public static String strip(String txt){
		return ChatColor.stripColor(txt);
	}
}
